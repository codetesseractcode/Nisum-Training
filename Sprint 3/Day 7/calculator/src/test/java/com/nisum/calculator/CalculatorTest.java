package com.nisum.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Calculator using JUnit 5.
 * Tests all arithmetic operations and edge cases.
 */
@DisplayName("Calculator Tests")
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Nested
    @DisplayName("Addition Tests")
    class AdditionTests {

        @Test
        @DisplayName("Should add two positive numbers correctly")
        void testAddPositiveNumbers() {
            // Given
            int a = 5;
            int b = 3;

            // When
            int result = calculator.add(a, b);

            // Then
            assertEquals(8, result, "5 + 3 should equal 8");
        }

        @Test
        @DisplayName("Should add negative numbers correctly")
        void testAddNegativeNumbers() {
            // Given
            int a = -5;
            int b = -3;

            // When
            int result = calculator.add(a, b);

            // Then
            assertEquals(-8, result, "-5 + (-3) should equal -8");
        }

        @Test
        @DisplayName("Should add zero correctly")
        void testAddWithZero() {
            // Given
            int a = 10;
            int b = 0;

            // When
            int result = calculator.add(a, b);

            // Then
            assertEquals(10, result, "10 + 0 should equal 10");
            assertNotEquals(9, result, "Result should not be 9");
        }
    }

    @Nested
    @DisplayName("Subtraction Tests")
    class SubtractionTests {

        @Test
        @DisplayName("Should subtract two positive numbers correctly")
        void testSubtractPositiveNumbers() {
            // Given
            int a = 10;
            int b = 3;

            // When
            int result = calculator.subtract(a, b);

            // Then
            assertEquals(7, result, "10 - 3 should equal 7");
        }

        @Test
        @DisplayName("Should subtract negative numbers correctly")
        void testSubtractNegativeNumbers() {
            // Given
            int a = 5;
            int b = -3;

            // When
            int result = calculator.subtract(a, b);

            // Then
            assertEquals(8, result, "5 - (-3) should equal 8");
        }

        @Test
        @DisplayName("Should return zero when subtracting equal numbers")
        void testSubtractEqualNumbers() {
            // Given
            int a = 5;
            int b = 5;

            // When
            int result = calculator.subtract(a, b);

            // Then
            assertEquals(0, result, "5 - 5 should equal 0");
            assertNotEquals(1, result, "Result should not be 1");
        }
    }

    @Nested
    @DisplayName("Multiplication Tests")
    class MultiplicationTests {

        @Test
        @DisplayName("Should multiply two positive numbers correctly")
        void testMultiplyPositiveNumbers() {
            // Given
            int a = 4;
            int b = 5;

            // When
            int result = calculator.multiply(a, b);

            // Then
            assertEquals(20, result, "4 * 5 should equal 20");
        }

        @Test
        @DisplayName("Should multiply by zero correctly")
        void testMultiplyByZero() {
            // Given
            int a = 10;
            int b = 0;

            // When
            int result = calculator.multiply(a, b);

            // Then
            assertEquals(0, result, "10 * 0 should equal 0");
        }

        @Test
        @DisplayName("Should multiply negative numbers correctly")
        void testMultiplyNegativeNumbers() {
            // Given
            int a = -3;
            int b = 4;

            // When
            int result = calculator.multiply(a, b);

            // Then
            assertEquals(-12, result, "-3 * 4 should equal -12");
            assertNotEquals(12, result, "Result should not be positive 12");
        }

        @Test
        @DisplayName("Should multiply two negative numbers to get positive result")
        void testMultiplyTwoNegativeNumbers() {
            // Given
            int a = -3;
            int b = -4;

            // When
            int result = calculator.multiply(a, b);

            // Then
            assertEquals(12, result, "-3 * -4 should equal 12");
        }
    }

    @Nested
    @DisplayName("Division Tests")
    class DivisionTests {

        @Test
        @DisplayName("Should divide two positive numbers correctly")
        void testDividePositiveNumbers() {
            // Given
            int a = 15;
            int b = 3;

            // When
            int result = calculator.divide(a, b);

            // Then
            assertEquals(5, result, "15 / 3 should equal 5");
        }

        @Test
        @DisplayName("Should divide negative numbers correctly")
        void testDivideNegativeNumbers() {
            // Given
            int a = -15;
            int b = 3;

            // When
            int result = calculator.divide(a, b);

            // Then
            assertEquals(-5, result, "-15 / 3 should equal -5");
            assertNotEquals(5, result, "Result should not be positive 5");
        }

        @Test
        @DisplayName("Should handle integer division correctly")
        void testIntegerDivision() {
            // Given
            int a = 10;
            int b = 3;

            // When
            int result = calculator.divide(a, b);

            // Then
            assertEquals(3, result, "10 / 3 should equal 3 (integer division)");
            assertNotEquals(4, result, "Result should not be 4");
        }

        @Test
        @DisplayName("Should throw ArithmeticException when dividing by zero")
        void testDivideByZero() {
            // Given
            int a = 10;
            int b = 0;

            // When & Then
            ArithmeticException exception = assertThrows(ArithmeticException.class,
                () -> calculator.divide(a, b),
                "Division by zero should throw ArithmeticException");

            assertEquals("Division by zero is not allowed", exception.getMessage(),
                "Exception message should match expected text");
        }

        @Test
        @DisplayName("Should return zero when dividing zero by non-zero number")
        void testDivideZeroByNumber() {
            // Given
            int a = 0;
            int b = 5;

            // When
            int result = calculator.divide(a, b);

            // Then
            assertEquals(0, result, "0 / 5 should equal 0");
        }
    }
}
