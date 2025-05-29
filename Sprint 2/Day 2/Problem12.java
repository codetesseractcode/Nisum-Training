import java.util.*;

// Problem 12: If an employee doesn't have an address, return a default address in their details.
public class Problem12 {
    public static void main(String[] args) {        Employee employeeWithAddress = new Employee(1, "Rohit", "Sharma", "IT", "MALE", 75000.0, 
                "rohit.sharma@company.com", new Address("123 MG Road", "Mumbai", "India", "400001"));
                
        Employee employeeWithoutAddress = new Employee(2, "Priya", "Patel", "HR", "FEMALE", 65000.0);
        
        System.out.println("Employee with address:");
        printEmployeeWithDefaultAddress(employeeWithAddress);
        
        System.out.println("\nEmployee without address (default will be used):");
        printEmployeeWithDefaultAddress(employeeWithoutAddress);
    }
    
    private static void printEmployeeWithDefaultAddress(Employee employee) {
        Address defaultAddress = new Address("Default Street", "Default City", "India", "000000");
        Address address = employee.getAddress().orElse(defaultAddress);
        
        System.out.println("Employee: " + employee.getFullName());
        System.out.println("Address: " + address);
    }
}
