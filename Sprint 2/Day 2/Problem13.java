import java.util.*;
import java.util.stream.Collectors;

// Problem 13: If department is "IT", increase the salary of those employees using Optional.ifPresent.
public class Problem13 {
    public static void main(String[] args) {
        List<Employee> employees = createEmployeeList();
        
        System.out.println("Salaries before increase:");
        employees.forEach(emp -> System.out.println(emp.getFullName() + " (" + emp.getDepartment() + "): $" + emp.getSalary()));
        
        // Increase salary of IT employees by 10%
        employees.stream()
                .filter(emp -> "IT".equals(emp.getDepartment()))
                .forEach(emp -> {
                    Optional.of(emp).ifPresent(e -> e.setSalary(e.getSalary() * 1.1));
                });
        
        System.out.println("\nSalaries after increasing IT employees' salaries by 10%:");
        employees.forEach(emp -> System.out.println(emp.getFullName() + " (" + emp.getDepartment() + "): $" + emp.getSalary()));
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
