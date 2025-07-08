package com.nisum.UserService.controller;

import com.nisum.UserService.model.User;
import com.nisum.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for User operations
 * Following Single Responsibility Principle - handles HTTP requests/responses
 * Following Dependency Inversion Principle - depends on abstraction (UserService)
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * Constructor injection following Dependency Inversion Principle
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET endpoint to retrieve user by ID
     * Returns hardcoded user profile as per requirements
     *
     * @param id the user ID from path variable
     * @return ResponseEntity containing User object
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
