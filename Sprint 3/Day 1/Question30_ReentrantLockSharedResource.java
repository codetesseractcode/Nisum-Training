import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;

/**
 * Question 30: Java program using ReentrantLock to synchronize access to a shared resource
 */
public class Question30_ReentrantLockSharedResource {
    
    // Example 1: Simple Bank Account with ReentrantLock
    static class BankAccount {
        private double balance;
        private final ReentrantLock lock = new ReentrantLock();
        
        public BankAccount(double initialBalance) {
            this.balance = initialBalance;
        }
        
        public void deposit(double amount, String threadName) {
            lock.lock();
            try {
                System.out.println(threadName + " acquiring lock for deposit");
                double oldBalance = balance;
                // Simulate some processing time
                Thread.sleep(100);
                balance += amount;
                System.out.println(threadName + " deposited $" + amount + 
                                 ". Balance: $" + oldBalance + " -> $" + balance);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(threadName + " releasing lock after deposit");
                lock.unlock();
            }
        }
        
        public boolean withdraw(double amount, String threadName) {
            lock.lock();
            try {
                System.out.println(threadName + " acquiring lock for withdrawal");
                if (balance >= amount) {
                    double oldBalance = balance;
                    // Simulate some processing time
                    Thread.sleep(100);
                    balance -= amount;
                    System.out.println(threadName + " withdrew $" + amount + 
                                     ". Balance: $" + oldBalance + " -> $" + balance);
                    return true;
                } else {
                    System.out.println(threadName + " insufficient funds for withdrawal of $" + amount + 
                                     ". Current balance: $" + balance);
                    return false;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            } finally {
                System.out.println(threadName + " releasing lock after withdrawal attempt");
                lock.unlock();
            }
        }
        
        public double getBalance() {
            lock.lock();
            try {
                return balance;
            } finally {
                lock.unlock();
            }
        }
    }
    
    // Example 2: Resource Pool with ReentrantLock and tryLock with timeout
    static class ResourcePool {
        private final List<String> resources = new ArrayList<>();
        private final ReentrantLock lock = new ReentrantLock();
        
        public ResourcePool() {
            // Initialize with 3 resources
            resources.add("Resource-1");
            resources.add("Resource-2");
            resources.add("Resource-3");
        }
        
        public String acquireResource(String threadName, long timeoutMs) {
            try {
                if (lock.tryLock(timeoutMs, TimeUnit.MILLISECONDS)) {
                    try {
                        if (!resources.isEmpty()) {
                            String resource = resources.remove(0);
                            System.out.println(threadName + " acquired " + resource + 
                                             ". Remaining resources: " + resources.size());
                            return resource;
                        } else {
                            System.out.println(threadName + " found no available resources");
                            return null;
                        }
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println(threadName + " failed to acquire lock within " + timeoutMs + "ms");
                    return null;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(threadName + " was interrupted while waiting for lock");
                return null;
            }
        }
        
        public void releaseResource(String resource, String threadName) {
            lock.lock();
            try {
                resources.add(resource);
                System.out.println(threadName + " released " + resource + 
                                 ". Available resources: " + resources.size());
            } finally {
                lock.unlock();
            }
        }
    }
    
    // Example 3: Producer-Consumer with ReentrantLock and Condition
    static class ProducerConsumerBuffer {
        private final List<Integer> buffer = new ArrayList<>();
        private final int capacity;
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();
        
        public ProducerConsumerBuffer(int capacity) {
            this.capacity = capacity;
        }
        
        public void produce(int item, String threadName) throws InterruptedException {
            lock.lock();
            try {
                while (buffer.size() == capacity) {
                    System.out.println(threadName + " waiting - buffer is full");
                    notFull.await();
                }
                buffer.add(item);
                System.out.println(threadName + " produced " + item + 
                                 ". Buffer size: " + buffer.size());
                notEmpty.signal(); // Wake up consumers
            } finally {
                lock.unlock();
            }
        }
        
        public int consume(String threadName) throws InterruptedException {
            lock.lock();
            try {
                while (buffer.isEmpty()) {
                    System.out.println(threadName + " waiting - buffer is empty");
                    notEmpty.await();
                }
                int item = buffer.remove(0);
                System.out.println(threadName + " consumed " + item + 
                                 ". Buffer size: " + buffer.size());
                notFull.signal(); // Wake up producers
                return item;
            } finally {
                lock.unlock();
            }
        }
    }
    
    // Example 4: Fair vs Unfair ReentrantLock demonstration
    static class FairUnfairDemo {
        private final ReentrantLock fairLock = new ReentrantLock(true);   // Fair lock
        private final ReentrantLock unfairLock = new ReentrantLock(false); // Unfair lock
        private int counter = 0;
        
        public void demonstrateFairLock(String threadName) {
            for (int i = 0; i < 3; i++) {
                fairLock.lock();
                try {
                    System.out.println("Fair Lock - " + threadName + " acquired lock, counter: " + (++counter));
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    fairLock.unlock();
                }
            }
        }
        
        public void demonstrateUnfairLock(String threadName) {
            for (int i = 0; i < 3; i++) {
                unfairLock.lock();
                try {
                    System.out.println("Unfair Lock - " + threadName + " acquired lock, counter: " + (++counter));
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    unfairLock.unlock();
                }
            }
        }
        
        public void resetCounter() {
            counter = 0;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Question 30: ReentrantLock Shared Resource Synchronization ===\n");
        
        // Demo 1: Basic ReentrantLock with Bank Account
        System.out.println("Demo 1: Basic ReentrantLock with Bank Account");
        System.out.println("------------------------------------------------");
        BankAccount account = new BankAccount(1000.0);
        
        ExecutorService executor1 = Executors.newFixedThreadPool(4);
        
        // Multiple threads performing deposits and withdrawals
        executor1.submit(() -> account.deposit(200, "Thread-Deposit-1"));
        executor1.submit(() -> account.withdraw(150, "Thread-Withdraw-1"));
        executor1.submit(() -> account.deposit(100, "Thread-Deposit-2"));
        executor1.submit(() -> account.withdraw(300, "Thread-Withdraw-2"));
        
        executor1.shutdown();
        try {
            executor1.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Final balance: $" + account.getBalance());
        System.out.println();
        
        // Demo 2: Resource Pool with tryLock and timeout
        System.out.println("Demo 2: Resource Pool with tryLock and timeout");
        System.out.println("----------------------------------------------");
        ResourcePool pool = new ResourcePool();
        
        ExecutorService executor2 = Executors.newFixedThreadPool(5);
        
        // Multiple threads trying to acquire resources
        for (int i = 1; i <= 5; i++) {
            final int threadNum = i;
            executor2.submit(() -> {
                String threadName = "Thread-" + threadNum;
                String resource = pool.acquireResource(threadName, 1000);
                if (resource != null) {
                    try {
                        // Simulate using the resource
                        Thread.sleep(2000);
                        pool.releaseResource(resource, threadName);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
        
        executor2.shutdown();
        try {
            executor2.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println();
        
        // Demo 3: Producer-Consumer with ReentrantLock and Condition
        System.out.println("Demo 3: Producer-Consumer with ReentrantLock and Condition");
        System.out.println("----------------------------------------------------------");
        ProducerConsumerBuffer buffer = new ProducerConsumerBuffer(3);
        
        ExecutorService executor3 = Executors.newFixedThreadPool(4);
        
        // Producers
        for (int i = 1; i <= 2; i++) {
            final int producerId = i;
            executor3.submit(() -> {
                String threadName = "Producer-" + producerId;
                try {
                    for (int j = 1; j <= 3; j++) {
                        buffer.produce(producerId * 10 + j, threadName);
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // Consumers
        for (int i = 1; i <= 2; i++) {
            final int consumerId = i;
            executor3.submit(() -> {
                String threadName = "Consumer-" + consumerId;
                try {
                    for (int j = 1; j <= 3; j++) {
                        buffer.consume(threadName);
                        Thread.sleep(700);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        executor3.shutdown();
        try {
            executor3.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println();
        
        // Demo 4: Fair vs Unfair Lock demonstration
        System.out.println("Demo 4: Fair vs Unfair Lock demonstration");
        System.out.println("-----------------------------------------");
        FairUnfairDemo demo = new FairUnfairDemo();
        
        System.out.println("Fair Lock (FIFO order):");
        ExecutorService executor4 = Executors.newFixedThreadPool(3);
        
        for (int i = 1; i <= 3; i++) {
            final int threadNum = i;
            executor4.submit(() -> demo.demonstrateFairLock("Thread-" + threadNum));
        }
        
        executor4.shutdown();
        try {
            executor4.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        demo.resetCounter();
        System.out.println("\nUnfair Lock (no guaranteed order):");
        ExecutorService executor5 = Executors.newFixedThreadPool(3);
        
        for (int i = 1; i <= 3; i++) {
            final int threadNum = i;
            executor5.submit(() -> demo.demonstrateUnfairLock("Thread-" + threadNum));
        }
        
        executor5.shutdown();
        try {
            executor5.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\n=== ReentrantLock Features Demonstrated ===");
        System.out.println("1. Basic mutual exclusion with lock()/unlock()");
        System.out.println("2. Timeout-based locking with tryLock()");
        System.out.println("3. Condition variables for advanced coordination");
        System.out.println("4. Fair vs Unfair locking policies");
        System.out.println("5. Proper exception handling and resource cleanup");
    }
}
