import java.util.*;
import java.util.stream.Collectors;

// Problem 10: Sort employees in ascending order based on their salary.
public class Problem10 {
    public static void main(String[] args) {
        List<Employee> employees = createEmployeeList();
        
        List<Employee> sortedEmployees = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary))
                .collect(Collectors.toList());
                
        System.out.println("Employees sorted by salary (ascending):");
        sortedEmployees.forEach(emp -> 
            System.out.println(emp.getFullName() + " - $" + emp.getSalary())
        );
    }
      private static List<Employee> createEmployeeList() {
        List<Employee> employees = new ArrayList<>();
        
        employees.add(new Employee(1, "Ranveer", "Singh", "IT", "MALE", 75000.0, "ranveer.singh@company.com", 
                new Address("123 Main St", "Mumbai", "India", "400001")));
        employees.add(new Employee(2, "Sonakshi", "Sinha", "HR", "FEMALE", 65000.0, "sonakshi.sinha@company.com", 
                new Address("456 Oak Ave", "Delhi", "India", "110001")));
        employees.add(new Employee(3, "Siddharth", "Malhotra", "IT", "MALE", 80000.0, "siddharth.malhotra@company.com", 
                new Address("789 Pine St", "Bangalore", "India", "560001")));
        employees.add(new Employee(4, "Parineeti", "Chopra", "Finance", "FEMALE", 70000.0, "parineeti.chopra@company.com", 
                new Address("321 Elm St", "Chennai", "India", "600001")));
        employees.add(new Employee(5, "Vicky", "Kaushal", "IT", "MALE", 85000.0, "vicky.kaushal@company.com", 
                new Address("654 Maple Ave", "Hyderabad", "India", "500001")));
        
        return employees;
    }
}
