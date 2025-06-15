/**
 * Question 28: Multi-threaded Prime Number Calculation
 */

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Sequential prime calculator for comparison
 */
class SequentialPrimeCalculator {
    
    public static boolean isPrime(long n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        
        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
    
    public static long sumPrimesUpTo(long limit) {
        long sum = 0;
        for (long i = 2; i <= limit; i++) {
            if (isPrime(i)) {
                sum += i;
            }
        }
        return sum;
    }
    
    public static List<Long> findPrimesUpTo(long limit) {
        List<Long> primes = new ArrayList<>();
        for (long i = 2; i <= limit; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }
        return primes;
    }
}

/**
 * Range-based parallel prime calculator
 */
class RangeBasedPrimeCalculator {
    private final ExecutorService executor;
    private final int numThreads;
    
    public RangeBasedPrimeCalculator(int numThreads) {
        this.numThreads = numThreads;
        this.executor = Executors.newFixedThreadPool(numThreads);
    }
    
    public long sumPrimesUpTo(long limit) {
        AtomicLong totalSum = new AtomicLong(0);
        CountDownLatch latch = new CountDownLatch(numThreads);
        
        long rangeSize = (limit - 1) / numThreads + 1;
        
        for (int i = 0; i < numThreads; i++) {
            final long start = 2 + i * rangeSize;
            final long end = Math.min(start + rangeSize - 1, limit);
            
            executor.submit(() -> {
                try {
                    long localSum = 0;
                    for (long num = start; num <= end; num++) {
                        if (SequentialPrimeCalculator.isPrime(num)) {
                            localSum += num;
                        }
                    }
                    totalSum.addAndGet(localSum);
                    System.out.printf("Thread %s processed range [%d, %d], local sum: %d%n", 
                                    Thread.currentThread().getName(), start, end, localSum);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Prime calculation interrupted", e);
        }
        
        return totalSum.get();
    }
    
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Fork-Join prime calculator with work stealing
 */
class ForkJoinPrimeCalculator extends RecursiveTask<Long> {
    private final long start;
    private final long end;
    private final long threshold;
    
    public ForkJoinPrimeCalculator(long start, long end, long threshold) {
        this.start = start;
        this.end = end;
        this.threshold = threshold;
    }
    
    @Override
    protected Long compute() {
        if (end - start <= threshold) {
            // Sequential computation for small ranges
            long sum = 0;
            for (long i = start; i <= end; i++) {
                if (SequentialPrimeCalculator.isPrime(i)) {
                    sum += i;
                }
            }
            return sum;
        } else {
            // Divide the range
            long mid = start + (end - start) / 2;
            
            ForkJoinPrimeCalculator leftTask = new ForkJoinPrimeCalculator(start, mid, threshold);
            ForkJoinPrimeCalculator rightTask = new ForkJoinPrimeCalculator(mid + 1, end, threshold);
            
            // Fork left task and compute right task
            leftTask.fork();
            Long rightResult = rightTask.compute();
            Long leftResult = leftTask.join();
            
            return leftResult + rightResult;
        }
    }
}

/**
 * Parallel Sieve of Eratosthenes
 */
class ParallelSieveCalculator {
    private final ExecutorService executor;
    private final int numThreads;
    
    public ParallelSieveCalculator(int numThreads) {
        this.numThreads = numThreads;
        this.executor = Executors.newFixedThreadPool(numThreads);
    }
    
    public long sumPrimesUpTo(long limit) {
        if (limit < 2) return 0;
        
        // Use BitSet for memory efficiency
        BitSet isPrime = new BitSet((int) limit + 1);
        isPrime.set(2, (int) limit + 1); // Initially assume all numbers are prime
        
        // Sequential sieving for small primes (better than parallel for small ranges)
        long sqrtLimit = (long) Math.sqrt(limit);
        for (long p = 2; p <= sqrtLimit; p++) {
            if (isPrime.get((int) p)) {
                // Parallel sieving for multiples of p
                parallelSieve(isPrime, p, limit);
            }
        }
        
        // Parallel sum calculation
        return parallelSum(isPrime, 2, limit);
    }
    
    private void parallelSieve(BitSet isPrime, long prime, long limit) {
        CountDownLatch latch = new CountDownLatch(numThreads);
        
        long start = prime * prime;
        long rangeSize = (limit - start) / numThreads + 1;
        
        for (int i = 0; i < numThreads; i++) {
            final long threadStart = start + i * rangeSize;
            final long threadEnd = Math.min(threadStart + rangeSize - 1, limit);
            
            if (threadStart <= limit) {
                executor.submit(() -> {
                    try {
                        // Find first multiple in this range
                        long firstMultiple = threadStart;
                        long remainder = firstMultiple % prime;
                        if (remainder != 0) {
                            firstMultiple += prime - remainder;
                        }
                        
                        // Mark all multiples in this range
                        for (long multiple = firstMultiple; multiple <= threadEnd; multiple += prime) {
                            isPrime.clear((int) multiple);
                        }
                    } finally {
                        latch.countDown();
                    }
                });
            } else {
                latch.countDown();
            }
        }
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private long parallelSum(BitSet isPrime, long start, long limit) {
        AtomicLong totalSum = new AtomicLong(0);
        CountDownLatch latch = new CountDownLatch(numThreads);
        
        long rangeSize = (limit - start + 1) / numThreads;
        
        for (int i = 0; i < numThreads; i++) {
            final long threadStart = start + i * rangeSize;
            final long threadEnd = (i == numThreads - 1) ? limit : threadStart + rangeSize - 1;
            
            executor.submit(() -> {
                try {
                    long localSum = 0;
                    for (long num = threadStart; num <= threadEnd; num++) {
                        if (isPrime.get((int) num)) {
                            localSum += num;
                        }
                    }
                    totalSum.addAndGet(localSum);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return totalSum.get();
    }
    
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Producer-Consumer prime calculator
 */
class ProducerConsumerPrimeCalculator {
    private final BlockingQueue<Long> numberQueue;
    private final BlockingQueue<Long> primeQueue;
    private final ExecutorService producers;
    private final ExecutorService consumers;
    private final int numProducers;
    private final int numConsumers;
    private final AtomicInteger activeProducers;
    
    public ProducerConsumerPrimeCalculator(int numProducers, int numConsumers) {
        this.numProducers = numProducers;
        this.numConsumers = numConsumers;
        this.numberQueue = new ArrayBlockingQueue<>(10000);
        this.primeQueue = new ArrayBlockingQueue<>(10000);
        this.producers = Executors.newFixedThreadPool(numProducers);
        this.consumers = Executors.newFixedThreadPool(numConsumers);
        this.activeProducers = new AtomicInteger(numProducers);
    }
    
    public long sumPrimesUpTo(long limit) {
        // Start producers
        long rangeSize = limit / numProducers;
        for (int i = 0; i < numProducers; i++) {
            final long start = 2 + i * rangeSize;
            final long end = (i == numProducers - 1) ? limit : start + rangeSize - 1;
            
            producers.submit(() -> {
                try {
                    for (long num = start; num <= end; num++) {
                        numberQueue.put(num);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    if (activeProducers.decrementAndGet() == 0) {
                        // Signal end of production
                        for (int j = 0; j < numConsumers; j++) {
                            try {
                                numberQueue.put(-1L); // Poison pill
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }
            });
        }
        
        // Start consumers
        CountDownLatch consumerLatch = new CountDownLatch(numConsumers);
        for (int i = 0; i < numConsumers; i++) {
            consumers.submit(() -> {
                try {
                    while (true) {
                        Long number = numberQueue.take();
                        if (number == -1L) break; // Poison pill
                        
                        if (SequentialPrimeCalculator.isPrime(number)) {
                            primeQueue.put(number);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    consumerLatch.countDown();
                }
            });
        }
        
        // Sum collector
        AtomicLong sum = new AtomicLong(0);
        Thread sumCollector = new Thread(() -> {
            try {
                while (consumerLatch.getCount() > 0 || !primeQueue.isEmpty()) {
                    Long prime = primeQueue.poll(100, TimeUnit.MILLISECONDS);
                    if (prime != null) {
                        sum.addAndGet(prime);
                    }
                }
                
                // Collect remaining primes
                Long prime;
                while ((prime = primeQueue.poll()) != null) {
                    sum.addAndGet(prime);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        sumCollector.start();
        
        try {
            consumerLatch.await();
            sumCollector.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return sum.get();
    }
    
    public void shutdown() {
        producers.shutdown();
        consumers.shutdown();
        try {
            if (!producers.awaitTermination(60, TimeUnit.SECONDS)) {
                producers.shutdownNow();
            }
            if (!consumers.awaitTermination(60, TimeUnit.SECONDS)) {
                consumers.shutdownNow();
            }
        } catch (InterruptedException e) {
            producers.shutdownNow();
            consumers.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Optimized prime calculator with segmented sieve
 */
class SegmentedSievePrimeCalculator {
    private final ExecutorService executor;
    private final int numThreads;
    
    public SegmentedSievePrimeCalculator(int numThreads) {
        this.numThreads = numThreads;
        this.executor = Executors.newFixedThreadPool(numThreads);
    }
    
    public long sumPrimesUpTo(long limit) {
        if (limit < 2) return 0;
        
        // First, find all primes up to sqrt(limit) using simple sieve
        long sqrtLimit = (long) Math.sqrt(limit);
        List<Long> basePrimes = SequentialPrimeCalculator.findPrimesUpTo(sqrtLimit);
        
        // Divide the range [sqrt(limit)+1, limit] into segments
        long segmentSize = Math.max(sqrtLimit, 100000L);
        AtomicLong totalSum = new AtomicLong(0);
        
        // Add sum of base primes
        totalSum.addAndGet(basePrimes.stream().mapToLong(Long::longValue).sum());
        
        // Process segments in parallel
        CountDownLatch latch = new CountDownLatch(numThreads);
        AtomicLong nextSegmentStart = new AtomicLong(sqrtLimit + 1);
        
        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                try {
                    while (true) {
                        long segStart = nextSegmentStart.getAndAdd(segmentSize);
                        if (segStart > limit) break;
                        
                        long segEnd = Math.min(segStart + segmentSize - 1, limit);
                        long segmentSum = processSegment(basePrimes, segStart, segEnd);
                        totalSum.addAndGet(segmentSum);
                        
                        System.out.printf("Thread %s processed segment [%d, %d], sum: %d%n", 
                                        Thread.currentThread().getName(), segStart, segEnd, segmentSum);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return totalSum.get();
    }
    
    private long processSegment(List<Long> basePrimes, long segStart, long segEnd) {
        // Create a boolean array for this segment
        int segmentSize = (int) (segEnd - segStart + 1);
        boolean[] isPrime = new boolean[segmentSize];
        java.util.Arrays.fill(isPrime, true);
        
        // Sieve this segment using base primes
        for (long prime : basePrimes) {
            // Find the first multiple of prime in this segment
            long firstMultiple = ((segStart + prime - 1) / prime) * prime;
            if (firstMultiple < segStart) firstMultiple += prime;
            
            // Mark multiples as composite
            for (long multiple = firstMultiple; multiple <= segEnd; multiple += prime) {
                isPrime[(int) (multiple - segStart)] = false;
            }
        }
        
        // Sum the primes in this segment
        long sum = 0;
        for (int i = 0; i < segmentSize; i++) {
            if (isPrime[i]) {
                sum += segStart + i;
            }
        }
        
        return sum;
    }
    
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Main class demonstrating multi-threaded prime calculation
 */
public class Question28_MultithreadedPrimeCalculation {
    
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    
    /**
     * Test different prime calculation approaches
     */
    public static void testPrimeCalculationApproaches() {
        System.out.println("=== Testing Prime Calculation Approaches ===");
        
        long limit = 100000;
        
        // Sequential calculation
        System.out.println("Sequential calculation:");
        long start = System.currentTimeMillis();
        long sequentialSum = SequentialPrimeCalculator.sumPrimesUpTo(limit);
        long sequentialTime = System.currentTimeMillis() - start;
        System.out.printf("Sum of primes up to %d: %d%n", limit, sequentialSum);
        System.out.printf("Time: %d ms%n%n", sequentialTime);
        
        // Range-based parallel calculation
        System.out.println("Range-based parallel calculation:");
        RangeBasedPrimeCalculator rangeCalculator = new RangeBasedPrimeCalculator(NUM_THREADS);
        start = System.currentTimeMillis();
        long rangeSum = rangeCalculator.sumPrimesUpTo(limit);
        long rangeTime = System.currentTimeMillis() - start;
        System.out.printf("Sum of primes: %d%n", rangeSum);
        System.out.printf("Time: %d ms (%.2fx speedup)%n", rangeTime, (double) sequentialTime / rangeTime);
        System.out.printf("Results match: %s%n%n", sequentialSum == rangeSum);
        rangeCalculator.shutdown();
        
        // Fork-Join calculation
        System.out.println("Fork-Join calculation:");
        ForkJoinPool forkJoinPool = new ForkJoinPool(NUM_THREADS);
        start = System.currentTimeMillis();
        long forkJoinSum = forkJoinPool.invoke(new ForkJoinPrimeCalculator(2, limit, 10000));
        long forkJoinTime = System.currentTimeMillis() - start;
        System.out.printf("Sum of primes: %d%n", forkJoinSum);
        System.out.printf("Time: %d ms (%.2fx speedup)%n", forkJoinTime, (double) sequentialTime / forkJoinTime);
        System.out.printf("Results match: %s%n%n", sequentialSum == forkJoinSum);
        forkJoinPool.shutdown();
        
        // Parallel Sieve calculation
        System.out.println("Parallel Sieve calculation:");
        ParallelSieveCalculator sieveCalculator = new ParallelSieveCalculator(NUM_THREADS);
        start = System.currentTimeMillis();
        long sieveSum = sieveCalculator.sumPrimesUpTo(limit);
        long sieveTime = System.currentTimeMillis() - start;
        System.out.printf("Sum of primes: %d%n", sieveSum);
        System.out.printf("Time: %d ms (%.2fx speedup)%n", sieveTime, (double) sequentialTime / sieveTime);
        System.out.printf("Results match: %s%n%n", sequentialSum == sieveSum);
        sieveCalculator.shutdown();
        
        // Producer-Consumer calculation
        System.out.println("Producer-Consumer calculation:");
        ProducerConsumerPrimeCalculator pcCalculator = new ProducerConsumerPrimeCalculator(2, NUM_THREADS - 2);
        start = System.currentTimeMillis();
        long pcSum = pcCalculator.sumPrimesUpTo(limit);
        long pcTime = System.currentTimeMillis() - start;
        System.out.printf("Sum of primes: %d%n", pcSum);
        System.out.printf("Time: %d ms (%.2fx speedup)%n", pcTime, (double) sequentialTime / pcTime);
        System.out.printf("Results match: %s%n%n", sequentialSum == pcSum);
        pcCalculator.shutdown();
    }
    
    /**
     * Performance comparison with different limits
     */
    public static void performanceComparison() {
        System.out.println("=== Performance Comparison ===");
        
        long[] limits = {10000, 50000, 100000, 500000};
        
        for (long limit : limits) {
            System.out.printf("Limit: %d%n", limit);
            
            // Sequential
            long start = System.currentTimeMillis();
            long seqSum = SequentialPrimeCalculator.sumPrimesUpTo(limit);
            long seqTime = System.currentTimeMillis() - start;
            
            // Range-based parallel
            RangeBasedPrimeCalculator rangeCalc = new RangeBasedPrimeCalculator(NUM_THREADS);
            start = System.currentTimeMillis();
            long rangeSum = rangeCalc.sumPrimesUpTo(limit);
            long rangeTime = System.currentTimeMillis() - start;
            rangeCalc.shutdown();
            
            // Segmented sieve
            SegmentedSievePrimeCalculator segCalc = new SegmentedSievePrimeCalculator(NUM_THREADS);
            start = System.currentTimeMillis();
            long segSum = segCalc.sumPrimesUpTo(limit);
            long segTime = System.currentTimeMillis() - start;
            segCalc.shutdown();
            
            System.out.printf("  Sequential: %d ms (sum: %d)%n", seqTime, seqSum);
            System.out.printf("  Range-based: %d ms (%.2fx, sum: %d)%n", 
                            rangeTime, (double) seqTime / rangeTime, rangeSum);
            System.out.printf("  Segmented: %d ms (%.2fx, sum: %d)%n", 
                            segTime, (double) seqTime / segTime, segSum);
            System.out.printf("  Results match: %s%n%n", 
                            seqSum == rangeSum && seqSum == segSum);
        }
    }
    
    /**
     * Test scalability with different thread counts
     */
    public static void testScalability() {
        System.out.println("=== Thread Scalability Analysis ===");
        
        long limit = 200000;
        int[] threadCounts = {1, 2, 4, 8, 16};
        
        for (int threads : threadCounts) {
            if (threads > NUM_THREADS) continue;
            
            RangeBasedPrimeCalculator calculator = new RangeBasedPrimeCalculator(threads);
            
            long start = System.currentTimeMillis();
            long sum = calculator.sumPrimesUpTo(limit);
            long time = System.currentTimeMillis() - start;
            
            System.out.printf("%d threads: %d ms (sum: %d)%n", threads, time, sum);
            
            calculator.shutdown();
        }
        System.out.println();
    }
    
    /**
     * Test memory efficiency of different approaches
     */
    public static void testMemoryEfficiency() {
        System.out.println("=== Memory Efficiency Analysis ===");
        
        Runtime runtime = Runtime.getRuntime();
        long limit = 100000;
        
        // Range-based approach
        System.gc();
        long memBefore = runtime.totalMemory() - runtime.freeMemory();
        
        RangeBasedPrimeCalculator rangeCalc = new RangeBasedPrimeCalculator(NUM_THREADS);
        long sum1 = rangeCalc.sumPrimesUpTo(limit);
        rangeCalc.shutdown();
        
        System.gc();
        long memAfter1 = runtime.totalMemory() - runtime.freeMemory();
        
        // Sieve approach
        System.gc();
        long memBefore2 = runtime.totalMemory() - runtime.freeMemory();
        
        ParallelSieveCalculator sieveCalc = new ParallelSieveCalculator(NUM_THREADS);
        long sum2 = sieveCalc.sumPrimesUpTo(limit);
        sieveCalc.shutdown();
        
        System.gc();
        long memAfter2 = runtime.totalMemory() - runtime.freeMemory();
        
        System.out.printf("Range-based: %d KB memory used (sum: %d)%n", 
                        (memAfter1 - memBefore) / 1024, sum1);
        System.out.printf("Sieve-based: %d KB memory used (sum: %d)%n", 
                        (memAfter2 - memBefore2) / 1024, sum2);
        System.out.println();
    }
    
    /**
     * Test accuracy with known prime sums
     */
    public static void testAccuracy() {
        System.out.println("=== Accuracy Verification ===");
        
        // Known sums for verification
        long[][] knownSums = {
            {10, 17},      // sum of primes <= 10: 2+3+5+7 = 17
            {100, 1060},   // sum of primes <= 100
            {1000, 76127}, // sum of primes <= 1000
        };
        
        for (long[] testCase : knownSums) {
            long limit = testCase[0];
            long expectedSum = testCase[1];
            
            long calculatedSum = SequentialPrimeCalculator.sumPrimesUpTo(limit);
            
            System.out.printf("Limit: %d, Expected: %d, Calculated: %d, Match: %s%n", 
                            limit, expectedSum, calculatedSum, expectedSum == calculatedSum);
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("=== Question 28: Multi-threaded Prime Number Calculation ===");
        System.out.printf("Available processors: %d%n%n", NUM_THREADS);
        
        testAccuracy();
        testPrimeCalculationApproaches();
        performanceComparison();
        testScalability();
        testMemoryEfficiency();
        
        System.out.println("=== Key Concepts Demonstrated ===");
        System.out.println("• Range-based parallelization for CPU-intensive tasks");
        System.out.println("• Fork-Join framework with work stealing");
        System.out.println("• Parallel Sieve of Eratosthenes algorithm");
        System.out.println("• Producer-consumer pattern for prime generation");
        System.out.println("• Segmented sieve for memory-efficient large range processing");
        System.out.println("• AtomicLong for thread-safe accumulation");
        System.out.println("• Performance vs memory trade-offs in parallel algorithms");
        System.out.println("• Load balancing strategies for irregular workloads");
        System.out.println("• Thread scalability analysis for computational tasks");
    }
}
