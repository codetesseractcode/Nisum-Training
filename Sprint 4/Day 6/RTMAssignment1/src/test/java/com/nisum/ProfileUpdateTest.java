package com.nisum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for R3: User can update profile
 * Test Case IDs: TC007, TC008, TC009
 */
public class ProfileUpdateTest {
    private UserService userService;
    private User testUser;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        testUser = new User("bob_smith", "password123", "bob@example.com");
        userService.addUser(testUser);
    }

    @Test
    @DisplayName("TC007: Valid profile update for existing user")
    void testValidProfileUpdate() {
        // Test Case ID: TC007
        // Requirement: R3 - User can update profile
        // Description: User should be able to update profile with valid username
        String newProfile = "Senior Software Engineer with 5 years experience";
        boolean result = userService.updateProfile("bob_smith", newProfile);
        assertTrue(result, "Profile update should succeed for existing user");

        // Verify profile was actually updated
        User updatedUser = userService.getUser("bob_smith");
        assertEquals(newProfile, updatedUser.getProfile(),
                    "Profile should be updated with new information");
    }

    @Test
    @DisplayName("TC008: Invalid profile update for non-existent user")
    void testInvalidProfileUpdateNonExistentUser() {
        // Test Case ID: TC008
        // Requirement: R3 - User can update profile
        // Description: Profile update should fail for non-existent user
        boolean result = userService.updateProfile("non_existent", "Some profile");
        assertFalse(result, "Profile update should fail for non-existent user");
    }

    @Test
    @DisplayName("TC009: Profile update with empty profile information")
    void testProfileUpdateWithEmptyProfile() {
        // Test Case ID: TC009
        // Requirement: R3 - User can update profile
        // Description: User should be able to update profile with empty information
        boolean result = userService.updateProfile("bob_smith", "");
        assertTrue(result, "Profile update should succeed even with empty profile");

        // Verify profile was updated to empty string
        User updatedUser = userService.getUser("bob_smith");
        assertEquals("", updatedUser.getProfile(),
                    "Profile should be updated to empty string");
    }
}
