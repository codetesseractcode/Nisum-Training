/**
 * Question 2: Custom Exception - InvalidAgeException
 */

/**
 * Custom exception class for invalid age scenarios
 */
class InvalidAgeException extends RuntimeException {
    
    public InvalidAgeException(String message) {
        super(message);
    }
    
    public InvalidAgeException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class Question2_CustomAgeException {
    
    /**
     * Validates age input and throws InvalidAgeException for invalid values
     * @param age The age to validate
     * @throws InvalidAgeException if age is negative or zero
     */
    public static void validateAge(int age) throws InvalidAgeException {
        try {
            if (age <= 0) {
                throw new InvalidAgeException(
                    String.format("Invalid age: %d. Age must be positive.", age));
            }
            if (age > 150) {
                throw new InvalidAgeException(
                    String.format("Invalid age: %d. Age seems unrealistic (>150).", age));
            }
            System.out.printf("✓ Valid age: %d years old%n", age);
        } catch (InvalidAgeException e) {
            System.err.println("Age validation failed: " + e.getMessage());
            throw e; // Re-throw to be handled by caller
        }
    }
    
    /**
     * Enhanced validation method that handles multiple edge cases
     */
    public static void validateAgeEnhanced(Object ageInput) {
        try {
            // Handle different input types
            int age;
            if (ageInput instanceof String) {
                age = Integer.parseInt((String) ageInput);
            } else if (ageInput instanceof Integer) {
                age = (Integer) ageInput;
            } else {
                throw new IllegalArgumentException("Age must be a number or numeric string");
            }
            
            validateAge(age);
            
        } catch (NumberFormatException e) {
            System.err.println("Invalid age format: " + e.getMessage());
        } catch (InvalidAgeException e) {
            System.err.println("Custom validation error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Argument error: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Age Validation Testing ===");
        
        // Test cases for different age scenarios
        Object[] testAges = {
            25,      // Valid age
            -5,      // Negative age
            0,       // Zero age
            180,     // Unrealistic age
            "30",    // String number
            "abc",   // Invalid string
            null,    // Null input
            150      // Boundary case
        };
        
        for (Object testAge : testAges) {
            System.out.printf("%nTesting age input: %s%n", testAge);
            
            try {
                if (testAge == null) {
                    throw new IllegalArgumentException("Age cannot be null");
                }
                validateAgeEnhanced(testAge);
                
            } catch (InvalidAgeException e) {
                System.err.println("InvalidAgeException caught in main: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Other exception caught in main: " + 
                                 e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        }
        
        System.out.println("\n=== Direct validateAge() Testing ===");
        
        int[] directTestAges = {15, -10, 0, 45, 200};
        
        for (int age : directTestAges) {
            try {
                validateAge(age);
            } catch (InvalidAgeException e) {
                System.err.println("Caught InvalidAgeException: " + e.getMessage());
            } catch (RuntimeException e) {
                System.err.println("Caught other RuntimeException: " + e.getMessage());
            }
        }
        
        System.out.println("\n=== Exception Hierarchy Demonstration ===");
        try {
            validateAge(-1);
        } catch (InvalidAgeException e) {
            System.out.println("Exception details:");
            System.out.println("  Class: " + e.getClass().getSimpleName());
            System.out.println("  Message: " + e.getMessage());
            System.out.println("  Is RuntimeException: " + (e instanceof RuntimeException));
            System.out.println("  Is Exception: " + (e instanceof Exception));
        }
    }
}
