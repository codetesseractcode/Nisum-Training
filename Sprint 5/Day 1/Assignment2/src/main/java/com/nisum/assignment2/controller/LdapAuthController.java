package com.nisum.assignment2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ldap")
public class LdapAuthController {

    @GetMapping("/user/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("authType", "LDAP");
        userInfo.put("username", authentication.getName());
        userInfo.put("authorities", authentication.getAuthorities());
        userInfo.put("message", "LDAP authentication successful");
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/admin/info")
    public ResponseEntity<?> getAdminInfo(Authentication authentication) {
        Map<String, Object> adminInfo = new HashMap<>();
        adminInfo.put("authType", "LDAP");
        adminInfo.put("authenticatedUser", authentication.getName());
        adminInfo.put("message", "Admin access granted via LDAP");
        adminInfo.put("authorities", authentication.getAuthorities());
        return ResponseEntity.ok(adminInfo);
    }

    @GetMapping("/public/info")
    public ResponseEntity<?> getPublicInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("message", "This is a public endpoint for LDAP authentication");
        info.put("authType", "LDAP");
        return ResponseEntity.ok(info);
    }
}
