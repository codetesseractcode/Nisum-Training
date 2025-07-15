package com.nisum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for R2: User can reset password
 * Test Case IDs: TC004, TC005, TC006
 */
public class PasswordResetTest {
    private UserService userService;
    private User testUser;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        testUser = new User("jane_doe", "oldpassword", "jane@example.com");
        userService.addUser(testUser);
    }

    @Test
    @DisplayName("TC004: Valid password reset with correct email")
    void testValidPasswordReset() {
        // Test Case ID: TC004
        // Requirement: R2 - User can reset password
        // Description: User should be able to reset password with valid username and email
        boolean result = userService.resetPassword("jane_doe", "jane@example.com", "newpassword123");
        assertTrue(result, "Password reset should succeed with valid credentials");

        // Verify password was actually changed
        assertTrue(userService.login("jane_doe", "newpassword123"),
                  "User should be able to login with new password");
        assertFalse(userService.login("jane_doe", "oldpassword"),
                   "User should not be able to login with old password");
    }

    @Test
    @DisplayName("TC005: Invalid password reset with wrong email")
    void testInvalidPasswordResetWrongEmail() {
        // Test Case ID: TC005
        // Requirement: R2 - User can reset password
        // Description: Password reset should fail with wrong email
        boolean result = userService.resetPassword("jane_doe", "wrong@example.com", "newpassword123");
        assertFalse(result, "Password reset should fail with wrong email");

        // Verify password was not changed
        assertTrue(userService.login("jane_doe", "oldpassword"),
                  "User should still be able to login with old password");
    }

    @Test
    @DisplayName("TC006: Invalid password reset for non-existent user")
    void testInvalidPasswordResetNonExistentUser() {
        // Test Case ID: TC006
        // Requirement: R2 - User can reset password
        // Description: Password reset should fail for non-existent user
        boolean result = userService.resetPassword("non_existent", "email@example.com", "newpassword123");
        assertFalse(result, "Password reset should fail for non-existent user");
    }
}
