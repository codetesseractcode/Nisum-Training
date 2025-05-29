import java.util.Optional;

public class Employee {
    private int empId;
    private String firstName;
    private String lastName;
    private String department;
    private String gender;
    private double salary;
    private Optional<String> email;
    private Optional<Address> address;

    public Employee(int empId, String firstName, String lastName, String department, 
                   String gender, double salary, String email, Address address) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.gender = gender;
        this.salary = salary;
        this.email = Optional.ofNullable(email);
        this.address = Optional.ofNullable(address);
    }

    public Employee(int empId, String firstName, String lastName, String department, 
                   String gender, double salary) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.gender = gender;
        this.salary = salary;
        this.email = Optional.empty();
        this.address = Optional.empty();
    }

    public int getEmpId() { return empId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDepartment() { return department; }
    public String getGender() { return gender; }
    public double getSalary() { return salary; }
    public Optional<String> getEmail() { return email; }
    public Optional<Address> getAddress() { return address; }

    public void setEmpId(int empId) { this.empId = empId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDepartment(String department) { this.department = department; }
    public void setGender(String gender) { this.gender = gender; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setEmail(String email) { this.email = Optional.ofNullable(email); }
    public void setAddress(Address address) { this.address = Optional.ofNullable(address); }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", gender='" + gender + '\'' +
                ", salary=" + salary +
                ", email=" + email +
                ", address=" + address +
                '}';
    }
}
