package com.nisum.userDemo.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MinimumAgeValidator Tests - Following SOLID Principles")
class MinimumAgeValidatorTest {

    private MinimumAgeValidator validator;

    @BeforeEach
    void setUp() {
        validator = new MinimumAgeValidator();
    }

    @Nested
    @DisplayName("ValidateAge Method Tests")
    class ValidateAgeTests {

        @Test
        @DisplayName("Should throw IllegalArgumentException for underage user")
        void testValidateAge_UnderageThrowsException() {
            // Test with age less than 18
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validateAge(17),
                "Expected validateAge(17) to throw IllegalArgumentException"
            );

            // Assert the exception message
            assertEquals("Underage", exception.getMessage(),
                "Exception message should be 'Underage'");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for age 0")
        void testValidateAge_ZeroAgeThrowsException() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validateAge(0),
                "Expected validateAge(0) to throw IllegalArgumentException"
            );

            assertEquals("Underage", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for negative age")
        void testValidateAge_NegativeAgeThrowsException() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validateAge(-5),
                "Expected validateAge(-5) to throw IllegalArgumentException"
            );

            assertEquals("Underage", exception.getMessage());
        }

        @Test
        @DisplayName("Should not throw exception for valid age 18")
        void testValidateAge_ValidAge18() {
            // This should not throw any exception
            assertDoesNotThrow(() -> validator.validateAge(18),
                "validateAge(18) should not throw any exception");
        }

        @Test
        @DisplayName("Should not throw exception for valid age above 18")
        void testValidateAge_ValidAgeAbove18() {
            // Test multiple valid ages
            assertAll("Valid ages should not throw exceptions",
                () -> assertDoesNotThrow(() -> validator.validateAge(18)),
                () -> assertDoesNotThrow(() -> validator.validateAge(25)),
                () -> assertDoesNotThrow(() -> validator.validateAge(65)),
                () -> assertDoesNotThrow(() -> validator.validateAge(100))
            );
        }

        @Test
        @DisplayName("Comprehensive test with multiple underage values")
        void testValidateAge_MultipleUnderageValues() {
            int[] underageValues = {1, 5, 10, 15, 17};

            for (int age : underageValues) {
                IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> validator.validateAge(age),
                    "Expected validateAge(" + age + ") to throw IllegalArgumentException"
                );

                assertEquals("Underage", exception.getMessage(),
                    "Exception message should be 'Underage' for age " + age);
            }
        }
    }

    @Test
    @DisplayName("MinimumAgeValidator should be instantiated")
    void testValidatorCreation() {
        assertNotNull(validator, "MinimumAgeValidator should not be null");
        assertTrue(validator instanceof AgeValidator, "Should implement AgeValidator interface");
    }
}
