import java.util.*;
import java.util.stream.Collectors;

// Problem 7: Given a list of employee IDs, return the matching Employee objects from a larger employee list.
public class Problem7 {
    public static void main(String[] args) {
        List<Employee> employees = createEmployeeList();
        List<Integer> employeeIds = Arrays.asList(1, 3, 5, 7, 9);
        
        Set<Integer> idSet = new HashSet<>(employeeIds); // Convert to Set for O(1) lookup
        List<Employee> matchingEmployees = employees.stream()
                .filter(emp -> idSet.contains(emp.getEmpId()))
                .collect(Collectors.toList());
                
        System.out.println("Employees with IDs " + employeeIds + ":");
        matchingEmployees.forEach(System.out::println);
    }
      private static List<Employee> createEmployeeList() {
        List<Employee> employees = new ArrayList<>();
        
        employees.add(new Employee(1, "Rajesh", "Khanna", "IT", "MALE", 75000.0, "rajesh.khanna@company.com", 
                new Address("123 Main St", "Mumbai", "India", "400001")));
        employees.add(new Employee(2, "Aishwarya", "Rai", "HR", "FEMALE", 65000.0, "aishwarya.rai@company.com", 
                new Address("456 Oak Ave", "Delhi", "India", "110001")));
        employees.add(new Employee(3, "Amitabh", "Bachchan", "IT", "MALE", 80000.0, "amitabh.bachchan@company.com", 
                new Address("789 Pine St", "Kolkata", "India", "700001")));
        employees.add(new Employee(4, "Madhuri", "Dixit", "Finance", "FEMALE", 70000.0, "madhuri.dixit@company.com", 
                new Address("321 Elm St", "Chennai", "India", "600001")));
        employees.add(new Employee(5, "Shah Rukh", "Khan", "IT", "MALE", 85000.0, "shahrukh.khan@company.com", 
                new Address("654 Maple Ave", "Bangalore", "India", "560001")));
                employees.add(new Employee(6, "Kajol", "Devgn", "HR", "FEMALE", 60000.0, null, null));
        employees.add(new Employee(7, "Salman", "Khan", "Finance", "MALE", 72000.0, "salman.khan@company.com", 
                new Address("987 Cedar St", "Pune", "India", "411001")));
        employees.add(new Employee(8, "Katrina", "Kaif", "IT", "FEMALE", 78000.0, "katrina.kaif@company.com", 
                new Address("147 Birch Ave", "Hyderabad", "India", "500001")));
        employees.add(new Employee(9, "Hrithik", "Roshan", "HR", "MALE", 63000.0, "hrithik.roshan@company.com", 
                new Address("258 Spruce St", "Ahmedabad", "India", "380001")));
        employees.add(new Employee(10, "Sonam", "Kapoor", "Finance", "FEMALE", 74000.0, "sonam.kapoor@company.com", 
                new Address("369 Ash Ave", "Jaipur", "India", "302001")));
        
        return employees;
    }
}
