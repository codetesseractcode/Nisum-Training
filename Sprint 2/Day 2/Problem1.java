import java.util.*;
import java.util.stream.Collectors;

// Problem 1: Get the full name (firstName + lastName) of the first employee in the list.
public class Problem1 {
    public static void main(String[] args) {
        List<Employee> employees = createEmployeeList();
        
        String fullName = employees.stream()
                .findFirst()
                .map(Employee::getFullName)
                .orElse("No employee found");
                
        System.out.println("First employee's full name: " + fullName);
    }
    
    private static List<Employee> createEmployeeList() {
        List<Employee> employees = new ArrayList<>();
        
        employees.add(new Employee(1, "Ayusman", "Pradhan", "IT", "MALE", 75000.0, "ayusman.pradhan@company.com", 
                new Address("123 Main St", "New York", "USA", "10001")));
        employees.add(new Employee(2, "Janesh", "Singh", "HR", "FEMALE", 65000.0, "janesh.singh@company.com", 
                new Address("456 Oak Ave", "Los Angeles", "USA", "90001")));
        // Add more employees as needed
        
        return employees;
    }
}
