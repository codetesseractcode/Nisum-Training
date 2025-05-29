import java.util.*;
import java.util.stream.Collectors;

// Problem 2: Create a Map<String, Long> where key = department name, value = count of employees in that department.
public class Problem2 {
    public static void main(String[] args) {
        List<Employee> employees = createEmployeeList();
        
        Map<String, Long> departmentCounts = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
                
        System.out.println("Department counts: " + departmentCounts);
    }
      private static List<Employee> createEmployeeList() {
        List<Employee> employees = new ArrayList<>();
        
        employees.add(new Employee(1, "Rahul", "Sharma", "IT", "MALE", 75000.0, "rahul.sharma@company.com", 
                new Address("123 Main St", "New Delhi", "India", "110001")));
        employees.add(new Employee(2, "Priya", "Patel", "HR", "FEMALE", 65000.0, "priya.patel@company.com", 
                new Address("456 Oak Ave", "Mumbai", "India", "400001")));
        employees.add(new Employee(3, "Vikram", "Singh", "IT", "MALE", 80000.0, "vikram.singh@company.com", 
                new Address("789 Pine St", "Bangalore", "India", "560001")));
        employees.add(new Employee(4, "Neha", "Gupta", "Finance", "FEMALE", 70000.0, "neha.gupta@company.com", 
                new Address("321 Elm St", "Chennai", "India", "600001")));
        
        return employees;
    }
}
