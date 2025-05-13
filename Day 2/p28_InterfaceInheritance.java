/**
 * Program to demonstrate Interface Inheritance in Java
 * 
 * Interfaces can extend other interfaces, creating an inheritance hierarchy.
 * A class implementing a child interface must implement all methods from:
 * 1. The child interface
 * 2. All parent interfaces in the inheritance chain
 * 
 * This program shows interface inheritance with banking application example
 */

public class p28_InterfaceInheritance {
    public static void main(String[] args) {
        System.out.println("=== Interface Inheritance Demonstration ===\n");
        
        // Create a basic savings account
        System.out.println("1. Basic Savings Account:");
        SavingsAccount savingsAccount = new SavingsAccount("Anil Kumar", "SBIN00012345", 25000.0);
        savingsAccount.showAccountInfo();
        savingsAccount.deposit(10000.0);
        savingsAccount.withdraw(5000.0);
        savingsAccount.calculateInterest();
        System.out.println();
        
        // Create a net banking enabled account
        System.out.println("2. Net Banking Account:");
        NetBankingAccount netAccount = new NetBankingAccount("Sneha Reddy", "HDFC00056789", 50000.0, "sneha@email.com");
        netAccount.showAccountInfo();
        netAccount.enableNetBanking("Sneha123");
        netAccount.checkBalance();
        netAccount.transferFunds("ICICI00078965", 15000.0);
        netAccount.payUtilityBill("Electricity", "TNB123456", 2500.0);
        System.out.println();
        
        // Create a premium account with all features
        System.out.println("3. Premium Banking Account:");
        PremiumBankingAccount premiumAccount = new PremiumBankingAccount(
            "Rajesh Sharma", "AXIS00098765", 500000.0, "rajesh@email.com", 9876543210L);
        
        premiumAccount.showAccountInfo();
        premiumAccount.deposit(100000.0);
        premiumAccount.enableNetBanking("Rajesh456");
        premiumAccount.transferFunds("HDFC00012345", 25000.0);
        premiumAccount.requestLoan(1000000.0, 5);
        premiumAccount.provideFeedback("Excellent service!");
        premiumAccount.contactRelationshipManager("Need help with investments");
        
        System.out.println("\n=== End of Interface Inheritance Demonstration ===");
    }
}

// Base interface for banking operations
interface BankingOperations {
    void deposit(double amount);
    boolean withdraw(double amount);
    double checkBalance();
    void showAccountInfo();
}

// Interface for interest calculations, extending base operations
interface InterestBearing extends BankingOperations {
    void calculateInterest();
    void setInterestRate(double rate);
}

// Interface for online banking features
interface OnlineBanking extends BankingOperations {
    void enableNetBanking(String password);
    void transferFunds(String toAccountNumber, double amount);
    void payUtilityBill(String provider, String consumerNumber, double amount);
}

// Interface for premium banking features, inheriting from online banking
interface PremiumBanking extends OnlineBanking, InterestBearing {
    void requestLoan(double amount, int years);
    void provideFeedback(String feedback);
    void contactRelationshipManager(String message);
}

// Base account implementing interest bearing interface
class SavingsAccount implements InterestBearing {
    protected String accountHolderName;
    protected String accountNumber;
    protected double balance;
    protected double interestRate;
    
    public SavingsAccount(String accountHolderName, String accountNumber, double initialBalance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.interestRate = 4.5; // Default interest rate
    }
    
    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("₹" + amount + " deposited successfully");
            System.out.println("New balance: ₹" + balance);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }
    
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("₹" + amount + " withdrawn successfully");
            System.out.println("New balance: ₹" + balance);
            return true;
        } else {
            System.out.println("Withdrawal failed: Insufficient funds or invalid amount");
            return false;
        }
    }
    
    @Override
    public double checkBalance() {
        System.out.println("Current balance: ₹" + balance);
        return balance;
    }
    
    @Override
    public void showAccountInfo() {
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Current Balance: ₹" + balance);
        System.out.println("Interest Rate: " + interestRate + "%");
    }
    
    @Override
    public void calculateInterest() {
        double interest = balance * interestRate / 100;
        System.out.println("Annual interest at " + interestRate + "% would be: ₹" + interest);
    }
    
    @Override
    public void setInterestRate(double rate) {
        this.interestRate = rate;
        System.out.println("Interest rate set to " + rate + "%");
    }
}

// Net banking account implementing online banking interface
class NetBankingAccount implements OnlineBanking {
    protected String accountHolderName;
    protected String accountNumber;
    protected double balance;
    protected String email;
    protected boolean netBankingEnabled;
    
    public NetBankingAccount(String accountHolderName, String accountNumber, 
                           double initialBalance, String email) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.email = email;
        this.netBankingEnabled = false;
    }
    
    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("₹" + amount + " deposited successfully");
            System.out.println("New balance: ₹" + balance);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }
    
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("₹" + amount + " withdrawn successfully");
            System.out.println("New balance: ₹" + balance);
            return true;
        } else {
            System.out.println("Withdrawal failed: Insufficient funds or invalid amount");
            return false;
        }
    }
    
    @Override
    public double checkBalance() {
        System.out.println("Current balance: ₹" + balance);
        return balance;
    }
    
    @Override
    public void showAccountInfo() {
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Current Balance: ₹" + balance);
        System.out.println("Email: " + email);
        System.out.println("Net Banking Status: " + (netBankingEnabled ? "Enabled" : "Disabled"));
    }
    
    @Override
    public void enableNetBanking(String password) {
        if (password.length() >= 6) {
            netBankingEnabled = true;
            System.out.println("Net banking enabled for " + accountHolderName);
            System.out.println("Login credentials sent to: " + email);
        } else {
            System.out.println("Password must be at least 6 characters");
        }
    }
    
    @Override
    public void transferFunds(String toAccountNumber, double amount) {
        if (!netBankingEnabled) {
            System.out.println("Please enable net banking first");
            return;
        }
        
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("₹" + amount + " transferred to account: " + toAccountNumber);
            System.out.println("Remaining balance: ₹" + balance);
        } else {
            System.out.println("Transfer failed: Insufficient funds or invalid amount");
        }
    }
    
    @Override
    public void payUtilityBill(String provider, String consumerNumber, double amount) {
        if (!netBankingEnabled) {
            System.out.println("Please enable net banking first");
            return;
        }
        
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("₹" + amount + " paid to " + provider + " for Consumer #" + consumerNumber);
            System.out.println("Remaining balance: ₹" + balance);
        } else {
            System.out.println("Payment failed: Insufficient funds or invalid amount");
        }
    }
}

// Premium account implementing premium banking interface
class PremiumBankingAccount implements PremiumBanking {
    protected String accountHolderName;
    protected String accountNumber;
    protected double balance;
    protected String email;
    protected long phoneNumber;
    protected boolean netBankingEnabled;
    protected double interestRate;
    protected String relationshipManagerId;
    
    public PremiumBankingAccount(String accountHolderName, String accountNumber, 
                               double initialBalance, String email, long phoneNumber) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.netBankingEnabled = false;
        this.interestRate = 5.5; // Higher interest rate for premium accounts
        this.relationshipManagerId = "RM" + (1000 + (int)(Math.random() * 9000));
    }
    
    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("₹" + amount + " deposited successfully to your premium account");
            System.out.println("New balance: ₹" + balance);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }
    
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("₹" + amount + " withdrawn successfully from your premium account");
            System.out.println("New balance: ₹" + balance);
            return true;
        } else {
            System.out.println("Withdrawal failed: Insufficient funds or invalid amount");
            return false;
        }
    }
    
    @Override
    public double checkBalance() {
        System.out.println("Premium Account - Current balance: ₹" + balance);
        return balance;
    }
    
    @Override
    public void showAccountInfo() {
        System.out.println("Premium Account Details:");
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Current Balance: ₹" + balance);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Interest Rate: " + interestRate + "%");
        System.out.println("Relationship Manager ID: " + relationshipManagerId);
        System.out.println("Net Banking Status: " + (netBankingEnabled ? "Enabled" : "Disabled"));
    }
    
    @Override
    public void enableNetBanking(String password) {
        netBankingEnabled = true;
        System.out.println("Premium net banking enabled with enhanced security features");
        System.out.println("Login credentials sent to: " + email);
    }
    
    @Override
    public void transferFunds(String toAccountNumber, double amount) {
        System.out.println("Premium transfer service - No transaction fees");
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("₹" + amount + " transferred to account: " + toAccountNumber);
            System.out.println("Remaining balance: ₹" + balance);
        } else {
            System.out.println("Transfer failed: Insufficient funds or invalid amount");
        }
    }
    
    @Override
    public void payUtilityBill(String provider, String consumerNumber, double amount) {
        System.out.println("Premium bill payment service - With cashback offers");
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            double cashback = amount * 0.01; // 1% cashback
            balance += cashback;
            System.out.println("₹" + amount + " paid to " + provider + " for Consumer #" + consumerNumber);
            System.out.println("Cashback of ₹" + cashback + " credited");
            System.out.println("Remaining balance: ₹" + balance);
        } else {
            System.out.println("Payment failed: Insufficient funds or invalid amount");
        }
    }
    
    @Override
    public void calculateInterest() {
        double interest = balance * interestRate / 100;
        System.out.println("Premium account interest at " + interestRate + "% would be: ₹" + interest);
    }
    
    @Override
    public void setInterestRate(double rate) {
        this.interestRate = rate;
        System.out.println("Premium account interest rate updated to " + rate + "%");
    }
    
    @Override
    public void requestLoan(double amount, int years) {
        System.out.println("Loan request processed for premium customer:");
        System.out.println("Amount: ₹" + amount);
        System.out.println("Duration: " + years + " years");
        System.out.println("Status: Pre-approved with special interest rate");
        System.out.println("Your relationship manager will contact you shortly");
    }
    
    @Override
    public void provideFeedback(String feedback) {
        System.out.println("Thank you for your premium account feedback:");
        System.out.println("\"" + feedback + "\"");
        System.out.println("Your feedback is valuable to us!");
    }
    
    @Override
    public void contactRelationshipManager(String message) {
        System.out.println("Message sent to your dedicated relationship manager (" + relationshipManagerId + "):");
        System.out.println("\"" + message + "\"");
        System.out.println("You will receive a response within 2 hours");
    }
}
