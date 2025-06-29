package com.nisum.userDemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Class Tests - Following SOLID Principles")
class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("John Doe", "john.doe@email.com", 25);
    }

    @Test
    @DisplayName("User should be created successfully")
    void testUserCreation() {
        assertNotNull(user);
        assertTrue(user instanceof User);
    }

    @Test
    @DisplayName("Test all user fields using assertAll")
    void testAllUserFields() {
        assertAll("User properties",
            () -> assertEquals("John Doe", user.getName(), "Name should match"),
            () -> assertEquals("john.doe@email.com", user.getEmail(), "Email should match"),
            () -> assertEquals(25, user.getAge(), "Age should match")
        );
    }

    @Nested
    @DisplayName("Constructor Validation Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should throw exception for null name")
        void testNullName() {
            NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new User(null, "test@email.com", 25),
                "Should throw NullPointerException for null name"
            );
            assertEquals("Name cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception for null email")
        void testNullEmail() {
            NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new User("John", null, 25),
                "Should throw NullPointerException for null email"
            );
            assertEquals("Email cannot be null", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Getter Methods Tests")
    class GetterTests {

        @Test
        @DisplayName("Test getName method")
        void testGetName() {
            assertEquals("John Doe", user.getName());
            assertNotNull(user.getName());
        }

        @Test
        @DisplayName("Test getEmail method")
        void testGetEmail() {
            assertEquals("john.doe@email.com", user.getEmail());
            assertNotNull(user.getEmail());
        }

        @Test
        @DisplayName("Test getAge method")
        void testGetAge() {
            assertEquals(25, user.getAge());
            assertTrue(user.getAge() > 0);
        }
    }

    @Nested
    @DisplayName("Equality and HashCode Tests")
    class EqualityTests {

        @Test
        @DisplayName("Test equals method - same objects")
        void testEqualsSameObjects() {
            assertEquals(user, user);
        }

        @Test
        @DisplayName("Test equals method - equal objects")
        void testEqualsEqualObjects() {
            User anotherUser = new User("John Doe", "john.doe@email.com", 25);
            assertEquals(user, anotherUser);
        }

        @Test
        @DisplayName("Test equals method - different objects")
        void testEqualsDifferentObjects() {
            User differentUser = new User("Jane Smith", "jane@email.com", 30);
            assertNotEquals(user, differentUser);
        }

        @Test
        @DisplayName("Test hashCode consistency")
        void testHashCodeConsistency() {
            User anotherUser = new User("John Doe", "john.doe@email.com", 25);
            assertEquals(user.hashCode(), anotherUser.hashCode());
        }
    }

    @Test
    @DisplayName("Test toString method")
    void testToString() {
        String expected = "User{name='John Doe', email='john.doe@email.com', age=25}";
        assertEquals(expected, user.toString());
        assertNotNull(user.toString());
    }
}
