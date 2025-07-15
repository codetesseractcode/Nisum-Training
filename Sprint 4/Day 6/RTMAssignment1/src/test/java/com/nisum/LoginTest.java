package com.nisum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for R1: User can log in
 * Test Case IDs: TC001, TC002, TC003
 */
public class LoginTest {
    private UserService userService;
    private User testUser;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        testUser = new User("john_doe", "password123", "john@example.com");
        userService.addUser(testUser);
    }

    @Test
    @DisplayName("TC001: Valid login with correct credentials")
    void testValidLogin() {
        // Test Case ID: TC001
        // Requirement: R1 - User can log in
        // Description: User should be able to login with valid username and password
        boolean result = userService.login("john_doe", "password123");
        assertTrue(result, "User should be able to login with valid credentials");
    }

    @Test
    @DisplayName("TC002: Invalid login with wrong password")
    void testInvalidLoginWrongPassword() {
        // Test Case ID: TC002
        // Requirement: R1 - User can log in
        // Description: User should not be able to login with wrong password
        boolean result = userService.login("john_doe", "wrongpassword");
        assertFalse(result, "User should not be able to login with wrong password");
    }

    @Test
    @DisplayName("TC003: Invalid login with non-existent user")
    void testInvalidLoginNonExistentUser() {
        // Test Case ID: TC003
        // Requirement: R1 - User can log in
        // Description: Login should fail for non-existent user
        boolean result = userService.login("non_existent", "password123");
        assertFalse(result, "Login should fail for non-existent user");
    }
}
