package p31_packages.banking;

/**
 * Customer class in the banking package
 */
public class Customer {
    private String name;
    private String email;
    private String phone;
    
    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void displayInfo() {
        System.out.println("Customer: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
    }
}
