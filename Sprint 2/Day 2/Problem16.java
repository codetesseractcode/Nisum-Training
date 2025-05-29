import java.util.*;
import java.util.stream.Collectors;

// Problem 16: Use findAny() and findFirst() to fetch any or the first employee from a list.
public class Problem16 {
    public static void main(String[] args) {
        List<Employee> employees = createEmployeeList();
        
        Optional<Employee> anyEmployee = employees.stream().findAny();
        Optional<Employee> firstEmployee = employees.stream().findFirst();
        
        System.out.println("Using findAny():");
        anyEmployee.ifPresent(emp -> System.out.println(emp.getFullName() + " (ID: " + emp.getEmpId() + ")"));
        
        System.out.println("\nUsing findFirst():");
        firstEmployee.ifPresent(emp -> System.out.println(emp.getFullName() + " (ID: " + emp.getEmpId() + ")"));
        
        // Demonstrating difference with parallel streams
        System.out.println("\nWith parallel stream:");
        for (int i = 0; i < 5; i++) {
            Optional<Employee> anyEmployeeParallel = employees.parallelStream().findAny();
            System.out.println("findAny() run " + (i+1) + ": " + 
                anyEmployeeParallel.map(emp -> emp.getFullName() + " (ID: " + emp.getEmpId() + ")").orElse("None"));
        }
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
        
        return employees;
    }
}
