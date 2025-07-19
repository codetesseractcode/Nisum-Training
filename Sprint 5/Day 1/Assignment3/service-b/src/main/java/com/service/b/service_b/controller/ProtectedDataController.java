package com.service.b.service_b.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProtectedDataController {

    @GetMapping("/protected-data")
    public ResponseEntity<Map<String, String>> getProtectedData() {
        Map<String, String> response = Map.of(
            "status", "Access granted",
            "data", "Here is your secure business logic result."
        );

        return ResponseEntity.ok(response);
    }
}
