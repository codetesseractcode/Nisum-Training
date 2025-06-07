package com.nisum.controller;

import jakarta.validation.Valid;
import com.nisum.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nisum.service.ServiceException;
import com.nisum.service.StudentService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API Controller for Student resources
 * Handles JSON-based interactions
 */
@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    private final StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") int id) {
        Student student = studentService.getStudentById(id);

        if (student == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Student not found with ID: " + id);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    // Create new student
    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody Student student) throws ServiceException {
        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    // Update student
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") int id,
                                          @Valid @RequestBody Student student) throws ServiceException {
        // Ensure ID in path matches the one in the body
        student.setId(id);

        Student updatedStudent = studentService.updateStudent(student);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") int id) {
        boolean deleted = studentService.deleteStudent(id);

        if (!deleted) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Student not found with ID: " + id);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Student deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
