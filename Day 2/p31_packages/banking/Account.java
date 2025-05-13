package p31_packages.banking;

/**
 * Account class in the banking package
 */
public class Account {
    private String accountNumber;
    private Customer accountHolder;
    private double balance;
    
    public Account(String accountNumber, Customer accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: ₹" + amount);
            System.out.println("New Balance: ₹" + balance);
        } else {
            System.out.println("Invalid amount for deposit");
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: ₹" + amount);
            System.out.println("New Balance: ₹" + balance);
        } else {
            System.out.println("Invalid amount for withdrawal or insufficient funds");
        }
    }
    
    public void displayInfo() {
        System.out.println("Account Number: " + accountNumber);
        accountHolder.displayInfo();
        System.out.println("Current Balance: ₹" + balance);
    }
    
    public double getBalance() {
        return balance;
    }
}
