import java.util.*;
import java.util.stream.Collectors;

// Problem 15: Return a Map<String, Address> where key = firstName + lastName, value = address object.
public class Problem15 {
    public static void main(String[] args) {
        List<Employee> employees = createEmployeeList();
        
        Map<String, Address> nameAddressMap = employees.stream()
                .filter(emp -> emp.getAddress().isPresent())
                .collect(Collectors.toMap(
                        emp -> emp.getFirstName() + emp.getLastName(),
                        emp -> emp.getAddress().get(),
                        (existing, replacement) -> existing // In case of duplicate keys, keep existing value
                ));
        
        System.out.println("Employee name to address mapping:");
        nameAddressMap.forEach((name, address) -> 
            System.out.println(name + " -> " + address.getCity() + ", " + address.getCountry())
        );
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
        employees.add(new Employee(5, "Divya", "Sharma", "HR", "FEMALE", 60000.0, null, null)); // No address
        
        return employees;
    }
}
