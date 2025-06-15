import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

/**
 * Question 7: Global Exception Handling with Logging
 */

/**
 * Custom exception handler for logging unhandled exceptions
 */
class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    
    private static final String LOG_FILE = "global_exceptions.log";
    private static final String ERROR_DETAILS_FILE = "error_details.log";
    private static final DateTimeFormatter TIMESTAMP_FORMAT = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    
    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        
        // Log to console first
        System.err.printf("%n🚨 UNHANDLED EXCEPTION CAUGHT 🚨%n");
        System.err.printf("Time: %s%n", timestamp);
        System.err.printf("Thread: %s (ID: %d)%n", thread.getName(), thread.getId());
        System.err.printf("Exception: %s%n", exception.getClass().getSimpleName());
        System.err.printf("Message: %s%n", exception.getMessage());
        
        // Log to files
        logExceptionToFile(timestamp, thread, exception);
        logDetailedError(timestamp, thread, exception);
        
        // Additional cleanup or notification logic could go here
        System.err.println("Exception logged. Application may terminate.");
    }
    
    /**
     * Logs basic exception information to the main log file
     */
    private void logExceptionToFile(String timestamp, Thread thread, Throwable exception) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true);
             PrintWriter printer = new PrintWriter(writer)) {
            
            printer.printf("[%s] UNCAUGHT EXCEPTION%n", timestamp);
            printer.printf("Thread: %s (ID: %d)%n", thread.getName(), thread.getId());
            printer.printf("Exception: %s%n", exception.getClass().getName());
            printer.printf("Message: %s%n", exception.getMessage());
            printer.printf("Location: %s%n", getExceptionLocation(exception));
            printer.println("----------------------------------------");
            
        } catch (IOException e) {
            System.err.printf("Failed to log exception to file: %s%n", e.getMessage());
        }
    }
    
    /**
     * Logs detailed exception information including stack trace
     */
    private void logDetailedError(String timestamp, Thread thread, Throwable exception) {
        try (FileWriter writer = new FileWriter(ERROR_DETAILS_FILE, true);
             PrintWriter printer = new PrintWriter(writer)) {
            
            printer.printf("================== ERROR REPORT ==================%n");
            printer.printf("Timestamp: %s%n", timestamp);
            printer.printf("Thread Name: %s%n", thread.getName());
            printer.printf("Thread ID: %d%n", thread.getId());
            printer.printf("Thread State: %s%n", thread.getState());
            printer.printf("Thread Priority: %d%n", thread.getPriority());
            printer.printf("Exception Type: %s%n", exception.getClass().getName());
            printer.printf("Exception Message: %s%n", exception.getMessage());
            
            // System information
            printer.printf("Java Version: %s%n", System.getProperty("java.version"));
            printer.printf("OS: %s %s%n", System.getProperty("os.name"), System.getProperty("os.version"));
            printer.printf("Available Memory: %d MB%n", 
                          Runtime.getRuntime().maxMemory() / (1024 * 1024));
            printer.printf("Free Memory: %d MB%n", 
                          Runtime.getRuntime().freeMemory() / (1024 * 1024));
            
            // Stack trace
            printer.println("Stack Trace:");
            exception.printStackTrace(printer);
            
            // Cause chain
            Throwable cause = exception.getCause();
            while (cause != null) {
                printer.printf("%nCaused by: %s%n", cause.getClass().getName());
                printer.printf("Message: %s%n", cause.getMessage());
                cause.printStackTrace(printer);
                cause = cause.getCause();
            }
            
            printer.println("================================================");
            printer.println();
            
        } catch (IOException e) {
            System.err.printf("Failed to log detailed error: %s%n", e.getMessage());
        }
    }
    
    /**
     * Gets the location where the exception occurred
     */
    private String getExceptionLocation(Throwable exception) {
        StackTraceElement[] stackTrace = exception.getStackTrace();
        if (stackTrace.length > 0) {
            StackTraceElement element = stackTrace[0];
            return String.format("%s.%s(%s:%d)", 
                               element.getClassName(), 
                               element.getMethodName(),
                               element.getFileName(), 
                               element.getLineNumber());
        }
        return "Unknown location";
    }
}

/**
 * Demonstration classes that throw various types of exceptions
 */
class ProblematicService {
    
    public void divideByZero() {
        System.out.println("Attempting division by zero...");
        int result = 10 / 0; // This will throw ArithmeticException
    }
    
    public void accessNullObject() {
        System.out.println("Accessing null object...");
        String str = null;
        int length = str.length(); // This will throw NullPointerException
    }
    
    public void accessInvalidArray() {
        System.out.println("Accessing invalid array index...");
        int[] array = {1, 2, 3};
        int value = array[10]; // This will throw ArrayIndexOutOfBoundsException
    }
    
    public void readNonExistentFile() throws IOException {
        System.out.println("Reading non-existent file...");
        Files.readString(Paths.get("nonexistent_file.txt")); // This will throw IOException
    }
    
    public void createStackOverflow() {
        System.out.println("Creating stack overflow...");
        createStackOverflow(); // Infinite recursion - StackOverflowError
    }
}

/**
 * Worker threads that demonstrate exceptions in multi-threaded environment
 */
class WorkerThread extends Thread {
    private final String taskName;
    private final Runnable task;
    
    public WorkerThread(String taskName, Runnable task) {
        super("Worker-" + taskName);
        this.taskName = taskName;
        this.task = task;
    }
    
    @Override
    public void run() {
        try {
            System.out.printf("Worker thread %s starting task: %s%n", getName(), taskName);
            Thread.sleep(1000); // Simulate some work
            task.run();
            System.out.printf("Worker thread %s completed task: %s%n", getName(), taskName);
        } catch (InterruptedException e) {
            System.err.printf("Worker thread %s was interrupted%n", getName());
            Thread.currentThread().interrupt();
        }
    }
}

public class Question7_GlobalExceptionHandling {
    
    /**
     * Sets up global exception handling for the application
     */
    public static void setupGlobalExceptionHandling() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        
        // Set default uncaught exception handler for all threads
        Thread.setDefaultUncaughtExceptionHandler(handler);
        
        // Also set for the main thread specifically
        Thread.currentThread().setUncaughtExceptionHandler(handler);
        
        System.out.println("✓ Global exception handling configured");
    }
    
    /**
     * Demonstrates exceptions in the main thread
     */
    public static void demonstrateMainThreadExceptions() {
        System.out.println("\n=== Main Thread Exception Scenarios ===");
        
        ProblematicService service = new ProblematicService();
        
        System.out.println("\n1. Testing ArithmeticException...");
        try {
            service.divideByZero();
        } catch (ArithmeticException e) {
            System.out.println("✓ ArithmeticException caught and handled in main thread");
        }
        
        System.out.println("\n2. Creating uncaught NullPointerException...");
        // This will be caught by the global handler
        service.accessNullObject(); // This line will cause program termination
    }
    
    /**
     * Demonstrates exceptions in worker threads
     */
    public static void demonstrateWorkerThreadExceptions() {
        System.out.println("\n=== Worker Thread Exception Scenarios ===");
        
        ProblematicService service = new ProblematicService();
        
        // Create worker threads with different exception scenarios
        WorkerThread[] workers = {
            new WorkerThread("ArrayBounds", service::accessInvalidArray),
            new WorkerThread("NullPointer", service::accessNullObject),
            new WorkerThread("Arithmetic", service::divideByZero)
        };
        
        // Start all workers
        for (WorkerThread worker : workers) {
            worker.start();
        }
        
        // Wait for workers to complete (or fail)
        for (WorkerThread worker : workers) {
            try {
                worker.join(5000); // Wait up to 5 seconds
                if (worker.isAlive()) {
                    System.err.printf("Worker %s did not complete in time%n", worker.getName());
                    worker.interrupt();
                }
            } catch (InterruptedException e) {
                System.err.printf("Interrupted while waiting for worker %s%n", worker.getName());
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Demonstrates exception handling with ExecutorService
     */
    public static void demonstrateExecutorExceptions() {
        System.out.println("\n=== ExecutorService Exception Scenarios ===");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ProblematicService service = new ProblematicService();
        
        // Submit tasks that will throw exceptions
        Future<?>[] futures = {
            executor.submit(() -> {
                Thread.currentThread().setName("Executor-Task-1");
                service.accessInvalidArray();
            }),
            executor.submit(() -> {
                Thread.currentThread().setName("Executor-Task-2");
                service.accessNullObject();
            }),
            executor.submit(() -> {
                Thread.currentThread().setName("Executor-Task-3");
                try {
                    service.readNonExistentFile();
                } catch (IOException e) {
                    throw new RuntimeException("File operation failed", e);
                }
            })
        };
        
        // Check results
        for (int i = 0; i < futures.length; i++) {
            try {
                futures[i].get(3, TimeUnit.SECONDS);
                System.out.printf("Task %d completed successfully%n", i + 1);
            } catch (ExecutionException e) {
                System.err.printf("Task %d failed: %s%n", i + 1, e.getCause().getMessage());
            } catch (TimeoutException e) {
                System.err.printf("Task %d timed out%n", i + 1);
                futures[i].cancel(true);
            } catch (InterruptedException e) {
                System.err.printf("Interrupted while waiting for task %d%n", i + 1);
                Thread.currentThread().interrupt();
            }
        }
        
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Reads and displays logged exceptions
     */
    public static void displayLoggedExceptions() {
        System.out.println("\n=== Logged Exceptions ===");
        
        String[] logFiles = {"global_exceptions.log", "error_details.log"};
        
        for (String logFile : logFiles) {
            System.out.printf("%n--- Contents of %s ---%n", logFile);
            
            try {
                if (Files.exists(Paths.get(logFile))) {
                    String content = Files.readString(Paths.get(logFile));
                    if (content.trim().isEmpty()) {
                        System.out.println("(Log file is empty)");
                    } else {
                        System.out.println(content);
                    }
                } else {
                    System.out.println("(Log file does not exist)");
                }
            } catch (IOException e) {
                System.err.printf("Error reading log file %s: %s%n", logFile, e.getMessage());
            }
        }
    }
    
    /**
     * Cleans up log files
     */
    public static void cleanupLogFiles() {
        String[] logFiles = {"global_exceptions.log", "error_details.log"};
        
        System.out.println("\n=== Cleanup ===");
        for (String logFile : logFiles) {
            try {
                if (Files.deleteIfExists(Paths.get(logFile))) {
                    System.out.printf("Deleted: %s%n", logFile);
                }
            } catch (IOException e) {
                System.err.printf("Could not delete %s: %s%n", logFile, e.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Global Exception Handling Demonstration ===");
        
        // Clean up any existing log files
        cleanupLogFiles();
        
        // Setup global exception handling
        setupGlobalExceptionHandling();
        
        // Demonstrate different exception scenarios
        demonstrateWorkerThreadExceptions();
        demonstrateExecutorExceptions();
        
        // Allow some time for async operations to complete
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Display what was logged
        displayLoggedExceptions();
        
        System.out.println("\n=== Benefits of Global Exception Handling ===");
        System.out.println("✓ Centralized error logging and monitoring");
        System.out.println("✓ Captures exceptions from all threads");
        System.out.println("✓ Provides detailed error context");
        System.out.println("✓ Enables production debugging");
        System.out.println("✓ Prevents silent failures");
        System.out.println("✓ Supports error analytics and alerting");
        
        // Uncomment the line below to see an uncaught exception in action
        // demonstrateMainThreadExceptions();
        
        System.out.println("\n=== Program completed normally ===");
    }
}
