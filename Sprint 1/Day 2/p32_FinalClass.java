/**
 * Program to demonstrate final classes in Java with multiple use cases
 * 
 * A final class cannot be subclassed (inherited from)
 * Common use cases for final classes:
 * 1. Security - prevent malicious subclassing
 * 2. Immutability - ensure class can't be changed
 * 3. Utility classes - designed to be used only through static methods
 * 4. Performance optimization
 * 5. Design pattern implementation (e.g., Singleton)
 */

public class p32_FinalClass {
    public static void main(String[] args) {
        System.out.println("=== Final Class Demonstration ===\n");
        
        // 1. Immutability Example - String class is a final class
        System.out.println("1. Immutability Example (String class is final):");
        String name = "Rajesh";
        String upperName = name.toUpperCase(); // This creates a new String
        
        System.out.println("Original: " + name);     // Original remains unchanged
        System.out.println("Modified: " + upperName); // New object created
        
        // 2. Security Example - MathOperations
        System.out.println("\n2. Security Example (Secure Math Operations):");
        
        double circle1Area = SecureMathOperations.calculateCircleArea(5.0);
        double circle2Area = SecureMathOperations.calculateCircleArea(7.5);
        
        System.out.println("Circle 1 area: " + circle1Area);
        System.out.println("Circle 2 area: " + circle2Area);
        
        double result = SecureMathOperations.add(24.5, 15.5);
        System.out.println("Addition result: " + result);
        
        // 3. Configuration Example
        System.out.println("\n3. Configuration Example:");
        
        System.out.println("App Name: " + AppConfig.APP_NAME);
        System.out.println("Max Users: " + AppConfig.MAX_USERS);
        System.out.println("Server URL: " + AppConfig.SERVER_URL);
        
        // We can use the properties but can't extend AppConfig
        
        // 4. Value Object Example
        System.out.println("\n4. Value Object Example:");
        
        Address homeAddress = new Address("123 Main St", "Bangalore", "Karnataka", "560001");
        System.out.println("Address: " + homeAddress.getFullAddress());
        System.out.println("City: " + homeAddress.getCity());
        
        // Creating new object with different city
        Address officeAddress = homeAddress.withCity("Mumbai");
        System.out.println("Home: " + homeAddress.getCity()); // Original unchanged
        System.out.println("Office: " + officeAddress.getCity()); // New object
        
        // 5. Utility Class Example
        System.out.println("\n5. Utility Class Example:");
        
        String input = "java programming is fun";
        System.out.println("Original: " + input);
        System.out.println("Capitalized: " + StringUtils.capitalize(input));
        System.out.println("Word count: " + StringUtils.countWords(input));
        
        System.out.println("\n=== End of Final Class Demonstration ===");
    }
}

/**
 * Use Case 1: Security - Secure Math Operations
 * Final class to prevent attackers from subclassing and 
 * overriding with malicious implementations
 */
final class SecureMathOperations {
    // Private constructor prevents instantiation
    private SecureMathOperations() {
        // Prevent instantiation
    }
    
    public static double calculateCircleArea(double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius cannot be negative");
        }
        return Math.PI * radius * radius;
    }
    
    public static double add(double a, double b) {
        return a + b;
    }
    
    public static double subtract(double a, double b) {
        return a - b;
    }
    
    public static double multiply(double a, double b) {
        return a * b;
    }
    
    public static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
}

/**
 * Use Case 2: Configuration - App Configuration
 * Final class for application configuration that shouldn't be extended
 */
final class AppConfig {
    // Application constants
    public static final String APP_NAME = "Banking System";
    public static final int MAX_USERS = 1000;
    public static final String SERVER_URL = "https://api.bank.example.com";
    public static final int SESSION_TIMEOUT = 30; // minutes
    
    // Private constructor prevents instantiation
    private AppConfig() {
        // Prevent instantiation
    }
    
    // Safe to expose methods as the class cannot be subclassed
    public static boolean isProduction() {
        // Logic to determine environment
        return true;
    }
    
    public static String getDbConnectionString() {
        if (isProduction()) {
            return "prod-db-connection-string";
        } else {
            return "dev-db-connection-string";
        }
    }
}

/**
 * Use Case 3: Immutable Objects - Address Value Object
 * Final class ensures immutability and thread safety
 */
final class Address {
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;
    
    public Address(String street, String city, String state, String zipCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
    
    // All fields are final and there are no setters,
    // only getters that return the immutable values
    
    public String getStreet() {
        return street;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getState() {
        return state;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public String getFullAddress() {
        return street + ", " + city + ", " + state + " " + zipCode;
    }
    
    // To change a value, create a new object (immutable pattern)
    public Address withStreet(String newStreet) {
        return new Address(newStreet, this.city, this.state, this.zipCode);
    }
    
    public Address withCity(String newCity) {
        return new Address(this.street, newCity, this.state, this.zipCode);
    }
    
    public Address withState(String newState) {
        return new Address(this.street, this.city, newState, this.zipCode);
    }
    
    public Address withZipCode(String newZipCode) {
        return new Address(this.street, this.city, this.state, newZipCode);
    }
}

/**
 * Use Case 4: Utility Class - String Utilities
 * Final class for utility methods that shouldn't be extended
 */
final class StringUtils {
    // Private constructor prevents instantiation
    private StringUtils() {
        // Prevent instantiation
    }
    
    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        StringBuilder result = new StringBuilder();
        String[] words = input.split("\\s");
        
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1))
                      .append(" ");
            }
        }
        
        return result.toString().trim();
    }
    
    public static int countWords(String input) {
        if (input == null || input.trim().isEmpty()) {
            return 0;
        }
        
        return input.trim().split("\\s+").length;
    }
    
    public static boolean isNullOrEmpty(String input) {
        return input == null || input.isEmpty();
    }
    
    public static String reverse(String input) {
        if (input == null) {
            return null;
        }
        
        return new StringBuilder(input).reverse().toString();
    }
}
