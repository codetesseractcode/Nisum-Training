import java.util.*;
import java.util.stream.Collectors;

// Problem 8: Group employees by gender and get the count for each group.
public class Problem8 {
    public static void main(String[] args) {
        List<Employee> employees = createEmployeeList();
        
        Map<String, Long> genderCounts = employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
                
        System.out.println("Gender counts: " + genderCounts);
    }
      private static List<Employee> createEmployeeList() {
        List<Employee> employees = new ArrayList<>();
        
        employees.add(new Employee(1, "Aamir", "Khan", "IT", "MALE", 75000.0, "aamir.khan@company.com", 
                new Address("123 Main St", "Mumbai", "India", "400001")));
        employees.add(new Employee(2, "Deepika", "Padukone", "HR", "FEMALE", 65000.0, "deepika.padukone@company.com", 
                new Address("456 Oak Ave", "Bangalore", "India", "560001")));
        employees.add(new Employee(3, "Ajay", "Devgn", "IT", "MALE", 80000.0, "ajay.devgn@company.com", 
                new Address("789 Pine St", "Delhi", "India", "110001")));
        employees.add(new Employee(4, "Alia", "Bhatt", "Finance", "FEMALE", 70000.0, "alia.bhatt@company.com", 
                new Address("321 Elm St", "Chennai", "India", "600001")));
        employees.add(new Employee(5, "Varun", "Dhawan", "IT", "MALE", 85000.0, "varun.dhawan@company.com", 
                new Address("654 Maple Ave", "Hyderabad", "India", "500001")));
        
        return employees;
    }
}
