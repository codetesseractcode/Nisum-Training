package com.nisum.controller;

import jakarta.validation.Valid;
import com.nisum.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.nisum.service.ServiceException;
import com.nisum.service.StudentService;

import java.util.List;

/**
 * Controller for handling web interface operations for Student Management
 */
@Controller
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Show landing page
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    // Show all students - explicitly mapped to handle both with and without trailing slash
    @RequestMapping(value = {"/students", "/students/"}, method = RequestMethod.GET)
    public String listStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "index";
    }

    // Show student form for creating a new student
    @RequestMapping(value = "/students/new", method = RequestMethod.GET)
    public String showNewForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/form";
    }

    // Create new student
    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public String createStudent(@Valid @ModelAttribute("student") Student student,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "students/form";
        }

        try {
            studentService.createStudent(student);
            redirectAttributes.addFlashAttribute("message", "Student created successfully!");
            return "redirect:/students";
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/students/new";
        }
    }

    // Show student form for editing
    @RequestMapping(value = "/students/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            redirectAttributes.addFlashAttribute("error", "Student not found with ID: " + id);
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "students/form";
    }

    // Update student
    @RequestMapping(value = "/students/update", method = RequestMethod.POST)
    public String updateStudent(@Valid @ModelAttribute("student") Student student,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "students/form";
        }

        try {
            studentService.updateStudent(student);
            redirectAttributes.addFlashAttribute("message", "Student updated successfully!");
            return "redirect:/students";
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/students/edit/" + student.getId();
        }
    }

    // Delete student
    @RequestMapping(value = "/students/delete/{id}", method = RequestMethod.POST)
    public String deleteStudent(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("message", "Student deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Student not found with ID: " + id);
        }
        return "redirect:/students";
    }

    // View student details
    @RequestMapping(value = "/students/view/{id}", method = RequestMethod.GET)
    public String viewStudent(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            redirectAttributes.addFlashAttribute("error", "Student not found with ID: " + id);
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "students/view";
    }
}
