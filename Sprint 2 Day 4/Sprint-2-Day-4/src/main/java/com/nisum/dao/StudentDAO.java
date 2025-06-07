package com.nisum.dao;

import com.nisum.model.Student;
import java.util.List;

/**
 * Data Access Object interface for Student entity
 * Following Single Responsibility Principle by focusing only on data access operations
 */
public interface StudentDAO {

    /**
     * Retrieve all students from the database
     * @return List of all students
     */
    List<Student> getAllStudents();

    /**
     * Get a student by their ID
     * @param id The student ID
     * @return The student if found, null otherwise
     */
    Student getStudentById(int id);

    /**
     * Save a new student to the database
     * @param student The student to save
     * @return The saved student with generated ID
     */
    Student saveStudent(Student student);

    /**
     * Update an existing student
     * @param student The student to update
     * @return true if successful, false otherwise
     */
    boolean updateStudent(Student student);

    /**
     * Delete a student by their ID
     * @param id The ID of the student to delete
     * @return true if successful, false otherwise
     */
    boolean deleteStudent(int id);

    /**
     * Check if a student with the given email already exists
     * @param email The email to check
     * @param excludeId ID to exclude from the check (for updates)
     * @return true if email exists, false otherwise
     */
    boolean emailExists(String email, Integer excludeId);
}
