package com.nisum.service;

import com.nisum.model.Student;
import java.util.List;

/**
 * Service layer interface for Student operations
 * Follows Single Responsibility Principle by encapsulating business logic
 */
public interface StudentService {

    /**
     * Get all students
     * @return List of all students
     */
    List<Student> getAllStudents();

    /**
     * Get student by ID
     * @param id Student ID
     * @return Student if found, null otherwise
     */
    Student getStudentById(int id);

    /**
     * Create a new student
     * @param student Student to create
     * @return Created student with ID
     * @throws ServiceException if validation fails
     */
    Student createStudent(Student student) throws ServiceException;

    /**
     * Update an existing student
     * @param student Student to update
     * @return Updated student
     * @throws ServiceException if validation fails or student not found
     */
    Student updateStudent(Student student) throws ServiceException;

    /**
     * Delete a student
     * @param id ID of student to delete
     * @return true if deleted, false if not found
     */
    boolean deleteStudent(int id);
}
