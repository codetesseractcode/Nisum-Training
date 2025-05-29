import java.util.*;
import java.util.stream.Collectors;

// Problem 3: Given a Map<String, List<Employee>>, return a list of employees whose first or last name contains 
// a search string (case-insensitive).
public class Problem3 {    public static void main(String[] args) {
        List<Employee> employees = createEmployeeList();
        Map<String, List<Employee>> departmentEmployeeMap = createDepartmentEmployeeMap(employees);
        
        String searchString = "kumar";
        
        List<Employee> matchingEmployees = departmentEmployeeMap.values().stream()
                .flatMap(List::stream)
                .filter(emp -> emp.getFirstName().toLowerCase().contains(searchString.toLowerCase()) ||
                              emp.getLastName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
                
        System.out.println("Employees with name containing '" + searchString + "': ");
        matchingEmployees.forEach(System.out::println);
    }
      private static List<Employee> createEmployeeList() {
        List<Employee> employees = new ArrayList<>();
        
        employees.add(new Employee(1, "Raj", "Kumar", "IT", "MALE", 75000.0, "raj.kumar@company.com", 
                new Address("123 Main St", "New Delhi", "India", "110001")));
        employees.add(new Employee(2, "Ananya", "Verma", "HR", "FEMALE", 65000.0, "ananya.verma@company.com", 
                new Address("456 Oak Ave", "Mumbai", "India", "400001")));
        employees.add(new Employee(3, "Aditya", "Kumari", "IT", "MALE", 80000.0, "aditya.kumari@company.com", 
                new Address("789 Pine St", "Bangalore", "India", "560001")));
        
        return employees;
    }
    
    private static Map<String, List<Employee>> createDepartmentEmployeeMap(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }
}
