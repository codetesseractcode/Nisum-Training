package com.nisum.assignment2.config;

import com.nisum.assignment2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // Create test users for database authentication
        try {
            userService.createUser("admin", "admin123", "ADMIN");
            userService.createUser("user", "user123", "USER");
            userService.createUser("testuser", "test123", "USER");

            System.out.println("Database test users created:");
            System.out.println("- admin/admin123 (ADMIN role)");
            System.out.println("- user/user123 (USER role)");
            System.out.println("- testuser/test123 (USER role)");
        } catch (RuntimeException e) {
            System.out.println("Test users already exist or error occurred: " + e.getMessage());
        }

        System.out.println("\nLDAP test users available:");
        System.out.println("- ben/benspassword (USER role)");
        System.out.println("- bob/bobspassword (USER role)");
        System.out.println("- joe/joespassword (ADMIN role)");
    }
}
