/**
 * Main program to demonstrate how to access packages from other packages
 * This file must be saved in the main directory
 * 
 * How packages work:
 * 1. Packages organize classes into namespaces
 * 2. They help avoid naming conflicts
 * 3. They provide access control between different parts of an application
 * 4. Different ways to access classes from other packages:
 *    - Using fully qualified name
 *    - Using import statement for specific classes
 *    - Using import statement with wildcard
 */

// Import specific classes from a package
import p31_packages.banking.Account;
import p31_packages.banking.Customer;

// Import all classes from a package using wildcard
import p31_packages.utilities.*;

// Notice that we don't import p31_packages.products.Product yet
// We'll use fully qualified name instead

public class p31_PackageAccess {
    public static void main(String[] args) {
        System.out.println("=== Package Access Demonstration ===\n");
        
        // 1. Using imported classes from banking package
        System.out.println("1. Using imported banking classes:");
        
        // Create customer and account
        Customer customer = new Customer("Amit Kumar", "amit@example.com", "9876543210");
        Account savingsAccount = new Account("SB10001", customer, 25000.0);
        
        // Display account info
        savingsAccount.displayInfo();
        
        // Perform account operations
        savingsAccount.deposit(10000);
        savingsAccount.withdraw(5000);
        System.out.println();
        
        // 2. Using imported classes from utilities package with wildcard import
        System.out.println("2. Using imported utility classes (wildcard import):");
        DateFormatter dateFormatter = new DateFormatter();
        System.out.println("Formatted date: " + dateFormatter.formatCurrentDate());

        // Calculator class is from p31_packages.utilities (see Calculator.java in that package)
        Calculator calculator = new Calculator();
        System.out.println("Addition: " + calculator.add(56, 44));
        System.out.println("Multiplication: " + calculator.multiply(10, 5));

        // Using StringUtils static methods
        System.out.println("Reversed text: " + StringUtils.reverse("Hello India"));
        System.out.println("Capitalized text: " + StringUtils.capitalize("welcome to java"));
        System.out.println();
        
        // 3. Using fully qualified name (without import)
        System.out.println("3. Using fully qualified class name (without import):");
        
        // Create a product using fully qualified name
        p31_packages.products.Product laptop = new p31_packages.products.Product(
            "LP1001", "Dell Inspiron", 45000.0);
        laptop.displayInfo();
        
        // Use another product class method
        System.out.println("Discounted price: " + 
            laptop.calculateDiscountedPrice(10) + " INR");
        System.out.println();
        
        // 4. Accessing static members from another package
        System.out.println("4. Accessing static members from another package:");
        
        // Static methods can be accessed directly from the class
        System.out.println("Current tax rate: " + 
            p31_packages.utilities.Constants.TAX_RATE + "%");
        System.out.println("Bank name: " + 
            p31_packages.banking.BankDetails.BANK_NAME);
          // Call static method from another package
        String formattedCurrency = p31_packages.utilities.CurrencyFormatter.formatCurrency(5499.99);
        System.out.println("Formatted currency: " + formattedCurrency);
        
        // Call static method with city parameter
        System.out.println("Branch code for Mumbai: " + 
            p31_packages.banking.BankDetails.getBranchCode("Mumbai"));
        
        System.out.println("\n=== End of Package Access Demonstration ===");
    }
}
