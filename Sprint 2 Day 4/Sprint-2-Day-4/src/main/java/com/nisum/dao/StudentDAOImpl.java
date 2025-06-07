package com.nisum.dao;

import com.nisum.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class StudentDAOImpl implements StudentDAO {

    private final JdbcTemplate jdbcTemplate;

    // SQL Queries
    private static final String SQL_GET_ALL = "SELECT * FROM students ORDER BY id";
    private static final String SQL_GET_BY_ID = "SELECT * FROM students WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO students (name, email, course) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE students SET name = ?, email = ?, course = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM students WHERE id = ?";
    private static final String SQL_CHECK_EMAIL = "SELECT COUNT(*) FROM students WHERE email = ? AND id != COALESCE(?, -1)";

    // Row mapper for converting database rows to Student objects
    private final RowMapper<Student> studentRowMapper = (rs, rowNum) -> {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("email"));
        student.setCourse(rs.getString("course"));
        student.setCreatedAt(rs.getTimestamp("created_at"));
        student.setUpdatedAt(rs.getTimestamp("updated_at"));
        return student;
    };

    @Autowired
    public StudentDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Student> getAllStudents() {
        return jdbcTemplate.query(SQL_GET_ALL, studentRowMapper);
    }

    @Override
    public Student getStudentById(int id) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_BY_ID, studentRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Student saveStudent(Student student) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getCourse());
            return ps;
        }, keyHolder);

        int newId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        student.setId(newId);

        return student;
    }

    @Override
    public boolean updateStudent(Student student) {
        int rowsAffected = jdbcTemplate.update(SQL_UPDATE,
                student.getName(),
                student.getEmail(),
                student.getCourse(),
                student.getId());

        return rowsAffected > 0;
    }

    @Override
    public boolean deleteStudent(int id) {
        int rowsAffected = jdbcTemplate.update(SQL_DELETE, id);
        return rowsAffected > 0;
    }

    @Override
    public boolean emailExists(String email, Integer excludeId) {
        try {
            Integer count = jdbcTemplate.queryForObject(SQL_CHECK_EMAIL, Integer.class, email, excludeId);
            return count != null && count > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
