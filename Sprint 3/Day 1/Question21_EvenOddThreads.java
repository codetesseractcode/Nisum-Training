/**
 * Question 21: Even and Odd Number Threads
 */

/**
 * Thread class for printing even numbers
 */
class EvenNumberThread extends Thread {
    
    public EvenNumberThread(String name) {
        super(name);
    }
    
    @Override
    public void run() {
        System.out.println(getName() + " started");
        
        try {
            for (int i = 2; i <= 10; i += 2) {
                System.out.println(getName() + ": " + i);
                Thread.sleep(500); // Sleep for 500ms between prints
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted");
            Thread.currentThread().interrupt();
        }
        
        System.out.println(getName() + " finished");
    }
}

/**
 * Thread class for printing odd numbers
 */
class OddNumberThread extends Thread {
    
    public OddNumberThread(String name) {
        super(name);
    }
    
    @Override
    public void run() {
        System.out.println(getName() + " started");
        
        try {
            for (int i = 1; i <= 9; i += 2) {
                System.out.println(getName() + ": " + i);
                Thread.sleep(600); // Sleep for 600ms between prints (different timing)
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted");
            Thread.currentThread().interrupt();
        }
        
        System.out.println(getName() + " finished");
    }
}

/**
 * Alternative implementation using Runnable interface
 */
class NumberPrinter implements Runnable {
    private final boolean isEven;
    private final String threadName;
    
    public NumberPrinter(boolean isEven, String threadName) {
        this.isEven = isEven;
        this.threadName = threadName;
    }
    
    @Override
    public void run() {
        System.out.println(threadName + " started");
        
        try {
            if (isEven) {
                // Print even numbers 2, 4, 6, 8, 10
                for (int i = 2; i <= 10; i += 2) {
                    System.out.println(threadName + ": " + i);
                    Thread.sleep(400);
                }
            } else {
                // Print odd numbers 1, 3, 5, 7, 9
                for (int i = 1; i <= 9; i += 2) {
                    System.out.println(threadName + ": " + i);
                    Thread.sleep(450);
                }
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + " was interrupted");
            Thread.currentThread().interrupt();
        }
        
        System.out.println(threadName + " finished");
    }
}

/**
 * Main class demonstrating even and odd number threads
 */
public class Question21_EvenOddThreads {
    
    /**
     * Demonstration using Thread class extension
     */
    public static void demonstrateWithThreadClass() {
        System.out.println("=== Using Thread Class Extension ===");
        
        // Create threads
        EvenNumberThread evenThread = new EvenNumberThread("EvenThread");
        OddNumberThread oddThread = new OddNumberThread("OddThread");
        
        // Start threads
        evenThread.start();
        oddThread.start();
        
        try {
            // Wait for both threads to complete
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread was interrupted");
            Thread.currentThread().interrupt();
        }
        
        System.out.println("All threads completed\n");
    }
    
    /**
     * Demonstration using Runnable interface
     */
    public static void demonstrateWithRunnable() {
        System.out.println("=== Using Runnable Interface ===");
        
        // Create runnables
        NumberPrinter evenPrinter = new NumberPrinter(true, "EvenRunnable");
        NumberPrinter oddPrinter = new NumberPrinter(false, "OddRunnable");
        
        // Create and start threads
        Thread evenThread = new Thread(evenPrinter);
        Thread oddThread = new Thread(oddPrinter);
        
        evenThread.start();
        oddThread.start();
        
        try {
            // Wait for both threads to complete
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread was interrupted");
            Thread.currentThread().interrupt();
        }
        
        System.out.println("All threads completed\n");
    }
    
    /**
     * Advanced demonstration with thread priorities and coordination
     */
    public static void demonstrateAdvanced() {
        System.out.println("=== Advanced Thread Coordination ===");
        
        Thread evenThread = new Thread(() -> {
            try {
                System.out.println("Even thread started with priority: " + 
                                 Thread.currentThread().getPriority());
                
                for (int i = 2; i <= 10; i += 2) {
                    System.out.printf("Even: %d (Thread: %s)%n", 
                                    i, Thread.currentThread().getName());
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "AdvancedEvenThread");
        
        Thread oddThread = new Thread(() -> {
            try {
                System.out.println("Odd thread started with priority: " + 
                                 Thread.currentThread().getPriority());
                
                for (int i = 1; i <= 9; i += 2) {
                    System.out.printf("Odd: %d (Thread: %s)%n", 
                                    i, Thread.currentThread().getName());
                    Thread.sleep(350);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "AdvancedOddThread");
        
        // Set different priorities
        evenThread.setPriority(Thread.MAX_PRIORITY);
        oddThread.setPriority(Thread.MIN_PRIORITY);
        
        // Start threads
        evenThread.start();
        oddThread.start();
        
        try {
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Advanced demonstration completed\n");
    }
    
    /**
     * Demonstration with thread state monitoring
     */
    public static void demonstrateThreadStates() {
        System.out.println("=== Thread State Monitoring ===");
        
        Thread monitoredThread = new Thread(() -> {
            try {
                System.out.println("Thread is running...");
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Count: " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "MonitoredThread");
        
        System.out.println("Thread state before start: " + monitoredThread.getState());
        
        monitoredThread.start();
        System.out.println("Thread state after start: " + monitoredThread.getState());
        
        // Monitor thread state
        while (monitoredThread.isAlive()) {
            System.out.println("Thread state: " + monitoredThread.getState());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        System.out.println("Final thread state: " + monitoredThread.getState());
        System.out.println("Thread monitoring completed\n");
    }
    
    public static void main(String[] args) {
        System.out.println("=== Question 21: Even and Odd Number Threads ===\n");
        
        // Run different demonstrations
        demonstrateWithThreadClass();
        
        // Wait a bit between demonstrations
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        demonstrateWithRunnable();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        demonstrateAdvanced();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        demonstrateThreadStates();
        
        System.out.println("=== Key Concepts Demonstrated ===");
        System.out.println("• Thread class extension vs Runnable interface");
        System.out.println("• Thread.start() method to begin execution");
        System.out.println("• Thread.sleep() for introducing delays");
        System.out.println("• Thread.join() for waiting for completion");
        System.out.println("• Thread priorities and naming");
        System.out.println("• Thread state monitoring");
        System.out.println("• Concurrent execution of multiple threads");
    }
}
