package com.nisum.userDemo;

import com.nisum.userDemo.validation.AgeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("UserService Tests - Following SOLID Principles")
class UserServiceTest {

    @Mock
    private AgeValidator mockAgeValidator;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(mockAgeValidator);
    }

    @Nested
    @DisplayName("ValidateAge Method Tests - Using Mock")
    class ValidateAgeTests {

        @Test
        @DisplayName("Should delegate to AgeValidator")
        void testValidateAge_DelegatesToValidator() {
            // Given
            int age = 25;

            // When
            userService.validateAge(age);

            // Then
            verify(mockAgeValidator, times(1)).validateAge(age);
        }

        @Test
        @DisplayName("Should propagate exception from AgeValidator")
        void testValidateAge_PropagatesException() {
            // Given
            int underAge = 17;
            IllegalArgumentException expectedException = new IllegalArgumentException("Underage");
            doThrow(expectedException).when(mockAgeValidator).validateAge(underAge);

            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.validateAge(underAge),
                "Expected validateAge to throw IllegalArgumentException"
            );

            assertEquals("Underage", exception.getMessage());
            verify(mockAgeValidator, times(1)).validateAge(underAge);
        }
    }

    @Test
    @DisplayName("UserService should be instantiated with dependency")
    void testUserServiceCreation() {
        assertNotNull(userService, "UserService should not be null");
        assertTrue(userService instanceof UserService, "Should be instance of UserService");
    }
}
