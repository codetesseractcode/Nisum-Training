/**
 * Program to demonstrate how to check if a String contains only digits
 * 
 * This program shows different ways to verify if a string consists entirely of digits.
 */

public class p43_CheckDigitsOnly {
    public static void main(String[] args) {
        System.out.println("=== Checking for Digits Only ===\n");
        
        // Test strings
        String[] testStrings = {
            "12345",        // Only digits
            "123abc",       // Digits and letters
            "123 456",      // Digits with space
            "",             // Empty string
            "0123456789",   // All digits from 0-9
            "-123"          // Negative number
        };
        
        System.out.println("Method 1: Using isDigit() with for loop");
        for (String str : testStrings) {
            System.out.println("\"" + str + "\" contains only digits? " + checkUsingCharLoop(str));
        }
        
        System.out.println("\nMethod 2: Using matches() with regex");
        for (String str : testStrings) {
            System.out.println("\"" + str + "\" contains only digits? " + str.matches("\\d+"));
        }
        
        System.out.println("\nMethod 3: Using Stream API (Java 8+)");
        for (String str : testStrings) {
            System.out.println("\"" + str + "\" contains only digits? " + checkUsingStream(str));
        }
        
        System.out.println("\n=== End of Checking for Digits Only ===");
    }
    
    /**
     * Checks if a string contains only digits using Character.isDigit()
     */
    private static boolean checkUsingCharLoop(String str) {
        // Handle empty string
        if (str.isEmpty()) {
            return false;
        }
        
        // Check each character
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Checks if a string contains only digits using Stream API
     */
    private static boolean checkUsingStream(String str) {
        // Handle empty string
        if (str.isEmpty()) {
            return false;
        }
        
        // Check using Stream API
        return str.chars()
                .allMatch(Character::isDigit);
    }
}
