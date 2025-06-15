/**
 * Question 29: Producer-Consumer with wait() and notify()
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Basic bounded buffer using wait() and notify()
 */
class BoundedBuffer<T> {
    private final Queue<T> buffer;
    private final int capacity;
    private final Object lock = new Object();
    
    public BoundedBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();
    }
    
    public void put(T item) throws InterruptedException {
        synchronized (lock) {
            // Wait while buffer is full
            while (buffer.size() >= capacity) {
                System.out.printf("[%s] Buffer full, producer waiting...%n", 
                                Thread.currentThread().getName());
                lock.wait();
            }
            
            buffer.offer(item);
            System.out.printf("[%s] Produced: %s (buffer size: %d)%n", 
                            Thread.currentThread().getName(), item, buffer.size());
            
            // Notify waiting consumers
            lock.notifyAll();
        }
    }
    
    public T take() throws InterruptedException {
        synchronized (lock) {
            // Wait while buffer is empty
            while (buffer.isEmpty()) {
                System.out.printf("[%s] Buffer empty, consumer waiting...%n", 
                                Thread.currentThread().getName());
                lock.wait();
            }
            
            T item = buffer.poll();
            System.out.printf("[%s] Consumed: %s (buffer size: %d)%n", 
                            Thread.currentThread().getName(), item, buffer.size());
            
            // Notify waiting producers
            lock.notifyAll();
            return item;
        }
    }
    
    public int size() {
        synchronized (lock) {
            return buffer.size();
        }
    }
    
    public boolean isEmpty() {
        synchronized (lock) {
            return buffer.isEmpty();
        }
    }
}

/**
 * Enhanced bounded buffer with statistics
 */
class StatisticalBoundedBuffer<T> {
    private final Queue<T> buffer;
    private final int capacity;
    private final Object lock = new Object();
    
    // Statistics
    private long totalProduced = 0;
    private long totalConsumed = 0;
    private long totalWaitTimeProducers = 0;
    private long totalWaitTimeConsumers = 0;
    private int maxBufferSize = 0;
    
    public StatisticalBoundedBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();
    }
    
    public void put(T item) throws InterruptedException {
        long waitStart = System.currentTimeMillis();
        
        synchronized (lock) {
            // Wait while buffer is full
            while (buffer.size() >= capacity) {
                lock.wait();
            }
            
            long waitTime = System.currentTimeMillis() - waitStart;
            totalWaitTimeProducers += waitTime;
            
            buffer.offer(item);
            totalProduced++;
            maxBufferSize = Math.max(maxBufferSize, buffer.size());
            
            // Notify waiting consumers
            lock.notifyAll();
        }
    }
    
    public T take() throws InterruptedException {
        long waitStart = System.currentTimeMillis();
        
        synchronized (lock) {
            // Wait while buffer is empty
            while (buffer.isEmpty()) {
                lock.wait();
            }
            
            long waitTime = System.currentTimeMillis() - waitStart;
            totalWaitTimeConsumers += waitTime;
            
            T item = buffer.poll();
            totalConsumed++;
            
            // Notify waiting producers
            lock.notifyAll();
            return item;
        }
    }
    
    public synchronized Statistics getStatistics() {
        return new Statistics(totalProduced, totalConsumed, totalWaitTimeProducers, 
                            totalWaitTimeConsumers, maxBufferSize, buffer.size());
    }
    
    public static class Statistics {
        public final long totalProduced;
        public final long totalConsumed;
        public final long avgWaitTimeProducers;
        public final long avgWaitTimeConsumers;
        public final int maxBufferSize;
        public final int currentBufferSize;
        
        public Statistics(long totalProduced, long totalConsumed, 
                         long totalWaitTimeProducers, long totalWaitTimeConsumers,
                         int maxBufferSize, int currentBufferSize) {
            this.totalProduced = totalProduced;
            this.totalConsumed = totalConsumed;
            this.avgWaitTimeProducers = totalProduced > 0 ? totalWaitTimeProducers / totalProduced : 0;
            this.avgWaitTimeConsumers = totalConsumed > 0 ? totalWaitTimeConsumers / totalConsumed : 0;
            this.maxBufferSize = maxBufferSize;
            this.currentBufferSize = currentBufferSize;
        }
        
        @Override
        public String toString() {
            return String.format(
                "Statistics{produced=%d, consumed=%d, avgWaitProducers=%dms, " +
                "avgWaitConsumers=%dms, maxBuffer=%d, currentBuffer=%d}",
                totalProduced, totalConsumed, avgWaitTimeProducers, 
                avgWaitTimeConsumers, maxBufferSize, currentBufferSize);
        }
    }
}

/**
 * Priority-based bounded buffer
 */
class PriorityBoundedBuffer<T extends Comparable<T>> {
    private final PriorityQueue<T> buffer;
    private final int capacity;
    private final Object lock = new Object();
    
    public PriorityBoundedBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new PriorityQueue<>();
    }
    
    public void put(T item) throws InterruptedException {
        synchronized (lock) {
            while (buffer.size() >= capacity) {
                System.out.printf("[%s] Priority buffer full, waiting...%n", 
                                Thread.currentThread().getName());
                lock.wait();
            }
            
            buffer.offer(item);
            System.out.printf("[%s] Produced priority item: %s%n", 
                            Thread.currentThread().getName(), item);
            lock.notifyAll();
        }
    }
    
    public T take() throws InterruptedException {
        synchronized (lock) {
            while (buffer.isEmpty()) {
                System.out.printf("[%s] Priority buffer empty, waiting...%n", 
                                Thread.currentThread().getName());
                lock.wait();
            }
            
            T item = buffer.poll(); // Gets highest priority item
            System.out.printf("[%s] Consumed priority item: %s%n", 
                            Thread.currentThread().getName(), item);
            lock.notifyAll();
            return item;
        }
    }
}

/**
 * Work item for demonstration
 */
class WorkItem implements Comparable<WorkItem> {
    private final int id;
    private final int priority;
    private final String data;
    private final long timestamp;
    
    public WorkItem(int id, int priority, String data) {
        this.id = id;
        this.priority = priority;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
    
    public int getId() { return id; }
    public int getPriority() { return priority; }
    public String getData() { return data; }
    public long getTimestamp() { return timestamp; }
    
    @Override
    public int compareTo(WorkItem other) {
        // Higher priority first (reverse order)
        return Integer.compare(other.priority, this.priority);
    }
    
    @Override
    public String toString() {
        return String.format("WorkItem{id=%d, priority=%d, data='%s'}", id, priority, data);
    }
}

/**
 * Producer thread
 */
class Producer extends Thread {
    private final BoundedBuffer<WorkItem> buffer;
    private final int itemCount;
    private final int maxPriority;
    private final Random random;
    private final AtomicInteger itemIdGenerator;
    private int producedCount = 0;
    
    public Producer(BoundedBuffer<WorkItem> buffer, int itemCount, int maxPriority, 
                   AtomicInteger itemIdGenerator, String name) {
        super(name);
        this.buffer = buffer;
        this.itemCount = itemCount;
        this.maxPriority = maxPriority;
        this.itemIdGenerator = itemIdGenerator;
        this.random = new Random();
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < itemCount; i++) {
                int id = itemIdGenerator.incrementAndGet();
                int priority = random.nextInt(maxPriority) + 1;
                String data = "Data-" + id;
                
                WorkItem item = new WorkItem(id, priority, data);
                buffer.put(item);
                producedCount++;
                
                // Random delay between productions
                Thread.sleep(random.nextInt(100) + 50);
            }
        } catch (InterruptedException e) {
            System.out.printf("[%s] Producer interrupted%n", getName());
            Thread.currentThread().interrupt();
        }
        
        System.out.printf("[%s] Producer finished, produced %d items%n", getName(), producedCount);
    }
    
    public int getProducedCount() { return producedCount; }
}

/**
 * Consumer thread
 */
class Consumer extends Thread {
    private final BoundedBuffer<WorkItem> buffer;
    private final int itemCount;
    private final Random random;
    private int consumedCount = 0;
    
    public Consumer(BoundedBuffer<WorkItem> buffer, int itemCount, String name) {
        super(name);
        this.buffer = buffer;
        this.itemCount = itemCount;
        this.random = new Random();
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < itemCount; i++) {
                WorkItem item = buffer.take();
                consumedCount++;
                
                // Simulate processing time
                Thread.sleep(random.nextInt(150) + 75);
                
                System.out.printf("[%s] Processed %s (age: %dms)%n", 
                                getName(), item, System.currentTimeMillis() - item.getTimestamp());
            }
        } catch (InterruptedException e) {
            System.out.printf("[%s] Consumer interrupted%n", getName());
            Thread.currentThread().interrupt();
        }
        
        System.out.printf("[%s] Consumer finished, consumed %d items%n", getName(), consumedCount);
    }
    
    public int getConsumedCount() { return consumedCount; }
}

/**
 * Fast producer for stress testing
 */
class FastProducer extends Thread {
    private final StatisticalBoundedBuffer<Integer> buffer;
    private final int itemCount;
    private final AtomicLong totalProduced;
    
    public FastProducer(StatisticalBoundedBuffer<Integer> buffer, int itemCount, 
                       AtomicLong totalProduced, String name) {
        super(name);
        this.buffer = buffer;
        this.itemCount = itemCount;
        this.totalProduced = totalProduced;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < itemCount; i++) {
                buffer.put(i);
                totalProduced.incrementAndGet();
                
                if (i % 1000 == 0) {
                    System.out.printf("[%s] Produced %d items%n", getName(), i);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Fast consumer for stress testing
 */
class FastConsumer extends Thread {
    private final StatisticalBoundedBuffer<Integer> buffer;
    private final int itemCount;
    private final AtomicLong totalConsumed;
    
    public FastConsumer(StatisticalBoundedBuffer<Integer> buffer, int itemCount, 
                       AtomicLong totalConsumed, String name) {
        super(name);
        this.buffer = buffer;
        this.itemCount = itemCount;
        this.totalConsumed = totalConsumed;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < itemCount; i++) {
                Integer item = buffer.take();
                totalConsumed.incrementAndGet();
                
                if (i % 1000 == 0) {
                    System.out.printf("[%s] Consumed %d items%n", getName(), i);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Main class demonstrating producer-consumer pattern with wait() and notify()
 */
public class Question29_ProducerConsumerWaitNotify {
    
    /**
     * Basic producer-consumer demonstration
     */
    public static void basicProducerConsumerDemo() {
        System.out.println("=== Basic Producer-Consumer Demo ===");
        
        BoundedBuffer<WorkItem> buffer = new BoundedBuffer<>(5);
        AtomicInteger itemIdGenerator = new AtomicInteger(0);
        
        // Create producer and consumer
        Producer producer = new Producer(buffer, 10, 5, itemIdGenerator, "Producer-1");
        Consumer consumer = new Consumer(buffer, 10, "Consumer-1");
        
        // Start threads
        producer.start();
        consumer.start();
        
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.printf("Final buffer size: %d%n", buffer.size());
        System.out.printf("Producer produced: %d, Consumer consumed: %d%n%n", 
                        producer.getProducedCount(), consumer.getConsumedCount());
    }
    
    /**
     * Multiple producers and consumers demonstration
     */
    public static void multipleProducersConsumersDemo() {
        System.out.println("=== Multiple Producers and Consumers Demo ===");
        
        BoundedBuffer<WorkItem> buffer = new BoundedBuffer<>(8);
        AtomicInteger itemIdGenerator = new AtomicInteger(0);
        
        // Create multiple producers and consumers
        Producer[] producers = {
            new Producer(buffer, 5, 5, itemIdGenerator, "Producer-1"),
            new Producer(buffer, 5, 5, itemIdGenerator, "Producer-2"),
            new Producer(buffer, 5, 5, itemIdGenerator, "Producer-3")
        };
        
        Consumer[] consumers = {
            new Consumer(buffer, 5, "Consumer-1"),
            new Consumer(buffer, 5, "Consumer-2"),
            new Consumer(buffer, 5, "Consumer-3")
        };
        
        // Start all threads
        for (Producer producer : producers) {
            producer.start();
        }
        for (Consumer consumer : consumers) {
            consumer.start();
        }
        
        // Wait for completion
        try {
            for (Producer producer : producers) {
                producer.join();
            }
            for (Consumer consumer : consumers) {
                consumer.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Calculate totals
        int totalProduced = Arrays.stream(producers).mapToInt(Producer::getProducedCount).sum();
        int totalConsumed = Arrays.stream(consumers).mapToInt(Consumer::getConsumedCount).sum();
        
        System.out.printf("Total produced: %d, Total consumed: %d%n", totalProduced, totalConsumed);
        System.out.printf("Final buffer size: %d%n%n", buffer.size());
    }
    
    /**
     * Priority-based producer-consumer demonstration
     */
    public static void priorityProducerConsumerDemo() {
        System.out.println("=== Priority-based Producer-Consumer Demo ===");
        
        PriorityBoundedBuffer<WorkItem> buffer = new PriorityBoundedBuffer<>(6);
        AtomicInteger itemIdGenerator = new AtomicInteger(0);
        
        // Producer that creates items with different priorities
        Thread producer = new Thread(() -> {
            try {
                Random random = new Random();
                for (int i = 0; i < 10; i++) {
                    int id = itemIdGenerator.incrementAndGet();
                    int priority = random.nextInt(10) + 1; // Priority 1-10
                    WorkItem item = new WorkItem(id, priority, "PriorityData-" + id);
                    buffer.put(item);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "PriorityProducer");
        
        // Consumer that processes items in priority order
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    WorkItem item = buffer.take();
                    System.out.printf("Processing high-priority item: %s%n", item);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "PriorityConsumer");
        
        producer.start();
        // Start consumer after a delay to let some items accumulate
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        consumer.start();
        
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println();
    }
    
    /**
     * Performance and statistics demonstration
     */
    public static void performanceDemo() {
        System.out.println("=== Performance and Statistics Demo ===");
        
        StatisticalBoundedBuffer<Integer> buffer = new StatisticalBoundedBuffer<>(20);
        AtomicLong totalProduced = new AtomicLong(0);
        AtomicLong totalConsumed = new AtomicLong(0);
        
        int itemsPerThread = 5000;
        
        // Create fast producers and consumers
        FastProducer[] producers = {
            new FastProducer(buffer, itemsPerThread, totalProduced, "FastProducer-1"),
            new FastProducer(buffer, itemsPerThread, totalProduced, "FastProducer-2")
        };
        
        FastConsumer[] consumers = {
            new FastConsumer(buffer, itemsPerThread, totalConsumed, "FastConsumer-1"),
            new FastConsumer(buffer, itemsPerThread, totalConsumed, "FastConsumer-2")
        };
        
        long startTime = System.currentTimeMillis();
        
        // Start all threads
        for (FastProducer producer : producers) {
            producer.start();
        }
        for (FastConsumer consumer : consumers) {
            consumer.start();
        }
        
        // Monitor progress
        Thread monitor = new Thread(() -> {
            try {
                while (totalConsumed.get() < itemsPerThread * producers.length) {
                    Thread.sleep(1000);
                    StatisticalBoundedBuffer.Statistics stats = buffer.getStatistics();
                    System.out.printf("Progress: %s%n", stats);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Monitor");
        monitor.start();
        
        // Wait for completion
        try {
            for (FastProducer producer : producers) {
                producer.join();
            }
            for (FastConsumer consumer : consumers) {
                consumer.join();
            }
            monitor.interrupt();
            monitor.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long endTime = System.currentTimeMillis();
        
        StatisticalBoundedBuffer.Statistics finalStats = buffer.getStatistics();
        System.out.printf("Final statistics: %s%n", finalStats);
        System.out.printf("Total execution time: %d ms%n", endTime - startTime);
        System.out.printf("Throughput: %.2f items/second%n%n", 
                        finalStats.totalConsumed * 1000.0 / (endTime - startTime));
    }
    
    /**
     * Compare with modern concurrent utilities
     */
    public static void compareWithModernApproach() {
        System.out.println("=== Comparison with Modern Concurrent Utilities ===");
        
        int bufferSize = 10;
        int itemsPerThread = 10000;
        
        // Test with wait/notify approach
        System.out.println("Testing wait/notify approach:");
        StatisticalBoundedBuffer<Integer> waitNotifyBuffer = new StatisticalBoundedBuffer<>(bufferSize);
        AtomicLong waitNotifyProduced = new AtomicLong(0);
        AtomicLong waitNotifyConsumed = new AtomicLong(0);
        
        long startTime = System.currentTimeMillis();
        
        FastProducer waitNotifyProducer = new FastProducer(waitNotifyBuffer, itemsPerThread, 
                                                          waitNotifyProduced, "WaitNotifyProducer");
        FastConsumer waitNotifyConsumer = new FastConsumer(waitNotifyBuffer, itemsPerThread, 
                                                          waitNotifyConsumed, "WaitNotifyConsumer");
        
        waitNotifyProducer.start();
        waitNotifyConsumer.start();
        
        try {
            waitNotifyProducer.join();
            waitNotifyConsumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long waitNotifyTime = System.currentTimeMillis() - startTime;
        
        // Test with BlockingQueue approach
        System.out.println("Testing BlockingQueue approach:");
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(bufferSize);
        AtomicLong blockingQueueProduced = new AtomicLong(0);
        AtomicLong blockingQueueConsumed = new AtomicLong(0);
        
        startTime = System.currentTimeMillis();
        
        Thread blockingQueueProducer = new Thread(() -> {
            try {
                for (int i = 0; i < itemsPerThread; i++) {
                    blockingQueue.put(i);
                    blockingQueueProduced.incrementAndGet();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "BlockingQueueProducer");
        
        Thread blockingQueueConsumer = new Thread(() -> {
            try {
                for (int i = 0; i < itemsPerThread; i++) {
                    Integer item = blockingQueue.take();
                    blockingQueueConsumed.incrementAndGet();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "BlockingQueueConsumer");
        
        blockingQueueProducer.start();
        blockingQueueConsumer.start();
        
        try {
            blockingQueueProducer.join();
            blockingQueueConsumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long blockingQueueTime = System.currentTimeMillis() - startTime;
        
        System.out.printf("Wait/Notify approach: %d ms%n", waitNotifyTime);
        System.out.printf("BlockingQueue approach: %d ms%n", blockingQueueTime);
        System.out.printf("Performance ratio: %.2fx%n%n", 
                        (double) waitNotifyTime / blockingQueueTime);
    }
    
    /**
     * Demonstrate deadlock avoidance
     */
    public static void deadlockAvoidanceDemo() {
        System.out.println("=== Deadlock Avoidance Demo ===");
        
        // This demonstrates why notifyAll() is preferred over notify()
        BoundedBuffer<String> buffer = new BoundedBuffer<>(2);
        
        // Multiple producers that might cause deadlock with notify()
        Thread[] producers = new Thread[3];
        for (int i = 0; i < producers.length; i++) {
            final int producerId = i;
            producers[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < 3; j++) {
                        buffer.put("Item-" + producerId + "-" + j);
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Producer-" + (i + 1));
        }
        
        // Single slow consumer
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 9; i++) {
                    String item = buffer.take();
                    System.out.printf("Consumed: %s%n", item);
                    Thread.sleep(300); // Slow consumption
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "SlowConsumer");
        
        // Start all threads
        for (Thread producer : producers) {
            producer.start();
        }
        consumer.start();
        
        try {
            for (Thread producer : producers) {
                producer.join();
            }
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Deadlock avoidance demo completed successfully");
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("=== Question 29: Producer-Consumer with wait() and notify() ===\n");
        
        basicProducerConsumerDemo();
        multipleProducersConsumersDemo();
        priorityProducerConsumerDemo();
        performanceDemo();
        compareWithModernApproach();
        deadlockAvoidanceDemo();
        
        System.out.println("=== Key Concepts Demonstrated ===");
        System.out.println("• Classic producer-consumer problem with bounded buffer");
        System.out.println("• wait() and notify()/notifyAll() for thread coordination");
        System.out.println("• Synchronized blocks for thread safety");
        System.out.println("• Multiple producers and consumers coordination");
        System.out.println("• Priority-based processing with PriorityQueue");
        System.out.println("• Performance monitoring and statistics collection");
        System.out.println("• Comparison with modern BlockingQueue utilities");
        System.out.println("• Deadlock avoidance using notifyAll()");
        System.out.println("• Proper interrupt handling in threaded code");
    }
}
