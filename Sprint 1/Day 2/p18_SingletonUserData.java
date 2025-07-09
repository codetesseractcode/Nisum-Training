/*
 18. Write a java program to create and display user data.
     User object must be created once. Use Singleton design pattern.
*/

import java.util.Scanner;

public class p18_SingletonUserData 
{
    public static void main(String[] args) 
    {
        System.out.println("=== User Data Management (Singleton Pattern) ===\n");
        
        // Try to get the user instance - first time will create it
        System.out.println("First attempt to access the User:");
        User user = User.getInstance();
        
        // Set user data
        user.setUserData("John Doe", "john.doe@example.com", 30);
        
        // Display user data
        System.out.println("\nDisplay user data after first setting:");
        user.displayUserInfo();
        
        // Try to get another instance - should return the same object
        System.out.println("\nSecond attempt to access the User (should be same instance):");
        User sameUser = User.getInstance();
        
        // Verify it's the same instance
        System.out.println("Is it the same User object? " + (user == sameUser));
        
        // Update user data through the second reference
        System.out.println("\nUpdating user data through second reference:");
        sameUser.setUserData("John Smith", "john.smith@example.com", 31);
        
        // Display user data again - should show updated info
        System.out.println("\nDisplay user data after update:");
        user.displayUserInfo();
        
        // Demonstrate interactive update
        System.out.println("\nInteractive update of user data:");
        updateUserInteractively();
        
        // Display final user data
        System.out.println("\nFinal user data:");
        User.getInstance().displayUserInfo();
    }
    
    // Method to update user data interactively
    private static void updateUserInteractively() 
    {
        Scanner scanner = new Scanner(System.in);
        User user = User.getInstance();
        
        System.out.println("Current user name: " + user.getName());
        System.out.print("Enter new name (or press Enter to keep current): ");
        String name = scanner.nextLine().trim();
        
        System.out.println("Current user email: " + user.getEmail());
        System.out.print("Enter new email (or press Enter to keep current): ");
        String email = scanner.nextLine().trim();
        
        System.out.println("Current user age: " + user.getAge());
        System.out.print("Enter new age (or 0 to keep current): ");
        int age = 0;
        try {
            String ageInput = scanner.nextLine().trim();
            if (!ageInput.isEmpty()) {
                age = Integer.parseInt(ageInput);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid age input, keeping current age.");
        }
        
        // Update only non-empty values
        if (!name.isEmpty() || !email.isEmpty() || age > 0) {
            String updatedName = name.isEmpty() ? user.getName() : name;
            String updatedEmail = email.isEmpty() ? user.getEmail() : email;
            int updatedAge = age == 0 ? user.getAge() : age;
            
            user.setUserData(updatedName, updatedEmail, updatedAge);
            System.out.println("User data updated successfully.");
        } else {
            System.out.println("No changes made to user data.");
        }
    }
}

/**
 * Singleton User class
 * This ensures only one instance of User is created
 */
class User 
{
    // Private static instance of the class
    private static User instance;
    
    // Private instance variables
    private String name;
    private String email;
    private int age;
    private long creationTime;
    
    // Private constructor to prevent instantiation from outside
    private User() 
    {
        System.out.println("Creating new User instance...");
        this.creationTime = System.currentTimeMillis();
    }
    
    /**
     * Static method to get the singleton instance
     * Uses lazy initialization (creates instance only when first needed)
     */
    public static synchronized User getInstance() 
    {
        if (instance == null) 
        {
            instance = new User();
        }
        return instance;
    }
    
    /**
     * Method to set user data
     */
    public void setUserData(String name, String email, int age) 
    {
        this.name = name;
        this.email = email;
        
        if (age > 0) 
        {
            this.age = age;
        } 
        else 
        {
            System.out.println("Warning: Invalid age provided. Age must be positive.");
        }
    }
    
    /**
     * Method to display user information
     */
    public void displayUserInfo() 
    {
        System.out.println("--- User Information ---");
        System.out.println("Name: " + (name != null ? name : "Not set"));
        System.out.println("Email: " + (email != null ? email : "Not set"));
        System.out.println("Age: " + (age > 0 ? age : "Not set"));
        System.out.println("Instance created at: " + new java.util.Date(creationTime));
        System.out.println("Instance age: " + (System.currentTimeMillis() - creationTime) + " ms");
    }
    
    /**
     * Getters for user properties
     */
    public String getName() 
    {
        return name;
    }
    
    public String getEmail() 
    {
        return email;
    }
    
    public int getAge() 
    {
        return age;
    }
    
    /**
     * Override clone method to prevent cloning of singleton
     */
    @Override
    protected Object clone() throws CloneNotSupportedException 
    {
        throw new CloneNotSupportedException("Cloning of User singleton not allowed");
    }
}
