/**
 * Question 25: Shared Counter Synchronization (Duplicate of Question 22)
 */

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.StampedLock;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

/**
 * Counter with memory visibility issues
 */
class VolatileCounter {
    private volatile int count = 0; // volatile ensures visibility but not atomicity
    private int unsafeCount = 0;    // non-volatile for comparison
    
    public void incrementVolatile() {
        count++; // Still not atomic despite volatile!
    }
    
    public void incrementUnsafe() {
        unsafeCount++; // Neither atomic nor guaranteed visible
    }
    
    public int getVolatileCount() { return count; }
    public int getUnsafeCount() { return unsafeCount; }
    
    public void reset() {
        count = 0;
        unsafeCount = 0;
    }
}

/**
 * Counter using Compare-And-Swap (CAS) operations
 */
class CASCounter {
    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicReference<String> lastUpdater = new AtomicReference<>("none");
    
    public int incrementAndGet(String threadName) {
        int newValue = count.incrementAndGet();
        lastUpdater.set(threadName);
        return newValue;
    }
    
    public boolean compareAndSet(int expected, int update, String threadName) {
        boolean success = count.compareAndSet(expected, update);
        if (success) {
            lastUpdater.set(threadName);
        }
        return success;
    }
    
    public int addAndGet(int delta, String threadName) {
        int result = count.addAndGet(delta);
        lastUpdater.set(threadName);
        return result;
    }
    
    public int getAndUpdate(String threadName) {
        int result = count.getAndUpdate(current -> current * 2);
        lastUpdater.set(threadName);
        return result;
    }
    
    public int get() { return count.get(); }
    public String getLastUpdater() { return lastUpdater.get(); }
    
    public void reset() {
        count.set(0);
        lastUpdater.set("reset");
    }
}

/**
 * Counter using StampedLock for optimistic reading
 */
class StampedLockCounter {
    private int count = 0;
    private final StampedLock lock = new StampedLock();
    
    public void increment() {
        long stamp = lock.writeLock();
        try {
            count++;
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    public int getCount() {
        // Try optimistic read first
        long stamp = lock.tryOptimisticRead();
        int currentCount = count;
        
        if (!lock.validate(stamp)) {
            // Optimistic read failed, fall back to pessimistic read
            stamp = lock.readLock();
            try {
                currentCount = count;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        
        return currentCount;
    }
    
    public boolean compareAndIncrement(int expected) {
        long stamp = lock.writeLock();
        try {
            if (count == expected) {
                count++;
                return true;
            }
            return false;
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    public void reset() {
        long stamp = lock.writeLock();
        try {
            count = 0;
        } finally {
            lock.unlockWrite(stamp);
        }
    }
}

/**
 * Advanced counter worker with coordination primitives
 */
class AdvancedCounterWorker extends Thread {
    private final Object counter;
    private final int increments;
    private final CountDownLatch startLatch;
    private final CyclicBarrier finishBarrier;
    private final String counterType;
    private int operationsCompleted = 0;
    
    public AdvancedCounterWorker(Object counter, int increments, CountDownLatch startLatch, 
                               CyclicBarrier finishBarrier, String counterType, String name) {
        super(name);
        this.counter = counter;
        this.increments = increments;
        this.startLatch = startLatch;
        this.finishBarrier = finishBarrier;
        this.counterType = counterType;
    }
    
    @Override
    public void run() {
        try {
            // Wait for all threads to be ready
            startLatch.await();
            
            System.out.printf("%s started working on %s%n", getName(), counterType);
            
            for (int i = 0; i < increments; i++) {
                if (counter instanceof VolatileCounter) {
                    ((VolatileCounter) counter).incrementVolatile();
                } else if (counter instanceof CASCounter) {
                    ((CASCounter) counter).incrementAndGet(getName());
                } else if (counter instanceof StampedLockCounter) {
                    ((StampedLockCounter) counter).increment();
                }
                
                operationsCompleted++;
                
                // Yield occasionally to increase contention
                if (i % 100 == 0) {
                    Thread.yield();
                }
            }
            
            System.out.printf("%s completed %d operations%n", getName(), operationsCompleted);
            
            // Wait for all threads to finish
            finishBarrier.await();
            
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            System.err.printf("%s was interrupted%n", getName());
        }
    }
    
    public int getOperationsCompleted() { return operationsCompleted; }
}

/**
 * Memory visibility demonstration worker
 */
class VisibilityWorker extends Thread {
    private final VolatileCounter counter;
    private final boolean useUnsafe;
    private int lastSeenValue = -1;
    private int updatesDetected = 0;
    
    public VisibilityWorker(VolatileCounter counter, boolean useUnsafe, String name) {
        super(name);
        this.counter = counter;
        this.useUnsafe = useUnsafe;
    }
    
    @Override
    public void run() {
        System.out.printf("%s started monitoring %s counter%n", 
                        getName(), useUnsafe ? "unsafe" : "volatile");
        
        for (int i = 0; i < 1000; i++) {
            int currentValue = useUnsafe ? counter.getUnsafeCount() : counter.getVolatileCount();
            
            if (currentValue != lastSeenValue) {
                updatesDetected++;
                lastSeenValue = currentValue;
                
                if (updatesDetected % 100 == 0) {
                    System.out.printf("%s detected update: %d%n", getName(), currentValue);
                }
            }
            
            // Small delay to allow other threads to update
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        System.out.printf("%s detected %d updates (final value: %d)%n", 
                        getName(), updatesDetected, lastSeenValue);
    }
    
    public int getUpdatesDetected() { return updatesDetected; }
}

/**
 * Main class demonstrating advanced shared counter synchronization
 */
public class Question25_AdvancedSharedCounter {
    
    private static final int THREADS = 4;
    private static final int INCREMENTS_PER_THREAD = 2500;
    private static final int EXPECTED_TOTAL = THREADS * INCREMENTS_PER_THREAD;
    
    /**
     * Test volatile counter (shows memory visibility but not atomicity)
     */
    public static void testVolatileCounter() {
        System.out.println("=== Testing Volatile Counter ===");
        
        VolatileCounter counter = new VolatileCounter();
        CountDownLatch startLatch = new CountDownLatch(1);
        CyclicBarrier finishBarrier = new CyclicBarrier(THREADS + 1); // +1 for main thread
        
        AdvancedCounterWorker[] workers = new AdvancedCounterWorker[THREADS];
        for (int i = 0; i < THREADS; i++) {
            workers[i] = new AdvancedCounterWorker(counter, INCREMENTS_PER_THREAD, 
                                                 startLatch, finishBarrier, 
                                                 "Volatile", "VolatileWorker-" + (i + 1));
        }
        
        // Start all workers
        for (AdvancedCounterWorker worker : workers) {
            worker.start();
        }
        
        long startTime = System.currentTimeMillis();
        
        // Release all workers simultaneously
        startLatch.countDown();
        
        try {
            // Wait for all workers to finish
            finishBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        long endTime = System.currentTimeMillis();
        
        int finalCount = counter.getVolatileCount();
        System.out.printf("Expected: %d, Actual: %d, Lost updates: %d%n", 
                        EXPECTED_TOTAL, finalCount, EXPECTED_TOTAL - finalCount);
        System.out.printf("Execution time: %d ms%n", endTime - startTime);
        
        if (finalCount != EXPECTED_TOTAL) {
            System.out.println("❌ Volatile keyword ensures visibility but NOT atomicity!");
        }
        System.out.println();
    }
    
    /**
     * Test CAS-based counter
     */
    public static void testCASCounter() {
        System.out.println("=== Testing Compare-And-Swap Counter ===");
        
        CASCounter counter = new CASCounter();
        CountDownLatch startLatch = new CountDownLatch(1);
        CyclicBarrier finishBarrier = new CyclicBarrier(THREADS + 1);
        
        AdvancedCounterWorker[] workers = new AdvancedCounterWorker[THREADS];
        for (int i = 0; i < THREADS; i++) {
            workers[i] = new AdvancedCounterWorker(counter, INCREMENTS_PER_THREAD, 
                                                 startLatch, finishBarrier, 
                                                 "CAS", "CASWorker-" + (i + 1));
        }
        
        // Start all workers
        for (AdvancedCounterWorker worker : workers) {
            worker.start();
        }
        
        long startTime = System.currentTimeMillis();
        startLatch.countDown();
        
        try {
            finishBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        long endTime = System.currentTimeMillis();
        
        int finalCount = counter.get();
        System.out.printf("Expected: %d, Actual: %d%n", EXPECTED_TOTAL, finalCount);
        System.out.printf("Last updater: %s%n", counter.getLastUpdater());
        System.out.printf("Execution time: %d ms%n", endTime - startTime);
        
        if (finalCount == EXPECTED_TOTAL) {
            System.out.println("✓ CAS operations are atomic and thread-safe!");
        }
        
        // Demonstrate other CAS operations
        System.out.println("\nDemonstrating advanced CAS operations:");
        counter.reset();
        
        // Compare and set
        boolean cas1 = counter.compareAndSet(0, 100, "Demo");
        System.out.printf("CAS(0 -> 100): %s, Value: %d%n", cas1, counter.get());
        
        boolean cas2 = counter.compareAndSet(50, 200, "Demo"); // Should fail
        System.out.printf("CAS(50 -> 200): %s, Value: %d%n", cas2, counter.get());
        
        // Add and get
        int addResult = counter.addAndGet(25, "Demo");
        System.out.printf("AddAndGet(25): %d%n", addResult);
        
        // Get and update (double the value)
        int oldValue = counter.getAndUpdate("Demo");
        System.out.printf("GetAndUpdate (double): old=%d, new=%d%n", oldValue, counter.get());
        
        System.out.println();
    }
    
    /**
     * Test StampedLock counter
     */
    public static void testStampedLockCounter() {
        System.out.println("=== Testing StampedLock Counter ===");
        
        StampedLockCounter counter = new StampedLockCounter();
        CountDownLatch startLatch = new CountDownLatch(1);
        CyclicBarrier finishBarrier = new CyclicBarrier(THREADS + 1);
        
        AdvancedCounterWorker[] workers = new AdvancedCounterWorker[THREADS];
        for (int i = 0; i < THREADS; i++) {
            workers[i] = new AdvancedCounterWorker(counter, INCREMENTS_PER_THREAD, 
                                                 startLatch, finishBarrier, 
                                                 "StampedLock", "StampedWorker-" + (i + 1));
        }
        
        // Also create readers to test optimistic reads
        Thread[] readers = new Thread[2];
        for (int i = 0; i < readers.length; i++) {
            final int readerIndex = i;
            readers[i] = new Thread(() -> {
                try {
                    startLatch.await();
                    
                    for (int j = 0; j < 1000; j++) {
                        int value = counter.getCount();
                        if (j % 200 == 0) {
                            System.out.printf("Reader-%d: %d%n", readerIndex + 1, value);
                        }
                        Thread.sleep(1);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Reader-" + (i + 1));
        }
        
        // Start all threads
        for (AdvancedCounterWorker worker : workers) {
            worker.start();
        }
        for (Thread reader : readers) {
            reader.start();
        }
        
        long startTime = System.currentTimeMillis();
        startLatch.countDown();
        
        try {
            finishBarrier.await();
            
            // Wait for readers to finish
            for (Thread reader : readers) {
                reader.join();
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        long endTime = System.currentTimeMillis();
        
        int finalCount = counter.getCount();
        System.out.printf("Expected: %d, Actual: %d%n", EXPECTED_TOTAL, finalCount);
        System.out.printf("Execution time: %d ms%n", endTime - startTime);
        
        if (finalCount == EXPECTED_TOTAL) {
            System.out.println("✓ StampedLock provides thread-safe access with optimistic reads!");
        }
        System.out.println();
    }
    
    /**
     * Demonstrate memory visibility issues
     */
    public static void demonstrateMemoryVisibility() {
        System.out.println("=== Memory Visibility Demonstration ===");
        
        VolatileCounter counter = new VolatileCounter();
        
        // Create updater threads
        Thread volatileUpdater = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                counter.incrementVolatile();
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "VolatileUpdater");
        
        Thread unsafeUpdater = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                counter.incrementUnsafe();
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "UnsafeUpdater");
        
        // Create observer threads
        VisibilityWorker volatileObserver = new VisibilityWorker(counter, false, "VolatileObserver");
        VisibilityWorker unsafeObserver = new VisibilityWorker(counter, true, "UnsafeObserver");
        
        // Start all threads
        volatileUpdater.start();
        unsafeUpdater.start();
        volatileObserver.start();
        unsafeObserver.start();
        
        try {
            volatileUpdater.join();
            unsafeUpdater.join();
            volatileObserver.join();
            unsafeObserver.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        System.out.printf("Final volatile count: %d%n", counter.getVolatileCount());
        System.out.printf("Final unsafe count: %d%n", counter.getUnsafeCount());
        System.out.printf("Volatile observer detected %d updates%n", volatileObserver.getUpdatesDetected());
        System.out.printf("Unsafe observer detected %d updates%n", unsafeObserver.getUpdatesDetected());
        
        System.out.println("Note: Volatile ensures visibility, unsafe variables may have stale reads");
        System.out.println();
    }
    
    /**
     * Performance comparison of synchronization methods
     */
    public static void performanceComparison() {
        System.out.println("=== Performance Comparison ===");
        
        int iterations = 3;
        long[] volatileTimes = new long[iterations];
        long[] casTimes = new long[iterations];
        long[] stampedTimes = new long[iterations];
        
        for (int i = 0; i < iterations; i++) {
            System.out.printf("Performance test iteration %d...%n", i + 1);
            
            // Test volatile counter
            VolatileCounter volatileCounter = new VolatileCounter();
            CountDownLatch startLatch1 = new CountDownLatch(1);
            CyclicBarrier barrier1 = new CyclicBarrier(THREADS + 1);
            
            AdvancedCounterWorker[] volatileWorkers = new AdvancedCounterWorker[THREADS];
            for (int j = 0; j < THREADS; j++) {
                volatileWorkers[j] = new AdvancedCounterWorker(volatileCounter, 1000, 
                                                             startLatch1, barrier1, "Perf", "VW" + j);
            }
            
            for (AdvancedCounterWorker worker : volatileWorkers) worker.start();
            
            long start = System.nanoTime();
            startLatch1.countDown();
            try { barrier1.await(); } catch (Exception e) { continue; }
            volatileTimes[i] = System.nanoTime() - start;
            
            // Test CAS counter
            CASCounter casCounter = new CASCounter();
            CountDownLatch startLatch2 = new CountDownLatch(1);
            CyclicBarrier barrier2 = new CyclicBarrier(THREADS + 1);
            
            AdvancedCounterWorker[] casWorkers = new AdvancedCounterWorker[THREADS];
            for (int j = 0; j < THREADS; j++) {
                casWorkers[j] = new AdvancedCounterWorker(casCounter, 1000, 
                                                        startLatch2, barrier2, "Perf", "CW" + j);
            }
            
            for (AdvancedCounterWorker worker : casWorkers) worker.start();
            
            start = System.nanoTime();
            startLatch2.countDown();
            try { barrier2.await(); } catch (Exception e) { continue; }
            casTimes[i] = System.nanoTime() - start;
            
            // Test StampedLock counter
            StampedLockCounter stampedCounter = new StampedLockCounter();
            CountDownLatch startLatch3 = new CountDownLatch(1);
            CyclicBarrier barrier3 = new CyclicBarrier(THREADS + 1);
            
            AdvancedCounterWorker[] stampedWorkers = new AdvancedCounterWorker[THREADS];
            for (int j = 0; j < THREADS; j++) {
                stampedWorkers[j] = new AdvancedCounterWorker(stampedCounter, 1000, 
                                                            startLatch3, barrier3, "Perf", "SW" + j);
            }
            
            for (AdvancedCounterWorker worker : stampedWorkers) worker.start();
            
            start = System.nanoTime();
            startLatch3.countDown();
            try { barrier3.await(); } catch (Exception e) { continue; }
            stampedTimes[i] = System.nanoTime() - start;
        }
        
        System.out.printf("Volatile average: %.2f ms%n", average(volatileTimes) / 1_000_000.0);
        System.out.printf("CAS average: %.2f ms%n", average(casTimes) / 1_000_000.0);
        System.out.printf("StampedLock average: %.2f ms%n", average(stampedTimes) / 1_000_000.0);
        System.out.println();
    }
    
    private static double average(long[] times) {
        long sum = 0;
        for (long time : times) {
            sum += time;
        }
        return (double) sum / times.length;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Question 25: Advanced Shared Counter Synchronization ===\n");
        
        testVolatileCounter();
        testCASCounter();
        testStampedLockCounter();
        demonstrateMemoryVisibility();
        performanceComparison();
        
        System.out.println("=== Key Concepts Demonstrated ===");
        System.out.println("• Volatile keyword for memory visibility (not atomicity)");
        System.out.println("• Compare-and-swap (CAS) operations for lock-free programming");
        System.out.println("• StampedLock for optimistic reading");
        System.out.println("• Memory visibility vs atomicity");
        System.out.println("• Coordination primitives (CountDownLatch, CyclicBarrier)");
        System.out.println("• Advanced atomic operations (getAndUpdate, compareAndSet)");
        System.out.println("• Performance characteristics of different approaches");
        System.out.println("• Happens-before relationships in concurrent programming");
    }
}
