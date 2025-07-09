/*
 16. Create a class parent class Nisum and subclass Employee. 
     Access instance variables of Nisum from Employee without using super keyword.
*/

public class p16_InheritanceAccess 
{
    public static void main(String[] args) 
    {
        System.out.println("=== Accessing Parent Class Variables Without Super ===\n");
        
        // Create an Employee object
        Employee emp = new Employee("Narendra Modi", "E12345", "Software Engineer", 
                                   "Hyderabad", "India", 2000);
        
        // Display employee details which includes parent class variables
        emp.displayDetails();
        
        // Update some information
        System.out.println("\nUpdating employee location...");
        emp.updateLocation("Bangalore");
        
        // Display updated details
        System.out.println("\nAfter update:");
        emp.displayDetails();
    }
}

// Parent class Nisum
class Nisum 
{
    // Instance variables
    protected String companyLocation;
    protected String country;
    protected int establishedYear;
    
    // Constructor
    public Nisum(String companyLocation, String country, int establishedYear) 
    {
        this.companyLocation = companyLocation;
        this.country = country;
        this.establishedYear = establishedYear;
    }
    
    // Method to display company details
    public void displayCompanyDetails() 
    {
        System.out.println("Company: Nisum");
        System.out.println("Location: " + companyLocation);
        System.out.println("Country: " + country);
        System.out.println("Established: " + establishedYear);
    }
}

// Subclass Employee that extends Nisum
class Employee extends Nisum 
{
    // Instance variables specific to Employee
    private String employeeName;
    private String employeeId;
    private String designation;
    
    // Constructor
    public Employee(String employeeName, String employeeId, String designation,
                   String companyLocation, String country, int establishedYear) 
    {
        // Call parent class constructor
        super(companyLocation, country, establishedYear);
        
        // Initialize Employee specific variables
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.designation = designation;
    }
    
    // Method to display employee details - accessing parent variables directly
    public void displayDetails() 
    {
        System.out.println("--- Employee Details ---");
        System.out.println("Name: " + employeeName);
        System.out.println("ID: " + employeeId);
        System.out.println("Designation: " + designation);
        
        // Accessing parent class variables directly without super keyword
        // This works because these variables are protected in the parent class
        System.out.println("\n--- Company Information ---");
        System.out.println("Company: Nisum");
        System.out.println("Location: " + companyLocation);  // Direct access to parent variable
        System.out.println("Country: " + country);          // Direct access to parent variable
        System.out.println("Established: " + establishedYear); // Direct access to parent variable
    }
    
    // Method to update location - directly modifies parent class variable
    public void updateLocation(String newLocation) 
    {
        // Direct access to parent class variable without super
        companyLocation = newLocation;
    }
    
    // Method that demonstrates both direct access and super keyword access
    public void demonstrateDifferentAccesses() 
    {
        System.out.println("\nDirect access to parent variables:");
        System.out.println("Location: " + companyLocation);
        
        System.out.println("\nUsing super to call parent method:");
        super.displayCompanyDetails();
        
        // Note: We could also use super.companyLocation but it's not necessary
        // since we're directly accessing the protected variable
    }
}
