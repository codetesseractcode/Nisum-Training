package com.nisum.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/user/profile")
    public Map<String, Object> getUserProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User profile accessed successfully");
        response.put("username", auth.getName());
        response.put("authorities", auth.getAuthorities());
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    @GetMapping("/manager/reports")
    public Map<String, Object> getManagerReports() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Manager reports accessed successfully");
        response.put("username", auth.getName());
        response.put("data", "Confidential manager data");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    @GetMapping("/admin/users")
    public Map<String, Object> getAdminData() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Admin data accessed successfully");
        response.put("username", auth.getName());
        response.put("data", "Highly sensitive admin data");
        response.put("users", new String[]{"user", "admin", "manager"});
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    @GetMapping("/public/info")
    public Map<String, Object> getPublicInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Public information - no authentication required");
        response.put("timestamp", System.currentTimeMillis());
        response.put("version", "1.0.0");
        return response;
    }
}
