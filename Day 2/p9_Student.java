/*
 9. Write a Java program for student class with basic student Information (name, address, section, 
    college, class, roll no). Here College and roll number must be unique and should be loaded once 
    throughout the class. Write a method to display student info.
*/

import java.util.HashMap;
import java.util.Map;

public class p9_Student 
{
    public static void main(String[] args) 
    {
        System.out.println("=== Student Information System ===\n");
        
        // Create several student objects
        Student student1 = new Student("Ayusman Pradhan", "Bhubneswar", "B-25", "Kiit University", "Computer Science", 22053415);
        Student student2 = new Student("Binaya B. Sahoo", "Delhi", "S-25", "Delhi University", "Physics", 22053408);
        
        // Try to create a student with duplicate roll number (should show error message)
        System.out.println("\nTrying to create student with duplicate roll number:");
        new Student("Bob Johnson", "789 Pine Rd", "C", "MIT", "Mathematics", 101);
        
        // Display information for valid students
        System.out.println("\nDisplaying Student Information:");
        student1.displayStudentInfo();
        student2.displayStudentInfo();
        
        // Display college statistics
        System.out.println("\nCollege Statistics:");
        Student.displayCollegeStatistics();
    }
}

class Student 
{
    // Instance variables for student information
    private String name;
    private String address;
    private String section;
    private String collegeCode; // References the unique college in the static map
    private String className;
    private int rollNo;
    
    // Static variables that need to be unique across all instances
    private static Map<String, String> colleges = new HashMap<>(); // Maps college code to college name
    private static Map<Integer, String> rollNumbers = new HashMap<>(); // Maps roll number to student name
    private static Map<String, Integer> collegeStudentCount = new HashMap<>(); // Count of students per college
    
    // Static initialization block
    static 
    {
        System.out.println("Initializing Student class...");
        System.out.println("Setting up college and roll number tracking system");
    }
    
    public Student(String name, String address, String section, String college, String className, int rollNo) 
    {
        // Check if the roll number is already used
        if (rollNumbers.containsKey(rollNo)) 
        {
            System.out.println("Error: Roll number " + rollNo + " is already assigned to " + 
                              rollNumbers.get(rollNo) + ". Student not created.");
            return;
        }
        
        // Set the instance variables
        this.name = name;
        this.address = address;
        this.section = section;
        this.className = className;
        this.rollNo = rollNo;
        
        // Generate a unique code for the college if not already present
        String collegeCode = generateCollegeCode(college);
        this.collegeCode = collegeCode;
        
        // Register this roll number as used
        rollNumbers.put(rollNo, name);
        
        // Update college student count
        collegeStudentCount.put(collegeCode, collegeStudentCount.getOrDefault(collegeCode, 0) + 1);
        
        System.out.println("Student " + name + " created with roll number " + rollNo);
    }
    
    // Generate a unique code for a college and store it if not exists
    private String generateCollegeCode(String collegeName) 
    {
        // Create a simple code from the first letters of each word in the college name
        String[] words = collegeName.split(" ");
        StringBuilder code = new StringBuilder();
        
        for (String word : words) 
        {
            if (!word.isEmpty()) 
            {
                code.append(word.charAt(0));
            }
        }
        
        String collegeCode = code.toString().toUpperCase();
        
        // Check if this college is already registered
        if (!colleges.containsValue(collegeName)) 
        {
            colleges.put(collegeCode, collegeName);
            collegeStudentCount.put(collegeCode, 0);
            System.out.println("Registered new college: " + collegeName + " with code: " + collegeCode);
        }
        
        return collegeCode;
    }
    
    // Method to display student information
    public void displayStudentInfo() 
    {
        if (name == null) 
        {
            System.out.println("Invalid student record.");
            return;
        }
        
        System.out.println("\n--- Student Details ---");
        System.out.println("Name: " + name);
        System.out.println("Roll Number: " + rollNo);
        System.out.println("Class: " + className);
        System.out.println("Section: " + section);
        System.out.println("College: " + colleges.get(collegeCode));
        System.out.println("Address: " + address);
    }
    
    // Static method to display college statistics
    public static void displayCollegeStatistics() 
    {
        System.out.println("--- College-wise Student Count ---");
        for (Map.Entry<String, String> entry : colleges.entrySet()) 
        {
            String collegeCode = entry.getKey();
            String collegeName = entry.getValue();
            int count = collegeStudentCount.getOrDefault(collegeCode, 0);
            
            System.out.println(collegeName + " (" + collegeCode + "): " + count + " students");
        }
    }
}
