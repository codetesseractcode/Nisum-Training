import java.util.*;
import java.util.stream.Collectors;

// Problem 5: Concatenate all employees' full names (firstName + lastName) separated by pipe (|).
public class Problem5 {
    public static void main(String[] args) {
        List<Employee> employees = createEmployeeList();
        
        String concatenatedNames = employees.stream()
                .map(Employee::getFullName)
                .collect(Collectors.joining("|"));
                
        System.out.println("Concatenated names: " + concatenatedNames);
    }
      private static List<Employee> createEmployeeList() {
        List<Employee> employees = new ArrayList<>();
        
        employees.add(new Employee(1, "Arjun", "Reddy", "IT", "MALE", 75000.0, "arjun.reddy@company.com", 
                new Address("123 Main St", "Hyderabad", "India", "500001")));
        employees.add(new Employee(2, "Meera", "Joshi", "HR", "FEMALE", 65000.0, "meera.joshi@company.com", 
                new Address("456 Oak Ave", "Pune", "India", "411001")));
        employees.add(new Employee(3, "Karan", "Malhotra", "IT", "MALE", 80000.0, "karan.malhotra@company.com", 
                new Address("789 Pine St", "Kolkata", "India", "700001")));
        employees.add(new Employee(4, "Nisha", "Kapoor", "Finance", "FEMALE", 70000.0, "nisha.kapoor@company.com", 
                new Address("321 Elm St", "Jaipur", "India", "302001")));
        
        return employees;
    }
}
