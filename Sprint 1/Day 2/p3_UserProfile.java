/*
 3.Create user profile with basic information and print the 
   user information after completion of creating the profile.
*/

import java.util.Scanner;

public class p3_UserProfile 
{
    public static void main(String[] args) 
    {
        // Create a UserProfileManager to handle profile operations
        UserProfileManager profileManager = new UserProfileManager();
        
        // Create a new user profile
        UserProfile userProfile = profileManager.createUserProfile();
        
        // Print the user information
        System.out.println("\n=== User Profile Information ===");
        userProfile.displayUserInfo();
    }
}

/*
 Class to represent a user profile with basic information
*/
class UserProfile 
{
    private String name;
    private int age;
    private String email;
    private String address;
    private String phoneNumber;
    
    // Getters and setters for user profile data
    public String getName() 
    {
        return name;
    }
    
    public void setName(String name) 
    {
        this.name = name;
    }
    
    public int getAge() 
    {
        return age;
    }
    
    public void setAge(int age) 
    {
        this.age = age;
    }
    
    public String getEmail() 
    {
        return email;
    }
    
    public void setEmail(String email) 
    {
        this.email = email;
    }
    
    public String getAddress() 
    {
        return address;
    }
    
    public void setAddress(String address) 
    {
        this.address = address;
    }
    
    public String getPhoneNumber() 
    {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }
    
    /*
     Display all user information in a formatted way
    */
    public void displayUserInfo() 
    {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
        System.out.println("Phone Number: " + phoneNumber);
    }
}

/*
 Manager class to handle operations related to user profiles
*/
class UserProfileManager 
{
    private Scanner scanner;
    
    public UserProfileManager() 
    {
        scanner = new Scanner(System.in);
    }
    
    /*
     Create a new user profile by collecting information from user input
     @return A completed UserProfile object
    */
    public UserProfile createUserProfile() 
    {
        UserProfile profile = new UserProfile();
        
        System.out.println("=== User Profile Creation ===");
        
        // Collect user information with validation
        // Name validation - cannot be empty
        String name;
        do 
        {
            System.out.print("Enter your name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) 
            {
                System.out.println("Name cannot be empty. Please try again.");
            }
        } 
        while (name.isEmpty());
        profile.setName(name);
        
        // Age validation
        System.out.print("Enter your age: ");
        profile.setAge(getValidIntInput());
        scanner.nextLine(); // Clear buffer
        
        // Email validation
        String email;
        do 
        {
            System.out.print("Enter your email: ");
            email = scanner.nextLine().trim();
            if (email.isEmpty()) 
            {
                System.out.println("Email cannot be empty. Please try again.");
            } 
            else if (!isValidEmail(email)) 
            {
                System.out.println("Invalid email format. Please enter a valid email (e.g., example@domain.com).");
                email = "";
            }
        } 
        while (email.isEmpty());
        profile.setEmail(email);
        
        // Address validation - cannot be empty
        String address;
        do 
        {
            System.out.print("Enter your address: ");
            address = scanner.nextLine().trim();
            if (address.isEmpty()) {
                System.out.println("Address cannot be empty. Please try again.");
            }
        } 
        while (address.isEmpty());
        profile.setAddress(address);
        
        // Phone validation
        String phoneNumber;
        do 
        {
            System.out.print("Enter your phone number: ");
            phoneNumber = scanner.nextLine().trim();
            if (phoneNumber.isEmpty()) 
            {
                System.out.println("Phone number cannot be empty. Please try again.");
            } else if (!isValidPhoneNumber(phoneNumber)) 
            {
                System.out.println("Invalid phone format. Please enter a valid phone number (10 digits).");
                phoneNumber = "";
            }
        } 
        while (phoneNumber.isEmpty());
        profile.setPhoneNumber(phoneNumber);
        
        System.out.println("Profile created successfully!");
        
        return profile;
    }
    /*
     Helper method to validate integer input
     @return A valid integer input
    */
    private int getValidIntInput() 
    {
        while (!scanner.hasNextInt()) 
        {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }
    
    /*
     Helper method to validate email format using simple regex
     @param email The email to validate
     @return true if the email is valid, false otherwise
    */
    private boolean isValidEmail(String email) 
    {
        // Simple email validation regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
    
    /*
     Helper method to validate phone number format
     @param phoneNumber The phone number to validate
     @return true if the phone number is valid, false otherwise
    */
    private boolean isValidPhoneNumber(String phoneNumber) 
    {
        // Remove any non-digit characters for validation
        String digitsOnly = phoneNumber.replaceAll("\\D", "");
        // Check if the phone number has 10 digits (standard US format)
        return digitsOnly.length() == 10;
    }
}
