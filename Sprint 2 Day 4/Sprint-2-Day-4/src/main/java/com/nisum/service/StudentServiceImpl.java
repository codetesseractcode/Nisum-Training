package com.nisum.service;

import com.nisum.dao.StudentDAO;
import com.nisum.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Override
    public Student getStudentById(int id) {
        return studentDAO.getStudentById(id);
    }

    @Override
    public Student createStudent(Student student) throws ServiceException {
        // Check if email already exists
        if (studentDAO.emailExists(student.getEmail(), null)) {
            throw new ServiceException("Email already exists: " + student.getEmail());
        }

        return studentDAO.saveStudent(student);
    }

    @Override
    public Student updateStudent(Student student) throws ServiceException {
        // Check if student exists
        Student existingStudent = studentDAO.getStudentById(student.getId());
        if (existingStudent == null) {
            throw new ServiceException("Student not found with ID: " + student.getId());
        }

        // Check if email already exists (excluding this student)
        if (studentDAO.emailExists(student.getEmail(), student.getId())) {
            throw new ServiceException("Email already exists: " + student.getEmail());
        }

        boolean updated = studentDAO.updateStudent(student);
        if (!updated) {
            throw new ServiceException("Failed to update student with ID: " + student.getId());
        }

        return studentDAO.getStudentById(student.getId());
    }

    @Override
    public boolean deleteStudent(int id) {
        return studentDAO.deleteStudent(id);
    }
}
