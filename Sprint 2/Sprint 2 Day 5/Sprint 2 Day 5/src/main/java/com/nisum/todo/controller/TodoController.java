package com.nisum.todo.controller;

import com.nisum.todo.model.Todo;
import com.nisum.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling Todo operations
 * Following Single Responsibility Principle - handles HTTP requests only
 */
@Controller
public class TodoController {
    private final TodoService todoService;

    // Constructor Injection follows Dependency Inversion principle
    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // View controllers
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("todos", todoService.getAllTodos());
        return "index";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("todos", todoService.getAllTodos());
        return "index";
    }

    // REST API endpoints for AJAX operations

    @GetMapping("/api/todos")
    @ResponseBody
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/api/todos/{id}")
    @ResponseBody
    public Todo getTodoById(@PathVariable("id") Long id) {
        return todoService.getTodoById(id);
    }

    @PostMapping("/api/todos")
    @ResponseBody
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        todoService.createTodo(todo);
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    @PutMapping("/api/todos/{id}")
    @ResponseBody
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") Long id, @RequestBody Todo todo) {
        todo.setId(id);
        todoService.updateTodo(todo);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @DeleteMapping("/api/todos/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/todos/completed")
    @ResponseBody
    public List<Todo> getCompletedTodos() {
        return todoService.getCompletedTodos();
    }

    @GetMapping("/api/todos/pending")
    @ResponseBody
    public List<Todo> getPendingTodos() {
        return todoService.getPendingTodos();
    }
}
