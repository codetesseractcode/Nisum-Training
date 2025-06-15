/**
 * Question 22: Shared Counter with Synchronization
 * 
 * Explanation:
 * This program demonstrates the race condition problem that occurs when multiple threads
 * access shared data without proper synchronization. It shows:
 * 1. Unsynchronized version that produces incorrect results
 * 2. Synchronized version that fixes the race condition
 * 3. Various synchronization techniques (synchronized methods, blocks, AtomicInteger)
 */

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Unsynchronized counter that demonstrates race conditions
 */
class UnsynchronizedCounter {
    private int count = 0;
    
    public void increment() {
        count++; // This is not atomic and causes race conditions
    }
    
    public int getCount() {
        return count;
    }
    
    public void reset() {
        count = 0;
    }
}

/**
 * Synchronized counter using synchronized methods
 */
class SynchronizedMethodCounter {
    private int count = 0;
    
    public synchronized void increment() {
        count++; // Now this operation is atomic
    }
    
    public synchronized int getCount() {
        return count;
    }
    
    public synchronized void reset() {
        count = 0;
    }
}

/**
 * Synchronized counter using synchronized blocks
 */
class SynchronizedBlockCounter {
    private int count = 0;
    private final Object lock = new Object();
    
    public void increment() {
        synchronized (lock) {
            count++; // Synchronized on specific object
        }
    }
    
    public int getCount() {
        synchronized (lock) {
            return count;
        }
    }
    
    public void reset() {
        synchronized (lock) {
            count = 0;
        }
    }
}

/**
 * Counter using AtomicInteger for lock-free thread safety
 */
class AtomicCounter {
    private AtomicInteger count = new AtomicInteger(0);
    
    public void increment() {
        count.incrementAndGet(); // Atomic operation
    }
    
    public int getCount() {
        return count.get();
    }
    
    public void reset() {
        count.set(0);
    }
}

/**
 * Counter using ReentrantLock for explicit locking
 */
class ReentrantLockCounter {
    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();
    
    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock(); // Always unlock in finally block
        }
    }
    
    public int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
    
    public void reset() {
        lock.lock();
        try {
            count = 0;
        } finally {
            lock.unlock();
        }
    }
}

/**
 * Thread worker that increments a counter
 */
class CounterWorker extends Thread {
    private final Object counter;
    private final int increments;
    private final String counterType;
    
    public CounterWorker(Object counter, int increments, String counterType, String threadName) {
        super(threadName);
        this.counter = counter;
        this.increments = increments;
        this.counterType = counterType;
    }
    
    @Override
    public void run() {
        System.out.println(getName() + " started incrementing " + counterType);
        
        for (int i = 0; i < increments; i++) {
            if (counter instanceof UnsynchronizedCounter) {
                ((UnsynchronizedCounter) counter).increment();
            } else if (counter instanceof SynchronizedMethodCounter) {
                ((SynchronizedMethodCounter) counter).increment();
            } else if (counter instanceof SynchronizedBlockCounter) {
                ((SynchronizedBlockCounter) counter).increment();
            } else if (counter instanceof AtomicCounter) {
                ((AtomicCounter) counter).increment();
            } else if (counter instanceof ReentrantLockCounter) {
                ((ReentrantLockCounter) counter).increment();
            }
            
            // Yield occasionally to increase chance of race conditions
            if (i % 100 == 0) {
                Thread.yield();
            }
        }
        
        System.out.println(getName() + " finished incrementing " + counterType);
    }
}

/**
 * Main class demonstrating thread synchronization with shared counter
 */
public class Question22_SharedCounterSync {
    
    private static final int INCREMENTS_PER_THREAD = 1000;
    private static final int EXPECTED_TOTAL = 2 * INCREMENTS_PER_THREAD;
    
    /**
     * Test unsynchronized counter (will show race condition)
     */
    public static void testUnsynchronizedCounter() {
        System.out.println("=== Testing Unsynchronized Counter ===");
        
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.println("Attempt " + attempt + ":");
            
            UnsynchronizedCounter counter = new UnsynchronizedCounter();
            
            Thread thread1 = new CounterWorker(counter, INCREMENTS_PER_THREAD, 
                                             "Unsynchronized", "Thread-1");
            Thread thread2 = new CounterWorker(counter, INCREMENTS_PER_THREAD, 
                                             "Unsynchronized", "Thread-2");
            
            long startTime = System.currentTimeMillis();
            
            thread1.start();
            thread2.start();
            
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            
            long endTime = System.currentTimeMillis();
            int finalCount = counter.getCount();
            
            System.out.printf("Expected: %d, Actual: %d, Difference: %d, Time: %dms%n", 
                            EXPECTED_TOTAL, finalCount, EXPECTED_TOTAL - finalCount, 
                            endTime - startTime);
            
            if (finalCount != EXPECTED_TOTAL) {
                System.out.println("❌ Race condition detected!");
            } else {
                System.out.println("✓ No race condition this time (lucky!)");
            }
        }
        System.out.println();
    }
    
    /**
     * Test synchronized method counter
     */
    public static void testSynchronizedMethodCounter() {
        System.out.println("=== Testing Synchronized Method Counter ===");
        
        SynchronizedMethodCounter counter = new SynchronizedMethodCounter();
        
        Thread thread1 = new CounterWorker(counter, INCREMENTS_PER_THREAD, 
                                         "SynchronizedMethod", "Thread-1");
        Thread thread2 = new CounterWorker(counter, INCREMENTS_PER_THREAD, 
                                         "SynchronizedMethod", "Thread-2");
        
        long startTime = System.currentTimeMillis();
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        long endTime = System.currentTimeMillis();
        int finalCount = counter.getCount();
        
        System.out.printf("Expected: %d, Actual: %d, Time: %dms%n", 
                        EXPECTED_TOTAL, finalCount, endTime - startTime);
        
        if (finalCount == EXPECTED_TOTAL) {
            System.out.println("✓ Synchronization successful!");
        } else {
            System.out.println("❌ Synchronization failed!");
        }
        System.out.println();
    }
    
    /**
     * Test synchronized block counter
     */
    public static void testSynchronizedBlockCounter() {
        System.out.println("=== Testing Synchronized Block Counter ===");
        
        SynchronizedBlockCounter counter = new SynchronizedBlockCounter();
        
        Thread thread1 = new CounterWorker(counter, INCREMENTS_PER_THREAD, 
                                         "SynchronizedBlock", "Thread-1");
        Thread thread2 = new CounterWorker(counter, INCREMENTS_PER_THREAD, 
                                         "SynchronizedBlock", "Thread-2");
        
        long startTime = System.currentTimeMillis();
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        long endTime = System.currentTimeMillis();
        int finalCount = counter.getCount();
        
        System.out.printf("Expected: %d, Actual: %d, Time: %dms%n", 
                        EXPECTED_TOTAL, finalCount, endTime - startTime);
        
        if (finalCount == EXPECTED_TOTAL) {
            System.out.println("✓ Synchronization successful!");
        } else {
            System.out.println("❌ Synchronization failed!");
        }
        System.out.println();
    }
    
    /**
     * Test AtomicInteger counter
     */
    public static void testAtomicCounter() {
        System.out.println("=== Testing AtomicInteger Counter ===");
        
        AtomicCounter counter = new AtomicCounter();
        
        Thread thread1 = new CounterWorker(counter, INCREMENTS_PER_THREAD, 
                                         "Atomic", "Thread-1");
        Thread thread2 = new CounterWorker(counter, INCREMENTS_PER_THREAD, 
                                         "Atomic", "Thread-2");
        
        long startTime = System.currentTimeMillis();
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        long endTime = System.currentTimeMillis();
        int finalCount = counter.getCount();
        
        System.out.printf("Expected: %d, Actual: %d, Time: %dms%n", 
                        EXPECTED_TOTAL, finalCount, endTime - startTime);
        
        if (finalCount == EXPECTED_TOTAL) {
            System.out.println("✓ Atomic operations successful!");
        } else {
            System.out.println("❌ Atomic operations failed!");
        }
        System.out.println();
    }
    
    /**
     * Test ReentrantLock counter
     */
    public static void testReentrantLockCounter() {
        System.out.println("=== Testing ReentrantLock Counter ===");
        
        ReentrantLockCounter counter = new ReentrantLockCounter();
        
        Thread thread1 = new CounterWorker(counter, INCREMENTS_PER_THREAD, 
                                         "ReentrantLock", "Thread-1");
        Thread thread2 = new CounterWorker(counter, INCREMENTS_PER_THREAD, 
                                         "ReentrantLock", "Thread-2");
        
        long startTime = System.currentTimeMillis();
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        long endTime = System.currentTimeMillis();
        int finalCount = counter.getCount();
        
        System.out.printf("Expected: %d, Actual: %d, Time: %dms%n", 
                        EXPECTED_TOTAL, finalCount, endTime - startTime);
        
        if (finalCount == EXPECTED_TOTAL) {
            System.out.println("✓ ReentrantLock synchronization successful!");
        } else {
            System.out.println("❌ ReentrantLock synchronization failed!");
        }
        System.out.println();
    }
    
    /**
     * Performance comparison of different synchronization methods
     */
    public static void performanceComparison() {
        System.out.println("=== Performance Comparison ===");
        
        int iterations = 5;
        long[] unsyncTimes = new long[iterations];
        long[] syncMethodTimes = new long[iterations];
        long[] syncBlockTimes = new long[iterations];
        long[] atomicTimes = new long[iterations];
        long[] lockTimes = new long[iterations];
        
        for (int i = 0; i < iterations; i++) {
            // Unsynchronized (for timing comparison only)
            UnsynchronizedCounter unsyncCounter = new UnsynchronizedCounter();
            long start = System.nanoTime();
            Thread t1 = new CounterWorker(unsyncCounter, INCREMENTS_PER_THREAD, "Perf", "T1");
            Thread t2 = new CounterWorker(unsyncCounter, INCREMENTS_PER_THREAD, "Perf", "T2");
            t1.start(); t2.start();
            try { t1.join(); t2.join(); } catch (InterruptedException e) { break; }
            unsyncTimes[i] = System.nanoTime() - start;
            
            // Synchronized method
            SynchronizedMethodCounter syncMethodCounter = new SynchronizedMethodCounter();
            start = System.nanoTime();
            t1 = new CounterWorker(syncMethodCounter, INCREMENTS_PER_THREAD, "Perf", "T1");
            t2 = new CounterWorker(syncMethodCounter, INCREMENTS_PER_THREAD, "Perf", "T2");
            t1.start(); t2.start();
            try { t1.join(); t2.join(); } catch (InterruptedException e) { break; }
            syncMethodTimes[i] = System.nanoTime() - start;
            
            // Synchronized block
            SynchronizedBlockCounter syncBlockCounter = new SynchronizedBlockCounter();
            start = System.nanoTime();
            t1 = new CounterWorker(syncBlockCounter, INCREMENTS_PER_THREAD, "Perf", "T1");
            t2 = new CounterWorker(syncBlockCounter, INCREMENTS_PER_THREAD, "Perf", "T2");
            t1.start(); t2.start();
            try { t1.join(); t2.join(); } catch (InterruptedException e) { break; }
            syncBlockTimes[i] = System.nanoTime() - start;
            
            // AtomicInteger
            AtomicCounter atomicCounter = new AtomicCounter();
            start = System.nanoTime();
            t1 = new CounterWorker(atomicCounter, INCREMENTS_PER_THREAD, "Perf", "T1");
            t2 = new CounterWorker(atomicCounter, INCREMENTS_PER_THREAD, "Perf", "T2");
            t1.start(); t2.start();
            try { t1.join(); t2.join(); } catch (InterruptedException e) { break; }
            atomicTimes[i] = System.nanoTime() - start;
            
            // ReentrantLock
            ReentrantLockCounter lockCounter = new ReentrantLockCounter();
            start = System.nanoTime();
            t1 = new CounterWorker(lockCounter, INCREMENTS_PER_THREAD, "Perf", "T1");
            t2 = new CounterWorker(lockCounter, INCREMENTS_PER_THREAD, "Perf", "T2");
            t1.start(); t2.start();
            try { t1.join(); t2.join(); } catch (InterruptedException e) { break; }
            lockTimes[i] = System.nanoTime() - start;
        }
        
        System.out.printf("Unsynchronized avg: %.2f ms%n", average(unsyncTimes) / 1_000_000.0);
        System.out.printf("Synchronized method avg: %.2f ms%n", average(syncMethodTimes) / 1_000_000.0);
        System.out.printf("Synchronized block avg: %.2f ms%n", average(syncBlockTimes) / 1_000_000.0);
        System.out.printf("AtomicInteger avg: %.2f ms%n", average(atomicTimes) / 1_000_000.0);
        System.out.printf("ReentrantLock avg: %.2f ms%n", average(lockTimes) / 1_000_000.0);
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
        System.out.println("=== Question 22: Shared Counter with Synchronization ===\n");
        
        testUnsynchronizedCounter();
        testSynchronizedMethodCounter();
        testSynchronizedBlockCounter();
        testAtomicCounter();
        testReentrantLockCounter();
        performanceComparison();
        
        System.out.println("=== Key Concepts Demonstrated ===");
        System.out.println("• Race conditions in unsynchronized code");
        System.out.println("• synchronized methods for thread safety");
        System.out.println("• synchronized blocks with custom locks");
        System.out.println("• AtomicInteger for lock-free thread safety");
        System.out.println("• ReentrantLock for explicit locking");
        System.out.println("• Performance comparison of synchronization methods");
        System.out.println("• Proper exception handling in multithreaded code");
    }
}
