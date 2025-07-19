package com.service.b.service_b.controller;

import com.service.b.service_b.util.JwtTestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private JwtTestUtil jwtTestUtil;

    @GetMapping("/generate-token")
    public ResponseEntity<Map<String, String>> generateToken(
            @RequestParam(defaultValue = "testuser") String username,
            @RequestParam(defaultValue = "ROLE_SERVICE") String role) {

        String token = jwtTestUtil.generateToken(username, List.of(role));

        Map<String, String> response = Map.of(
            "token", token,
            "username", username,
            "role", role,
            "usage", "Use this token in Authorization header as 'Bearer " + token + "'"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "Service B is running", "port", "8085"));
    }
}
