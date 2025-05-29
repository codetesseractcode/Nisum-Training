import java.util.*;
import java.util.stream.Collectors;

// Problem 17: Use anyMatch, allMatch, and noneMatch to check conditions like: any employee from HR? 
// all employees have email? no employee has null name?
public class Problem17 {
    public static void main(String[] args) {
        List<Employee> employees = createEmployeeList();
        
        // 1. Check if any employee is from HR department
        boolean anyFromHR = employees.stream()
                .anyMatch(emp -> "HR".equals(emp.getDepartment()));
        System.out.println("Any employee from HR department? " + anyFromHR);
        
        // 2. Check if all employees have an email
        boolean allHaveEmail = employees.stream()
                .allMatch(emp -> emp.getEmail().isPresent());
        System.out.println("All employees have email? " + allHaveEmail);
        
        // 3. Check if no employee has a null name
        boolean noNullName = employees.stream()
                .noneMatch(emp -> emp.getFirstName() == null || emp.getLastName() == null);
        System.out.println("No employee has null name? " + noNullName);
        
        // 4. Additional example: Check if any employee has a salary over $80,000
        boolean anySalaryOver80K = employees.stream()
                .anyMatch(emp -> emp.getSalary() > 80000);
        System.out.println("Any employee with salary over $80,000? " + anySalaryOver80K);
    }
    
    private static List<Employee> createEmployeeList() {
        List<Employee> employees = new ArrayList<>();
          employees.add(new Employee(1, "Arjun", "Reddy", "IT", "MALE", 75000.0, "arjun.reddy@company.com", 
                new Address("123 MG Road", "Mumbai", "India", "400001")));
        employees.add(new Employee(2, "Priya", "Patel", "HR", "FEMALE", 65000.0, "priya.patel@company.com", 
                new Address("456 Anna Salai", "Chennai", "India", "600001")));
        employees.add(new Employee(3, "Vikram", "Singh", "IT", "MALE", 80000.0, "vikram.singh@company.com", 
                new Address("789 Brigade Road", "Bangalore", "India", "560001")));
        employees.add(new Employee(4, "Neha", "Gupta", "Finance", "FEMALE", 70000.0, "neha.gupta@company.com", 
                new Address("321 Connaught Place", "Delhi", "India", "110001")));
        employees.add(new Employee(5, "Akshay", "Kumar", "IT", "MALE", 85000.0, "akshay.kumar@company.com", 
                new Address("654 Park Street", "Kolkata", "India", "700001")));
        employees.add(new Employee(6, "Divya", "Sharma", "HR", "FEMALE", 60000.0, null, null)); // No email and no address
        
        return employees;
    }
}
