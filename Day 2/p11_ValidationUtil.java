/*
 11. Write a util class to check whether given username and password are valid or not.
     Username should be email id format.
     Password should have alphabets, digits and special characters.
*/

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class p11_ValidationUtil 
{
    public static void main(String[] args) 
    {
        System.out.println("=== Username and Password Validator ===\n");
        
        // Test cases for username (email format)
        String[] usernames = {
            "user@example.com",         // Valid
            "user.name@example.co.in",  // Valid
            "user-name@example.com",    // Valid
            "username@example",         // Invalid (missing TLD)
            "user@.com",                // Invalid (missing domain)
            "user@example..com",        // Invalid (double dots)
            "user name@example.com",    // Invalid (contains space)
            "user@exam_ple.com",        // Valid
            "@example.com",             // Invalid (missing local part)
            "user@"                     // Invalid (missing domain part)
        };
        
        // Test cases for password
        String[] passwords = {
            "Password123!",             // Valid (has letters, numbers, and special char)
            "password123",              // Invalid (no special char)
            "PASSWORD123!",             // Valid (has uppercase, numbers, and special char)
            "Pass!",                    // Invalid (too short)
            "Pass word123!",            // Valid (has space, but all required elements)
            "12345678!",                // Invalid (no letters)
            "Password!",                // Invalid (no digits)
            "passwordpassword123",      // Invalid (no special char)
            "Password123#$@",           // Valid (multiple special chars)
            "Pa1!"                      // Invalid (too short)
        };
        
        // Test username validation
        System.out.println("Username Validation Tests:");
        for (String username : usernames) 
        {
            boolean isValid = ValidationUtil.isValidUsername(username);
            System.out.println(username + " -> " + (isValid ? "Valid" : "Invalid"));
        }
        
        // Test password validation
        System.out.println("\nPassword Validation Tests:");
        for (String password : passwords) 
        {
            boolean isValid = ValidationUtil.isValidPassword(password);
            System.out.println(password + " -> " + (isValid ? "Valid" : "Invalid"));
        }
        
        // Interactive test with sample input
        System.out.println("\nValidating a sample login:");
        String sampleUsername = "user@example.com";
        String samplePassword = "SecurePass123!";
        
        boolean validCredentials = ValidationUtil.validateCredentials(sampleUsername, samplePassword);
        System.out.println("Login attempt with:");
        System.out.println("Username: " + sampleUsername);
        System.out.println("Password: " + samplePassword);
        System.out.println("Result: " + (validCredentials ? "Login successful!" : "Invalid credentials!"));
    }
}

/**
 * Utility class for validating usernames and passwords
 */
class ValidationUtil 
{
    // Constants for validation criteria
    private static final int MIN_PASSWORD_LENGTH = 8;
    
    // Regular expression for email validation
    private static final String EMAIL_PATTERN = 
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    
    // Regular expressions for password components
    private static final String HAS_LETTER = ".*[a-zA-Z].*";
    private static final String HAS_DIGIT = ".*\\d.*";
    private static final String HAS_SPECIAL = ".*[^a-zA-Z0-9].*";
    
    // Compiled patterns for better performance
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private static final Pattern letterPattern = Pattern.compile(HAS_LETTER);
    private static final Pattern digitPattern = Pattern.compile(HAS_DIGIT);
    private static final Pattern specialPattern = Pattern.compile(HAS_SPECIAL);
    
    /**
     * Validates if the given string is a valid username (email format)
     * 
     * @param username The username to validate
     * @return true if the username is valid, false otherwise
     */
    public static boolean isValidUsername(String username) 
    {
        if (username == null || username.trim().isEmpty()) 
        {
            return false;
        }
        
        Matcher matcher = emailPattern.matcher(username);
        return matcher.matches();
    }
    
    /**
     * Validates if the given string is a valid password
     * Password must:
     * 1. Be at least MIN_PASSWORD_LENGTH characters long
     * 2. Contain at least one letter (uppercase or lowercase)
     * 3. Contain at least one digit
     * 4. Contain at least one special character (non-alphanumeric)
     * 
     * @param password The password to validate
     * @return true if the password is valid, false otherwise
     */
    public static boolean isValidPassword(String password) 
    {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) 
        {
            return false;
        }
        
        // Check for required character types
        boolean hasLetter = letterPattern.matcher(password).matches();
        boolean hasDigit = digitPattern.matcher(password).matches();
        boolean hasSpecial = specialPattern.matcher(password).matches();
        
        return hasLetter && hasDigit && hasSpecial;
    }
    
    /**
     * Validates both username and password
     * 
     * @param username The username to validate
     * @param password The password to validate
     * @return true if both username and password are valid, false otherwise
     */
    public static boolean validateCredentials(String username, String password) 
    {
        return isValidUsername(username) && isValidPassword(password);
    }
}
