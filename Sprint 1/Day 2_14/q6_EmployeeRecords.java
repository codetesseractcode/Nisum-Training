import java.util.TreeSet;

class Employee implements Comparable<Employee> {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("ID: %-5d Name: %-15s Department: %-12s Salary: $%.2f", 
                            id, name, department, salary);
    }

    @Override
    public int compareTo(Employee other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

public class q6_EmployeeRecords {
    private TreeSet<Employee> employees;

    public q6_EmployeeRecords() {
        employees = new TreeSet<>();
    }

    public void addEmployee(int id, String name, String department, double salary) {
        Employee newEmployee = new Employee(id, name, department, salary);
        employees.add(newEmployee);
    }

    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees in the records.");
            return;
        }

        System.out.println("Employee Records (Alphabetical by Name):");
        System.out.println("------------------------------------------------------------");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
        System.out.println("------------------------------------------------------------");
    }    public static void main(String[] args) {
        q6_EmployeeRecords records = new q6_EmployeeRecords();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Employee Records System =====");
        
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a new employee");
            System.out.println("2. Display all employees (sorted by name)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");
            
            int choice = 0;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
            } catch (Exception e) {
                scanner.nextLine(); // Clear the buffer
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    System.out.print("Enter employee ID: ");
                    int id = 0;
                    try {
                        id = scanner.nextInt();
                        scanner.nextLine(); // Clear the buffer
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid ID. Employee not added.");
                        continue;
                    }
                    
                    System.out.print("Enter employee name: ");
                    String name = scanner.nextLine();
                    
                    System.out.print("Enter department: ");
                    String department = scanner.nextLine();
                    
                    System.out.print("Enter salary: $");
                    double salary = 0.0;
                    try {
                        salary = scanner.nextDouble();
                        scanner.nextLine(); // Clear the buffer
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid salary. Employee not added.");
                        continue;
                    }
                    
                    records.addEmployee(id, name, department, salary);
                    System.out.println("Employee added successfully.");
                    break;
                    
                case 2:
                    records.displayEmployees();
                    break;
                    
                case 3:
                    running = false;
                    System.out.println("Exiting Employee Records System.");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}
