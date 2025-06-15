/**
 * Question 24: Money Transfer with Thread Synchronization
 */

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Bank account with thread-safe operations
 */
class BankAccount {
    private final int accountId;
    private final String accountHolder;
    private BigDecimal balance;
    private final Lock accountLock = new ReentrantLock();
    private final AtomicLong transactionCount = new AtomicLong(0);
    private final List<String> transactionHistory = Collections.synchronizedList(new ArrayList<>());
    
    public BankAccount(int accountId, String accountHolder, BigDecimal initialBalance) {
        this.accountId = accountId;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        addTransaction("Account created with initial balance: $" + initialBalance);
    }
    
    public int getAccountId() { return accountId; }
    public String getAccountHolder() { return accountHolder; }
    
    public BigDecimal getBalance() {
        accountLock.lock();
        try {
            return balance;
        } finally {
            accountLock.unlock();
        }
    }
    
    public Lock getLock() { return accountLock; }
    
    public boolean withdraw(BigDecimal amount, String description) {
        accountLock.lock();
        try {
            if (balance.compareTo(amount) >= 0) {
                balance = balance.subtract(amount);
                transactionCount.incrementAndGet();
                addTransaction(String.format("Withdrawn $%s - %s. New balance: $%s", 
                                           amount, description, balance));
                return true;
            } else {
                addTransaction(String.format("Failed withdrawal of $%s - Insufficient funds. Balance: $%s", 
                                           amount, balance));
                return false;
            }
        } finally {
            accountLock.unlock();
        }
    }
    
    public void deposit(BigDecimal amount, String description) {
        accountLock.lock();
        try {
            balance = balance.add(amount);
            transactionCount.incrementAndGet();
            addTransaction(String.format("Deposited $%s - %s. New balance: $%s", 
                                       amount, description, balance));
        } finally {
            accountLock.unlock();
        }
    }
    
    private void addTransaction(String transaction) {
        transactionHistory.add(String.format("[%d] %s", 
                                           System.currentTimeMillis(), transaction));
    }
    
    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
    
    public long getTransactionCount() {
        return transactionCount.get();
    }
    
    @Override
    public String toString() {
        return String.format("Account[%d: %s, Balance: $%s, Transactions: %d]", 
                           accountId, accountHolder, getBalance(), getTransactionCount());
    }
}

/**
 * Unsafe money transfer (can cause race conditions and inconsistent state)
 */
class UnsafeMoneyTransfer {
    
    public static boolean transfer(BankAccount from, BankAccount to, BigDecimal amount) {
        System.out.printf("Unsafe transfer: $%s from Account-%d to Account-%d%n", 
                        amount, from.getAccountId(), to.getAccountId());
        
        // Race condition: balance check and withdrawal are not atomic
        if (from.getBalance().compareTo(amount) >= 0) {
            
            // Simulate processing delay (increases chance of race condition)
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
            
            // Two separate operations - not atomic!
            boolean withdrawn = from.withdraw(amount, "Transfer to Account-" + to.getAccountId());
            if (withdrawn) {
                to.deposit(amount, "Transfer from Account-" + from.getAccountId());
                return true;
            }
        }
        
        System.out.printf("❌ Transfer failed: $%s from Account-%d to Account-%d%n", 
                        amount, from.getAccountId(), to.getAccountId());
        return false;
    }
}

/**
 * Safe money transfer using proper locking
 */
class SafeMoneyTransfer {
    
    /**
     * Transfer money using ordered locking to prevent deadlocks
     */
    public static boolean transfer(BankAccount from, BankAccount to, BigDecimal amount) {
        System.out.printf("Safe transfer: $%s from Account-%d to Account-%d%n", 
                        amount, from.getAccountId(), to.getAccountId());
        
        // Order locks by account ID to prevent deadlock
        BankAccount firstLock = from.getAccountId() < to.getAccountId() ? from : to;
        BankAccount secondLock = from.getAccountId() < to.getAccountId() ? to : from;
        
        firstLock.getLock().lock();
        try {
            secondLock.getLock().lock();
            try {
                // Now both accounts are locked - perform atomic transfer
                if (from.getBalance().compareTo(amount) >= 0) {
                    
                    // Simulate processing
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return false;
                    }
                    
                    // Atomic transfer operation
                    boolean withdrawn = from.withdraw(amount, "Safe transfer to Account-" + to.getAccountId());
                    if (withdrawn) {
                        to.deposit(amount, "Safe transfer from Account-" + from.getAccountId());
                        System.out.printf("✓ Safe transfer completed: $%s from Account-%d to Account-%d%n", 
                                        amount, from.getAccountId(), to.getAccountId());
                        return true;
                    }
                } else {
                    System.out.printf("❌ Safe transfer failed - insufficient funds: $%s from Account-%d (balance: $%s)%n", 
                                    amount, from.getAccountId(), from.getBalance());
                }
                
                return false;
                
            } finally {
                secondLock.getLock().unlock();
            }
        } finally {
            firstLock.getLock().unlock();
        }
    }
    
    /**
     * Transfer with timeout to detect potential deadlocks
     */
    public static boolean transferWithTimeout(BankAccount from, BankAccount to, BigDecimal amount, 
                                            long timeoutMs) {
        System.out.printf("Timeout transfer: $%s from Account-%d to Account-%d (timeout: %dms)%n", 
                        amount, from.getAccountId(), to.getAccountId(), timeoutMs);
        
        // Order locks by account ID
        BankAccount firstLock = from.getAccountId() < to.getAccountId() ? from : to;
        BankAccount secondLock = from.getAccountId() < to.getAccountId() ? to : from;
        
        try {
            if (firstLock.getLock().tryLock(timeoutMs, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                try {
                    if (secondLock.getLock().tryLock(timeoutMs, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                        try {
                            // Perform transfer
                            if (from.getBalance().compareTo(amount) >= 0) {
                                boolean withdrawn = from.withdraw(amount, "Timeout transfer to Account-" + to.getAccountId());
                                if (withdrawn) {
                                    to.deposit(amount, "Timeout transfer from Account-" + from.getAccountId());
                                    System.out.printf("✓ Timeout transfer completed: $%s%n", amount);
                                    return true;
                                }
                            }
                            return false;
                        } finally {
                            secondLock.getLock().unlock();
                        }
                    } else {
                        System.out.printf("⏰ Transfer timed out acquiring second lock: $%s%n", amount);
                        return false;
                    }
                } finally {
                    firstLock.getLock().unlock();
                }
            } else {
                System.out.printf("⏰ Transfer timed out acquiring first lock: $%s%n", amount);
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}

/**
 * Deadlock-prone transfer (demonstrates potential deadlock scenario)
 */
class DeadlockProneTransfer {
    
    public static boolean transfer(BankAccount from, BankAccount to, BigDecimal amount) {
        System.out.printf("Deadlock-prone transfer: $%s from Account-%d to Account-%d%n", 
                        amount, from.getAccountId(), to.getAccountId());
        
        // Acquire locks in order of method parameters (can cause deadlock!)
        from.getLock().lock();
        try {
            System.out.printf("Thread %s acquired lock for Account-%d%n", 
                            Thread.currentThread().getName(), from.getAccountId());
            
            // Introduce delay to increase deadlock probability
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
            
            to.getLock().lock();
            try {
                System.out.printf("Thread %s acquired lock for Account-%d%n", 
                                Thread.currentThread().getName(), to.getAccountId());
                
                // Perform transfer
                if (from.getBalance().compareTo(amount) >= 0) {
                    boolean withdrawn = from.withdraw(amount, "Deadlock-prone transfer to Account-" + to.getAccountId());
                    if (withdrawn) {
                        to.deposit(amount, "Deadlock-prone transfer from Account-" + from.getAccountId());
                        return true;
                    }
                }
                return false;
                
            } finally {
                to.getLock().unlock();
                System.out.printf("Thread %s released lock for Account-%d%n", 
                                Thread.currentThread().getName(), to.getAccountId());
            }
        } finally {
            from.getLock().unlock();
            System.out.printf("Thread %s released lock for Account-%d%n", 
                            Thread.currentThread().getName(), from.getAccountId());
        }
    }
}

/**
 * Money transfer worker thread
 */
class TransferWorker extends Thread {
    private final List<BankAccount> accounts;
    private final int transferCount;
    private final String transferType;
    private int successfulTransfers = 0;
    private int failedTransfers = 0;
    
    public TransferWorker(List<BankAccount> accounts, int transferCount, String transferType, String name) {
        super(name);
        this.accounts = accounts;
        this.transferCount = transferCount;
        this.transferType = transferType;
    }
    
    @Override
    public void run() {
        System.out.printf("%s started (%s transfers)%n", getName(), transferType);
        
        ThreadLocalRandom random = ThreadLocalRandom.current();
        
        for (int i = 0; i < transferCount; i++) {
            // Select random accounts
            int fromIndex = random.nextInt(accounts.size());
            int toIndex = random.nextInt(accounts.size());
            
            // Ensure different accounts
            while (toIndex == fromIndex) {
                toIndex = random.nextInt(accounts.size());
            }
            
            BankAccount from = accounts.get(fromIndex);
            BankAccount to = accounts.get(toIndex);
            
            // Random transfer amount between $10 and $500
            BigDecimal amount = BigDecimal.valueOf(random.nextDouble(10, 500))
                                         .setScale(2, RoundingMode.HALF_UP);
            
            boolean success = false;
            
            switch (transferType) {
                case "unsafe":
                    success = UnsafeMoneyTransfer.transfer(from, to, amount);
                    break;
                case "safe":
                    success = SafeMoneyTransfer.transfer(from, to, amount);
                    break;
                case "timeout":
                    success = SafeMoneyTransfer.transferWithTimeout(from, to, amount, 1000);
                    break;
                case "deadlock-prone":
                    success = DeadlockProneTransfer.transfer(from, to, amount);
                    break;
            }
            
            if (success) {
                successfulTransfers++;
            } else {
                failedTransfers++;
            }
            
            // Random delay between transfers
            try {
                Thread.sleep(random.nextInt(10, 100));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        System.out.printf("%s completed: %d successful, %d failed%n", 
                        getName(), successfulTransfers, failedTransfers);
    }
    
    public int getSuccessfulTransfers() { return successfulTransfers; }
    public int getFailedTransfers() { return failedTransfers; }
}

/**
 * Main class demonstrating money transfer synchronization
 */
public class Question24_MoneyTransferSync {
    
    /**
     * Create test bank accounts
     */
    private static List<BankAccount> createAccounts() {
        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(new BankAccount(1, "Alice Johnson", new BigDecimal("5000.00")));
        accounts.add(new BankAccount(2, "Bob Smith", new BigDecimal("3000.00")));
        accounts.add(new BankAccount(3, "Charlie Brown", new BigDecimal("7000.00")));
        accounts.add(new BankAccount(4, "Diana Prince", new BigDecimal("4500.00")));
        accounts.add(new BankAccount(5, "Eve Wilson", new BigDecimal("6000.00")));
        return accounts;
    }
    
    /**
     * Calculate total money in the system
     */
    private static BigDecimal calculateTotalMoney(List<BankAccount> accounts) {
        return accounts.stream()
                      .map(BankAccount::getBalance)
                      .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    /**
     * Display account summaries
     */
    private static void displayAccountSummaries(List<BankAccount> accounts) {
        System.out.println("=== Account Summaries ===");
        for (BankAccount account : accounts) {
            System.out.println(account);
        }
        System.out.printf("Total money in system: $%s%n", calculateTotalMoney(accounts));
        System.out.println();
    }
    
    /**
     * Test unsafe transfers (demonstrates race conditions)
     */
    public static void testUnsafeTransfers() {
        System.out.println("=== Testing Unsafe Money Transfers ===");
        
        List<BankAccount> accounts = createAccounts();
        BigDecimal initialTotal = calculateTotalMoney(accounts);
        
        System.out.printf("Initial total money: $%s%n", initialTotal);
        displayAccountSummaries(accounts);
        
        // Create transfer workers
        TransferWorker[] workers = {
            new TransferWorker(accounts, 5, "unsafe", "UnsafeWorker-1"),
            new TransferWorker(accounts, 5, "unsafe", "UnsafeWorker-2"),
            new TransferWorker(accounts, 5, "unsafe", "UnsafeWorker-3")
        };
        
        // Start workers
        for (TransferWorker worker : workers) {
            worker.start();
        }
        
        // Wait for completion
        for (TransferWorker worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        
        BigDecimal finalTotal = calculateTotalMoney(accounts);
        System.out.printf("Final total money: $%s%n", finalTotal);
        System.out.printf("Money conservation: %s%n", 
                        initialTotal.equals(finalTotal) ? "✓ PRESERVED" : "❌ VIOLATED");
        
        displayAccountSummaries(accounts);
    }
    
    /**
     * Test safe transfers with proper synchronization
     */
    public static void testSafeTransfers() {
        System.out.println("=== Testing Safe Money Transfers ===");
        
        List<BankAccount> accounts = createAccounts();
        BigDecimal initialTotal = calculateTotalMoney(accounts);
        
        System.out.printf("Initial total money: $%s%n", initialTotal);
        
        // Create transfer workers
        TransferWorker[] workers = {
            new TransferWorker(accounts, 10, "safe", "SafeWorker-1"),
            new TransferWorker(accounts, 10, "safe", "SafeWorker-2"),
            new TransferWorker(accounts, 10, "safe", "SafeWorker-3"),
            new TransferWorker(accounts, 10, "safe", "SafeWorker-4")
        };
        
        long startTime = System.currentTimeMillis();
        
        // Start workers
        for (TransferWorker worker : workers) {
            worker.start();
        }
        
        // Wait for completion
        for (TransferWorker worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        
        long endTime = System.currentTimeMillis();
        
        BigDecimal finalTotal = calculateTotalMoney(accounts);
        System.out.printf("Final total money: $%s%n", finalTotal);
        System.out.printf("Money conservation: %s%n", 
                        initialTotal.equals(finalTotal) ? "✓ PRESERVED" : "❌ VIOLATED");
        System.out.printf("Execution time: %d ms%n", endTime - startTime);
        
        // Calculate statistics
        int totalSuccessful = 0;
        int totalFailed = 0;
        for (TransferWorker worker : workers) {
            totalSuccessful += worker.getSuccessfulTransfers();
            totalFailed += worker.getFailedTransfers();
        }
        
        System.out.printf("Transfer statistics: %d successful, %d failed%n", 
                        totalSuccessful, totalFailed);
        
        displayAccountSummaries(accounts);
    }
    
    /**
     * Test transfers with timeout (deadlock detection)
     */
    public static void testTimeoutTransfers() {
        System.out.println("=== Testing Timeout-based Transfers ===");
        
        List<BankAccount> accounts = createAccounts();
        BigDecimal initialTotal = calculateTotalMoney(accounts);
        
        // Create many concurrent workers to increase contention
        TransferWorker[] workers = new TransferWorker[8];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new TransferWorker(accounts, 5, "timeout", "TimeoutWorker-" + (i + 1));
        }
        
        long startTime = System.currentTimeMillis();
        
        // Start workers
        for (TransferWorker worker : workers) {
            worker.start();
        }
        
        // Wait for completion
        for (TransferWorker worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        
        long endTime = System.currentTimeMillis();
        
        BigDecimal finalTotal = calculateTotalMoney(accounts);
        System.out.printf("Money conservation: %s%n", 
                        initialTotal.equals(finalTotal) ? "✓ PRESERVED" : "❌ VIOLATED");
        System.out.printf("Execution time: %d ms%n", endTime - startTime);
        
        displayAccountSummaries(accounts);
    }
    
    /**
     * Demonstrate deadlock scenario (WARNING: May cause actual deadlock!)
     */
    public static void demonstrateDeadlockScenario() {
        System.out.println("=== Demonstrating Deadlock Scenario (with timeout) ===");
        
        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(new BankAccount(1, "Account-A", new BigDecimal("1000.00")));
        accounts.add(new BankAccount(2, "Account-B", new BigDecimal("1000.00")));
        
        // Create workers that will likely deadlock
        Thread worker1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                DeadlockProneTransfer.transfer(accounts.get(0), accounts.get(1), 
                                             new BigDecimal("100.00"));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "DeadlockWorker-1");
        
        Thread worker2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                DeadlockProneTransfer.transfer(accounts.get(1), accounts.get(0), 
                                             new BigDecimal("150.00"));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "DeadlockWorker-2");
        
        worker1.start();
        worker2.start();
        
        // Use timeout to detect deadlock
        try {
            worker1.join(5000); // Wait max 5 seconds
            worker2.join(5000);
            
            if (worker1.isAlive() || worker2.isAlive()) {
                System.out.println("❌ Potential deadlock detected - interrupting workers");
                worker1.interrupt();
                worker2.interrupt();
            } else {
                System.out.println("✓ No deadlock occurred (lucky timing)");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        displayAccountSummaries(accounts);
    }
    
    /**
     * Performance comparison of different synchronization approaches
     */
    public static void performanceComparison() {
        System.out.println("=== Performance Comparison ===");
        
        int iterations = 3;
        
        for (int i = 0; i < iterations; i++) {
            System.out.printf("Iteration %d:%n", i + 1);
            
            // Test safe transfers
            List<BankAccount> safeAccounts = createAccounts();
            TransferWorker[] safeWorkers = {
                new TransferWorker(safeAccounts, 20, "safe", "Safe-1"),
                new TransferWorker(safeAccounts, 20, "safe", "Safe-2")
            };
            
            long start = System.currentTimeMillis();
            for (TransferWorker worker : safeWorkers) worker.start();
            for (TransferWorker worker : safeWorkers) {
                try { worker.join(); } catch (InterruptedException e) { break; }
            }
            long safeTime = System.currentTimeMillis() - start;
            
            // Test timeout transfers
            List<BankAccount> timeoutAccounts = createAccounts();
            TransferWorker[] timeoutWorkers = {
                new TransferWorker(timeoutAccounts, 20, "timeout", "Timeout-1"),
                new TransferWorker(timeoutAccounts, 20, "timeout", "Timeout-2")
            };
            
            start = System.currentTimeMillis();
            for (TransferWorker worker : timeoutWorkers) worker.start();
            for (TransferWorker worker : timeoutWorkers) {
                try { worker.join(); } catch (InterruptedException e) { break; }
            }
            long timeoutTime = System.currentTimeMillis() - start;
            
            System.out.printf("  Safe: %d ms, Timeout: %d ms%n", safeTime, timeoutTime);
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("=== Question 24: Money Transfer with Thread Synchronization ===\n");
        
        testUnsafeTransfers();
        testSafeTransfers();
        testTimeoutTransfers();
        demonstrateDeadlockScenario();
        performanceComparison();
        
        System.out.println("=== Key Concepts Demonstrated ===");
        System.out.println("• Race conditions in financial transactions");
        System.out.println("• Deadlock prevention using ordered locking");
        System.out.println("• Timeout-based deadlock detection");
        System.out.println("• Atomic operations for data consistency");
        System.out.println("• Money conservation in concurrent systems");
        System.out.println("• ReentrantLock for fine-grained control");
        System.out.println("• Thread-safe collections and atomic operations");
        System.out.println("• Performance impact of different synchronization strategies");
    }
}
