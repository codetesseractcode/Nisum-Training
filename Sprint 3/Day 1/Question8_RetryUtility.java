import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Question 8: Retry Utility with Exponential Backoff
 */

/**
 * Configuration class for retry behavior
 */
class RetryConfig {
    private final int maxAttempts;
    private final Duration initialDelay;
    private final double backoffMultiplier;
    private final Duration maxDelay;
    private final Class<? extends Exception>[] retryableExceptions;
    
    @SafeVarargs
    public RetryConfig(int maxAttempts, Duration initialDelay, double backoffMultiplier, 
                      Duration maxDelay, Class<? extends Exception>... retryableExceptions) {
        this.maxAttempts = maxAttempts;
        this.initialDelay = initialDelay;
        this.backoffMultiplier = backoffMultiplier;
        this.maxDelay = maxDelay;
        this.retryableExceptions = retryableExceptions;
    }
    
    public int getMaxAttempts() { return maxAttempts; }
    public Duration getInitialDelay() { return initialDelay; }
    public double getBackoffMultiplier() { return backoffMultiplier; }
    public Duration getMaxDelay() { return maxDelay; }
    public Class<? extends Exception>[] getRetryableExceptions() { return retryableExceptions; }
    
    public static RetryConfig defaultConfig() {
        return new RetryConfig(3, Duration.ofSeconds(1), 2.0, Duration.ofSeconds(30));
    }
    
    public static RetryConfig networkConfig() {
        return new RetryConfig(5, Duration.ofMillis(500), 1.5, Duration.ofSeconds(10),
                              RuntimeException.class, IllegalStateException.class);
    }
}

/**
 * Result class containing retry attempt information
 */
class RetryResult<T> {
    private final T result;
    private final boolean successful;
    private final int attemptsMade;
    private final Duration totalTime;
    private final List<Exception> exceptions;
    
    public RetryResult(T result, boolean successful, int attemptsMade, 
                      Duration totalTime, List<Exception> exceptions) {
        this.result = result;
        this.successful = successful;
        this.attemptsMade = attemptsMade;
        this.totalTime = totalTime;
        this.exceptions = new ArrayList<>(exceptions);
    }
    
    public T getResult() { return result; }
    public boolean isSuccessful() { return successful; }
    public int getAttemptsMade() { return attemptsMade; }
    public Duration getTotalTime() { return totalTime; }
    public List<Exception> getExceptions() { return new ArrayList<>(exceptions); }
}

/**
 * Functional interface for operations that can be retried
 */
@FunctionalInterface
interface RetryableOperation<T> {
    T execute() throws Exception;
}

/**
 * Comprehensive retry utility implementation
 */
public class Question8_RetryUtility {
    
    private static final DateTimeFormatter TIME_FORMAT = 
        DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    
    /**
     * Executes operation with retry logic and exponential backoff
     */
    public static <T> RetryResult<T> executeWithRetry(RetryableOperation<T> operation, 
                                                     RetryConfig config) {
        List<Exception> exceptions = new ArrayList<>();
        LocalDateTime startTime = LocalDateTime.now();
        
        System.out.printf("[%s] Starting retry operation (max attempts: %d)%n", 
                        startTime.format(TIME_FORMAT), config.getMaxAttempts());
        
        for (int attempt = 1; attempt <= config.getMaxAttempts(); attempt++) {
            try {
                System.out.printf("[%s] Attempt %d/%d%n", 
                                LocalDateTime.now().format(TIME_FORMAT), attempt, config.getMaxAttempts());
                
                T result = operation.execute();
                
                Duration totalTime = Duration.between(startTime, LocalDateTime.now());
                System.out.printf("[%s] ✓ Operation succeeded on attempt %d (total time: %d ms)%n", 
                                LocalDateTime.now().format(TIME_FORMAT), attempt, totalTime.toMillis());
                
                return new RetryResult<>(result, true, attempt, totalTime, exceptions);
                
            } catch (Exception e) {
                exceptions.add(e);
                System.err.printf("[%s] ✗ Attempt %d failed: %s - %s%n", 
                                LocalDateTime.now().format(TIME_FORMAT), attempt, 
                                e.getClass().getSimpleName(), e.getMessage());
                
                // Check if exception is retryable
                if (!isRetryableException(e, config)) {
                    System.err.printf("[%s] Exception is not retryable, aborting%n", 
                                    LocalDateTime.now().format(TIME_FORMAT));
                    break;
                }
                
                // Don't wait after the last attempt
                if (attempt < config.getMaxAttempts()) {
                    Duration delay = calculateDelay(attempt, config);
                    System.out.printf("[%s] Waiting %d ms before next attempt...%n", 
                                    LocalDateTime.now().format(TIME_FORMAT), delay.toMillis());
                    
                    try {
                        Thread.sleep(delay.toMillis());
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        System.err.println("Retry interrupted");
                        break;
                    }
                }
            }
        }
        
        Duration totalTime = Duration.between(startTime, LocalDateTime.now());
        System.err.printf("[%s] ❌ All retry attempts failed (total time: %d ms)%n", 
                        LocalDateTime.now().format(TIME_FORMAT), totalTime.toMillis());
        
        return new RetryResult<>(null, false, config.getMaxAttempts(), totalTime, exceptions);
    }
    
    /**
     * Simplified retry method with default configuration
     */
    public static <T> T retry(RetryableOperation<T> operation) throws Exception {
        return retry(operation, RetryConfig.defaultConfig());
    }
    
    /**
     * Simplified retry method that throws exception on failure
     */
    public static <T> T retry(RetryableOperation<T> operation, RetryConfig config) throws Exception {
        RetryResult<T> result = executeWithRetry(operation, config);
        
        if (result.isSuccessful()) {
            return result.getResult();
        } else {
            // Throw the last exception
            List<Exception> exceptions = result.getExceptions();
            if (!exceptions.isEmpty()) {
                Exception lastException = exceptions.get(exceptions.size() - 1);
                throw new RuntimeException("Operation failed after " + result.getAttemptsMade() + 
                                         " attempts", lastException);
            } else {
                throw new RuntimeException("Operation failed after " + result.getAttemptsMade() + 
                                         " attempts with no recorded exceptions");
            }
        }
    }
    
    /**
     * Calculates delay using exponential backoff
     */
    private static Duration calculateDelay(int attempt, RetryConfig config) {
        long delayMs = (long) (config.getInitialDelay().toMillis() * 
                              Math.pow(config.getBackoffMultiplier(), attempt - 1));
        
        // Apply jitter (±25% randomization)
        double jitter = 0.75 + (Math.random() * 0.5); // Random between 0.75 and 1.25
        delayMs = (long) (delayMs * jitter);
        
        // Cap at maximum delay
        Duration delay = Duration.ofMillis(delayMs);
        if (delay.compareTo(config.getMaxDelay()) > 0) {
            delay = config.getMaxDelay();
        }
        
        return delay;
    }
    
    /**
     * Checks if an exception is retryable based on configuration
     */
    private static boolean isRetryableException(Exception exception, RetryConfig config) {
        if (config.getRetryableExceptions().length == 0) {
            // If no specific exceptions configured, retry all except InterruptedException
            return !(exception instanceof InterruptedException);
        }
        
        for (Class<? extends Exception> retryableType : config.getRetryableExceptions()) {
            if (retryableType.isAssignableFrom(exception.getClass())) {
                return true;
            }
        }
        
        return false;
    }
    
    // Simulation classes for testing
    
    /**
     * Simulated network service that fails intermittently
     */
    static class NetworkService {
        private static final Random random = new Random();
        private int callCount = 0;
        
        public String fetchData(String endpoint) throws Exception {
            callCount++;
            
            // Simulate various failure scenarios
            double failureChance = Math.random();
            
            if (failureChance < 0.3) { // 30% chance of timeout
                throw new RuntimeException("Connection timeout for endpoint: " + endpoint);
            } else if (failureChance < 0.5) { // 20% chance of server error
                throw new IllegalStateException("Server returned 500 error for: " + endpoint);
            } else if (failureChance < 0.6) { // 10% chance of network error
                throw new RuntimeException("Network unreachable");
            }
            
            // Success case
            return String.format("Data from %s (call #%d)", endpoint, callCount);
        }
        
        public void resetCallCount() {
            callCount = 0;
        }
    }
    
    /**
     * Simulated database service with transient failures
     */
    static class DatabaseService {
        private int attempts = 0;
        
        public List<String> queryUsers(String query) throws Exception {
            attempts++;
            
            // First few attempts fail, then succeed
            if (attempts <= 2) {
                if (attempts == 1) {
                    throw new RuntimeException("Database connection pool exhausted");
                } else {
                    throw new IllegalStateException("Transaction deadlock detected");
                }
            }
            
            // Success on 3rd attempt
            return Arrays.asList("user1", "user2", "user3");
        }
        
        public void reset() {
            attempts = 0;
        }
    }
    
    /**
     * Demonstrates basic retry functionality
     */
    public static void demonstrateBasicRetry() {
        System.out.println("\n=== Basic Retry Demonstration ===");
        
        NetworkService networkService = new NetworkService();
        
        try {
            String result = retry(() -> networkService.fetchData("/api/users"));
            System.out.printf("✓ Success: %s%n", result);
            
        } catch (Exception e) {
            System.err.printf("❌ Final failure: %s%n", e.getMessage());
        }
    }
    
    /**
     * Demonstrates custom retry configuration
     */
    public static void demonstrateCustomRetryConfig() {
        System.out.println("\n=== Custom Retry Configuration ===");
        
        DatabaseService dbService = new DatabaseService();
        
        // Custom config: 4 attempts, 200ms initial delay, 3x backoff, max 5s delay
        RetryConfig customConfig = new RetryConfig(
            4, 
            Duration.ofMillis(200), 
            3.0, 
            Duration.ofSeconds(5),
            RuntimeException.class, 
            IllegalStateException.class
        );
        
        RetryResult<List<String>> result = executeWithRetry(
            () -> dbService.queryUsers("SELECT * FROM users"), 
            customConfig
        );
        
        if (result.isSuccessful()) {
            System.out.printf("✓ Query result: %s%n", result.getResult());
            System.out.printf("✓ Attempts made: %d%n", result.getAttemptsMade());
            System.out.printf("✓ Total time: %d ms%n", result.getTotalTime().toMillis());
        } else {
            System.err.printf("❌ Query failed after %d attempts%n", result.getAttemptsMade());
            System.err.printf("❌ Exceptions: %s%n", result.getExceptions().size());
        }
    }
    
    /**
     * Demonstrates concurrent retry operations
     */
    public static void demonstrateConcurrentRetries() {
        System.out.println("\n=== Concurrent Retry Operations ===");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        NetworkService networkService = new NetworkService();
        
        // Submit multiple retry operations concurrently
        Future<String>[] futures = new Future[3];
        
        for (int i = 0; i < 3; i++) {
            final String endpoint = "/api/data/" + (i + 1);
            
            futures[i] = executor.submit(() -> {
                try {
                    return retry(() -> networkService.fetchData(endpoint), 
                               RetryConfig.networkConfig());
                } catch (Exception e) {
                    return "Failed: " + e.getMessage();
                }
            });
        }
        
        // Collect results
        for (int i = 0; i < futures.length; i++) {
            try {
                String result = futures[i].get(30, TimeUnit.SECONDS);
                System.out.printf("Concurrent operation %d result: %s%n", i + 1, result);
            } catch (Exception e) {
                System.err.printf("Concurrent operation %d failed: %s%n", i + 1, e.getMessage());
            }
        }
        
        executor.shutdown();
    }
    
    /**
     * Demonstrates retry with non-retryable exceptions
     */
    public static void demonstrateNonRetryableExceptions() {
        System.out.println("\n=== Non-Retryable Exception Handling ===");
        
        RetryConfig strictConfig = new RetryConfig(
            3, 
            Duration.ofMillis(100), 
            2.0, 
            Duration.ofSeconds(1),
            RuntimeException.class  // Only retry RuntimeException
        );
        
        // This will not be retried because IllegalArgumentException is not in retryable list
        RetryResult<String> result = executeWithRetry(() -> {
            throw new IllegalArgumentException("Invalid input parameter");
        }, strictConfig);
        
        System.out.printf("Non-retryable exception attempts: %d%n", result.getAttemptsMade());
        System.out.printf("Non-retryable exception successful: %s%n", result.isSuccessful());
    }
    
    /**
     * Performance and timing analysis
     */
    public static void analyzeRetryPerformance() {
        System.out.println("\n=== Retry Performance Analysis ===");
        
        long[] delays = new long[5];
        RetryConfig config = new RetryConfig(5, Duration.ofMillis(100), 2.0, Duration.ofSeconds(5));
        
        System.out.println("Calculated delays for exponential backoff:");
        for (int i = 1; i <= 5; i++) {
            Duration delay = calculateDelay(i, config);
            delays[i-1] = delay.toMillis();
            System.out.printf("Attempt %d: %d ms%n", i, delays[i-1]);
        }
        
        long totalTime = Arrays.stream(delays).sum();
        System.out.printf("Total potential delay time: %d ms%n", totalTime);
        
        // Demonstrate actual timing
        RetryResult<String> timedResult = executeWithRetry(() -> {
            throw new RuntimeException("Simulated failure");
        }, config);
        
        System.out.printf("Actual total time: %d ms%n", timedResult.getTotalTime().toMillis());
    }
    
    public static void main(String[] args) {
        System.out.println("=== Retry Utility with Exponential Backoff ===");
        
        // Demonstrate various retry scenarios
        demonstrateBasicRetry();
        demonstrateCustomRetryConfig();
        demonstrateNonRetryableExceptions();
        demonstrateConcurrentRetries();
        analyzeRetryPerformance();
        
        System.out.println("\n=== Benefits of Retry Utility ===");
        System.out.println("✓ Improved resilience against transient failures");
        System.out.println("✓ Configurable retry behavior for different scenarios");
        System.out.println("✓ Exponential backoff reduces system load");
        System.out.println("✓ Jitter prevents thundering herd problem");
        System.out.println("✓ Detailed logging and monitoring capabilities");
        System.out.println("✓ Thread-safe and suitable for concurrent use");
        System.out.println("✓ Customizable exception handling");
        
        System.out.println("\n=== Use Cases ===");
        System.out.println("• HTTP API calls");
        System.out.println("• Database operations");
        System.out.println("• File I/O operations");
        System.out.println("• Message queue operations");
        System.out.println("• Microservice communications");
        System.out.println("• Cloud service integrations");
    }
}
