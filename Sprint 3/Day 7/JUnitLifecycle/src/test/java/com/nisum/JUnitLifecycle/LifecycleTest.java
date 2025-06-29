package com.nisum.JUnitLifecycle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 Lifecycle Test class to demonstrate the order of lifecycle method execution
 *
 * Expected execution order:
 * 1. @BeforeAll (once before all tests)
 * 2. @BeforeEach (before each test)
 * 3. @Test (test method)
 * 4. @AfterEach (after each test)
 * 5. @AfterAll (once after all tests)
 */
public class LifecycleTest {

    @BeforeAll
    static void setUpBeforeClass() {
        System.out.println("1. @BeforeAll - This method is executed once before all test methods in the class");
    }

    @BeforeEach
    void setUp() {
        System.out.println("2. @BeforeEach - This method is executed before each test method");
    }

    @Test
    void testMethod1() {
        System.out.println("3. @Test - Executing testMethod1");
        // Simple assertion to make it a valid test
        assert true;
    }

    @Test
    void testMethod2() {
        System.out.println("3. @Test - Executing testMethod2");
        // Simple assertion to make it a valid test
        assert true;
    }

    @Test
    void testMethod3() {
        System.out.println("3. @Test - Executing testMethod3");
        // Simple assertion to make it a valid test
        assert true;
    }

    @AfterEach
    void tearDown() {
        System.out.println("4. @AfterEach - This method is executed after each test method");
        System.out.println("   -------------------------------------------");
    }

    @AfterAll
    static void tearDownAfterClass() {
        System.out.println("5. @AfterAll - This method is executed once after all test methods in the class");
    }
}
