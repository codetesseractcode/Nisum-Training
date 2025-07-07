package com.nisum.bookapi.controller;

import com.nisum.bookapi.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "JWT Authentication endpoints")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "Generate JWT token", description = "Generates a JWT token for testing purposes")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        // For demo purposes - in real app, validate credentials against user service
        String username = credentials.get("username");
        if (username == null || username.trim().isEmpty()) {
            username = "demo-user";
        }

        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token, "type", "Bearer"));
    }
}
