import java.util.*;

// Problem 11: Use Optional in the Employee class for optional fields and return employee details using Optional getters.
public class Problem11 {    public static void main(String[] args) {
        Employee employeeWithAllDetails = new Employee(1, "Abhishek", "Bachchan", "IT", "MALE", 75000.0, 
                "abhishek.bachchan@company.com", new Address("123 Main St", "Mumbai", "India", "400001"));
                
        Employee employeeWithMissingDetails = new Employee(2, "Aishwarya", "Rai", "HR", "FEMALE", 65000.0);
        
        System.out.println("Employee with all details:");
        printEmployeeDetails(employeeWithAllDetails);
        
        System.out.println("\nEmployee with missing details:");
        printEmployeeDetails(employeeWithMissingDetails);
    }
    
    private static void printEmployeeDetails(Employee employee) {
        String email = employee.getEmail().orElse("No email provided");
        String address = employee.getAddress().map(Address::toString).orElse("No address provided");
        
        System.out.println("Employee: " + employee.getFullName());
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
    }
}
