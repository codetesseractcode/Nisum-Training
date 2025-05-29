import java.util.*;
import java.util.stream.Collectors;

// Problem 6: From a list of 10 employees, get the 8th employee and return full name and department name.
public class Problem6 {
    public static void main(String[] args) {
        List<Employee> employees = createEmployeeList();
        
        String eighthEmployee = employees.stream()
                .skip(7) // Skip first 7 employees (0-indexed, so this gets the 8th)
                .findFirst()
                .map(emp -> emp.getFullName() + " - " + emp.getDepartment())
                .orElse("No 8th employee found");
                
        System.out.println("8th employee: " + eighthEmployee);
    }
      private static List<Employee> createEmployeeList() {
        List<Employee> employees = new ArrayList<>();
        
        employees.add(new Employee(1, "Rohit", "Sharma", "IT", "MALE", 75000.0, "rohit.sharma@company.com", 
                new Address("123 Main St", "Mumbai", "India", "400001")));
        employees.add(new Employee(2, "Deepika", "Padukone", "HR", "FEMALE", 65000.0, "deepika.padukone@company.com", 
                new Address("456 Oak Ave", "Bangalore", "India", "560001")));
        employees.add(new Employee(3, "Virat", "Kohli", "IT", "MALE", 80000.0, "virat.kohli@company.com", 
                new Address("789 Pine St", "Delhi", "India", "110001")));
        employees.add(new Employee(4, "Priyanka", "Chopra", "Finance", "FEMALE", 70000.0, "priyanka.chopra@company.com", 
                new Address("321 Elm St", "Mumbai", "India", "400001")));
        employees.add(new Employee(5, "Ranbir", "Kapoor", "IT", "MALE", 85000.0, "ranbir.kapoor@company.com", 
                new Address("654 Maple Ave", "Delhi", "India", "110001")));
        employees.add(new Employee(6, "Alia", "Bhatt", "HR", "FEMALE", 60000.0, null, null));
                employees.add(new Employee(7, "Akshay", "Kumar", "Finance", "MALE", 72000.0, "akshay.kumar@company.com", 
                new Address("987 Cedar St", "Chennai", "India", "600001")));
        employees.add(new Employee(8, "Shraddha", "Kapoor", "IT", "FEMALE", 78000.0, "shraddha.kapoor@company.com", 
                new Address("147 Birch Ave", "Hyderabad", "India", "500001")));
        employees.add(new Employee(9, "Ranveer", "Singh", "HR", "MALE", 63000.0, "ranveer.singh@company.com", 
                new Address("258 Spruce St", "Kolkata", "India", "700001")));
        employees.add(new Employee(10, "Kareena", "Kapoor", "Finance", "FEMALE", 74000.0, "kareena.kapoor@company.com", 
                new Address("369 Ash Ave", "Pune", "India", "411001")));
        
        return employees;
    }
}
