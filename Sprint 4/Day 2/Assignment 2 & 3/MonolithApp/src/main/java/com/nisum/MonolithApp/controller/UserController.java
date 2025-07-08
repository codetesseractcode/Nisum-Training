package com.nisum.MonolithApp.controller;

import com.nisum.MonolithApp.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    // Hardcoded user data for demonstration
    private final Map<Long, User> users = new HashMap<>();

    public UserController() {
        // Initialize with some hardcoded users
        users.put(1L, new User(1L, "John Doe", "john.doe@example.com", "123-456-7890"));
        users.put(2L, new User(2L, "Jane Smith", "jane.smith@example.com", "098-765-4321"));
        users.put(3L, new User(3L, "Bob Johnson", "bob.johnson@example.com", "555-123-4567"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = users.get(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
