import java.util.*;
import java.util.stream.Collectors;

// Problem 9: Separate employees into male and female, and return a string like: MALE: [John-Mike], FEMALE: [Alice-Lily].
public class Problem9 {
    public static void main(String[] args) {
        List<Employee> employees = createEmployeeList();
        
        Map<String, List<Employee>> genderMap = employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender));
        
        StringBuilder result = new StringBuilder();
        
        genderMap.forEach((gender, empList) -> {
            String names = empList.stream()
                    .map(Employee::getFirstName)
                    .collect(Collectors.joining("-"));
            result.append(gender.toUpperCase()).append(": [").append(names).append("] ");
        });
        
        System.out.println(result.toString().trim());
    }
    
    private static List<Employee> createEmployeeList() {
        List<Employee> employees = new ArrayList<>();
        
        employees.add(new Employee(1, "Ranbir", "Kapoor", "IT", "MALE", 75000.0, "ranbir.kapoor@company.com", 
                new Address("123 Main St", "Mumbai", "India", "400001")));
        employees.add(new Employee(2, "Anushka", "Sharma", "HR", "FEMALE", 65000.0, "anushka.sharma@company.com", 
                new Address("456 Oak Ave", "Delhi", "India", "110001")));
        employees.add(new Employee(3, "Shahid", "Kapoor", "IT", "MALE", 80000.0, "shahid.kapoor@company.com", 
                new Address("789 Pine St", "Bangalore", "India", "560001")));
        employees.add(new Employee(4, "Shruti", "Hassan", "Finance", "FEMALE", 70000.0, "shruti.hassan@company.com", 
                new Address("321 Elm St", "Chennai", "India", "600001")));
        employees.add(new Employee(5, "Ayushmann", "Khurrana", "IT", "MALE", 85000.0, "ayushmann.khurrana@company.com", 
                new Address("654 Maple Ave", "Hyderabad", "India", "500001")));
        employees.add(new Employee(6, "Kiara", "Advani", "HR", "FEMALE", 60000.0, null, null));
        
        return employees;
    }
}
