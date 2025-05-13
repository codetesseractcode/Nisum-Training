/*
 5. Create a class UserDetails with variables like name,id,email,password,creditCard,
    creditCardbalance where in name,id,email of any user should be accessible to all 
    but not creditcard and creditcardbalance.
*/

public class p5_UserDetails 
{
    public static void main(String[] args) 
    {
        // Create a new user
        UserDetails user = new UserDetails("John Doe", "U12345", "john.doe@example.com", 
                                           "password123", "1234-5678-9012-3456", 5000.0);
        
        // Access public information
        System.out.println("=== Public User Information ===");
        System.out.println("Name: " + user.name);
        System.out.println("ID: " + user.id);
        System.out.println("Email: " + user.email);
        
        // The following would cause compile errors because these are private:
        // System.out.println("Credit Card: " + user.creditCard);
        // System.out.println("Credit Card Balance: " + user.creditCardBalance);
        
        // Access private information through public methods
        System.out.println("\n=== Accessing Private Information Through Methods ===");
        System.out.println("Last 4 digits of Credit Card: " + user.getLastFourDigitsOfCreditCard());
        System.out.println("Has sufficient funds for $2000 purchase: " + user.hasSufficientFunds(2000));
        
        // Make a purchase
        boolean purchaseSuccess = user.makePurchase(1500);
        System.out.println("\n=== Purchase Transaction ===");
        System.out.println("Purchase of $1500 successful: " + purchaseSuccess);
        
        // Display balance securely
        System.out.println("Available balance: $" + user.getAvailableBalance());
        
        // Verify password
        System.out.println("\n=== Password Verification ===");
        System.out.println("Password verification: " + user.verifyPassword("password123"));
    }
}

class UserDetails 
{
    // Public variables accessible to all
    public String name;
    public String id;
    public String email;
    
    // Private variables not directly accessible outside this class
    private String password;
    private String creditCard;
    private double creditCardBalance;
    
    // Constructor to initialize all variables
    public UserDetails(String name, String id, String email, 
                       String password, String creditCard, double creditCardBalance) 
    {
        this.name = name;
        this.id = id;
        this.email = email;
        this.password = password;
        this.creditCard = creditCard;
        this.creditCardBalance = creditCardBalance;
    }
    
    // Method to get last four digits of credit card (safe to expose)
    public String getLastFourDigitsOfCreditCard() 
    {
        if (creditCard.length() >= 4) 
        {
            return "xxxx-xxxx-xxxx-" + creditCard.substring(creditCard.length() - 4);
        } 
        else 
        {
            return "Invalid credit card format";
        }
    }
    
    // Method to check if user has sufficient funds
    public boolean hasSufficientFunds(double amount) 
    {
        return creditCardBalance >= amount;
    }
    
    // Method to make a purchase
    public boolean makePurchase(double amount) 
    {
        if (hasSufficientFunds(amount)) 
        {
            creditCardBalance -= amount;
            return true;
        } 
        else 
        {
            return false;
        }
    }
    
    // Method to get available balance without directly exposing the field
    public double getAvailableBalance() 
    {
        return creditCardBalance;
    }
    
    // Method to verify user's password
    public boolean verifyPassword(String inputPassword) 
    {
        return password.equals(inputPassword);
    }
}
