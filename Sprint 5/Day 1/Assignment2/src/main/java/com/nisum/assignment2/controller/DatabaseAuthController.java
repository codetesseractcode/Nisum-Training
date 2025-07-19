package com.nisum.assignment2.controller;

import com.nisum.assignment2.entity.User;
import com.nisum.assignment2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/db")
public class DatabaseAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/public/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        try {
            User user = userService.createUser(request.getUsername(), request.getPassword(), request.getRole());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("username", user.getUsername());
            response.put("role", user.getRole());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/user/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("authType", "DATABASE");
        userInfo.put("username", authentication.getName());
        userInfo.put("authorities", authentication.getAuthorities());
        userInfo.put("message", "Database authentication successful");
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<?> getAllUsers(Authentication authentication) {
        List<User> users = userService.getAllUsers();
        Map<String, Object> response = new HashMap<>();
        response.put("authType", "DATABASE");
        response.put("authenticatedUser", authentication.getName());
        response.put("users", users);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/public/info")
    public ResponseEntity<?> getPublicInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("message", "This is a public endpoint for database authentication");
        info.put("authType", "DATABASE");
        return ResponseEntity.ok(info);
    }

    // Inner class for request body
    public static class UserRegistrationRequest {
        private String username;
        private String password;
        private String role;

        // Getters and Setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}
