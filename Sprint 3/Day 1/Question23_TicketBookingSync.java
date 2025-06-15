/**
 * Question 23: Thread Synchronization for Ticket Booking System
 */

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

/**
 * Unsynchronized ticket booking system (demonstrates race conditions)
 */
class UnsafeTicketBookingSystem {
    private int availableTickets;
    private int totalBookings = 0;
    private List<String> bookingHistory = new ArrayList<>();
    
    public UnsafeTicketBookingSystem(int initialTickets) {
        this.availableTickets = initialTickets;
    }
    
    public boolean bookTicket(String customerName, int quantity) {
        // Check availability (race condition possible here)
        if (availableTickets >= quantity) {
            
            // Simulate processing time (increases chance of race condition)
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
            
            // Update state (race condition possible here)
            availableTickets -= quantity;
            totalBookings++;
            bookingHistory.add(String.format("%s booked %d ticket(s)", customerName, quantity));
            
            return true;
        }
        return false;
    }
    
    public int getAvailableTickets() { return availableTickets; }
    public int getTotalBookings() { return totalBookings; }
    public List<String> getBookingHistory() { return new ArrayList<>(bookingHistory); }
}

/**
 * Thread-safe ticket booking system using synchronized methods
 */
class SafeTicketBookingSystem {
    private int availableTickets;
    private int totalBookings = 0;
    private final List<String> bookingHistory = Collections.synchronizedList(new ArrayList<>());
    
    public SafeTicketBookingSystem(int initialTickets) {
        this.availableTickets = initialTickets;
    }
    
    public synchronized boolean bookTicket(String customerName, int quantity) {
        // Atomic check and update
        if (availableTickets >= quantity) {
            
            // Simulate processing time
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
            
            availableTickets -= quantity;
            totalBookings++;
            bookingHistory.add(String.format("%s booked %d ticket(s) at %d", 
                                            customerName, quantity, System.currentTimeMillis()));
            
            System.out.printf("✓ %s successfully booked %d ticket(s). Remaining: %d%n", 
                            customerName, quantity, availableTickets);
            return true;
        } else {
            System.out.printf("❌ %s failed to book %d ticket(s). Only %d available%n", 
                            customerName, quantity, availableTickets);
            return false;
        }
    }
    
    public synchronized int getAvailableTickets() { return availableTickets; }
    public synchronized int getTotalBookings() { return totalBookings; }
    public List<String> getBookingHistory() { return new ArrayList<>(bookingHistory); }
    
    public synchronized void displayStatus() {
        System.out.printf("Status: %d tickets available, %d total bookings%n", 
                        availableTickets, totalBookings);
    }
}

/**
 * Advanced ticket booking system using ReentrantLock for fine-grained control
 */
class AdvancedTicketBookingSystem {
    private int availableTickets;
    private int totalBookings = 0;
    private final List<String> bookingHistory = new ArrayList<>();
    private final ReentrantLock bookingLock = new ReentrantLock();
    private final AtomicInteger activeBookings = new AtomicInteger(0);
    
    public AdvancedTicketBookingSystem(int initialTickets) {
        this.availableTickets = initialTickets;
    }
    
    public boolean bookTicket(String customerName, int quantity) {
        activeBookings.incrementAndGet();
        
        bookingLock.lock();
        try {
            if (availableTickets >= quantity) {
                System.out.printf("Processing booking for %s (quantity: %d)...%n", customerName, quantity);
                
                // Simulate processing time
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
                
                availableTickets -= quantity;
                totalBookings++;
                bookingHistory.add(String.format("%s booked %d ticket(s) at %d", 
                                                customerName, quantity, System.currentTimeMillis()));
                
                System.out.printf("✓ %s booking confirmed! Tickets: %d, Remaining: %d%n", 
                                customerName, quantity, availableTickets);
                return true;
            } else {
                System.out.printf("❌ %s booking failed! Requested: %d, Available: %d%n", 
                                customerName, quantity, availableTickets);
                return false;
            }
        } finally {
            bookingLock.unlock();
            activeBookings.decrementAndGet();
        }
    }
    
    public boolean tryBookTicket(String customerName, int quantity, long timeoutMs) {
        activeBookings.incrementAndGet();
        
        try {
            if (bookingLock.tryLock(timeoutMs, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                try {
                    return bookTicket(customerName, quantity);
                } finally {
                    bookingLock.unlock();
                }
            } else {
                System.out.printf("⏰ %s booking timed out after %dms%n", customerName, timeoutMs);
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            activeBookings.decrementAndGet();
        }
    }
    
    public int getAvailableTickets() {
        bookingLock.lock();
        try {
            return availableTickets;
        } finally {
            bookingLock.unlock();
        }
    }
    
    public int getTotalBookings() {
        bookingLock.lock();
        try {
            return totalBookings;
        } finally {
            bookingLock.unlock();
        }
    }
    
    public List<String> getBookingHistory() {
        bookingLock.lock();
        try {
            return new ArrayList<>(bookingHistory);
        } finally {
            bookingLock.unlock();
        }
    }
    
    public int getActiveBookings() {
        return activeBookings.get();
    }
}

/**
 * High-performance ticket booking system using read-write locks
 */
class HighPerformanceTicketBookingSystem {
    private int availableTickets;
    private int totalBookings = 0;
    private final Map<String, Integer> customerBookings = new ConcurrentHashMap<>();
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
    
    public HighPerformanceTicketBookingSystem(int initialTickets) {
        this.availableTickets = initialTickets;
    }
    
    public boolean bookTicket(String customerName, int quantity) {
        writeLock.lock();
        try {
            if (availableTickets >= quantity) {
                availableTickets -= quantity;
                totalBookings++;
                customerBookings.merge(customerName, quantity, Integer::sum);
                
                System.out.printf("✓ %s booked %d ticket(s) (total: %d). Remaining: %d%n", 
                                customerName, quantity, 
                                customerBookings.get(customerName), availableTickets);
                return true;
            } else {
                System.out.printf("❌ %s failed to book %d ticket(s). Available: %d%n", 
                                customerName, quantity, availableTickets);
                return false;
            }
        } finally {
            writeLock.unlock();
        }
    }
    
    public int getAvailableTickets() {
        readLock.lock();
        try {
            return availableTickets;
        } finally {
            readLock.unlock();
        }
    }
    
    public int getTotalBookings() {
        readLock.lock();
        try {
            return totalBookings;
        } finally {
            readLock.unlock();
        }
    }
    
    public Map<String, Integer> getCustomerBookings() {
        return new ConcurrentHashMap<>(customerBookings);
    }
}

/**
 * Customer thread that attempts to book tickets
 */
class Customer extends Thread {
    private final Object bookingSystem;
    private final String customerName;
    private final int[] ticketQuantities;
    private final Random random = new Random();
    private int successfulBookings = 0;
    private int failedBookings = 0;
    
    public Customer(Object bookingSystem, String customerName, int[] ticketQuantities) {
        super(customerName);
        this.bookingSystem = bookingSystem;
        this.customerName = customerName;
        this.ticketQuantities = ticketQuantities;
    }
    
    @Override
    public void run() {
        System.out.println(customerName + " started booking process");
        
        for (int quantity : ticketQuantities) {
            // Random delay between booking attempts
            try {
                Thread.sleep(random.nextInt(50));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            
            boolean success = false;
            
            if (bookingSystem instanceof UnsafeTicketBookingSystem) {
                success = ((UnsafeTicketBookingSystem) bookingSystem).bookTicket(customerName, quantity);
            } else if (bookingSystem instanceof SafeTicketBookingSystem) {
                success = ((SafeTicketBookingSystem) bookingSystem).bookTicket(customerName, quantity);
            } else if (bookingSystem instanceof AdvancedTicketBookingSystem) {
                success = ((AdvancedTicketBookingSystem) bookingSystem).bookTicket(customerName, quantity);
            } else if (bookingSystem instanceof HighPerformanceTicketBookingSystem) {
                success = ((HighPerformanceTicketBookingSystem) bookingSystem).bookTicket(customerName, quantity);
            }
            
            if (success) {
                successfulBookings++;
            } else {
                failedBookings++;
            }
        }
        
        System.out.printf("%s finished: %d successful, %d failed%n", 
                        customerName, successfulBookings, failedBookings);
    }
    
    public int getSuccessfulBookings() { return successfulBookings; }
    public int getFailedBookings() { return failedBookings; }
}

/**
 * Main class demonstrating thread synchronization in ticket booking
 */
public class Question23_TicketBookingSync {
    
    /**
     * Test unsynchronized booking system
     */
    public static void testUnsafeBookingSystem() {
        System.out.println("=== Testing Unsafe Ticket Booking System ===");
        
        UnsafeTicketBookingSystem system = new UnsafeTicketBookingSystem(50);
        
        Customer[] customers = {
            new Customer(system, "Alice", new int[]{3, 2, 1}),
            new Customer(system, "Bob", new int[]{2, 4, 1}),
            new Customer(system, "Charlie", new int[]{1, 3, 2}),
            new Customer(system, "Diana", new int[]{4, 1, 2}),
            new Customer(system, "Eve", new int[]{2, 2, 3})
        };
        
        // Start all customer threads
        for (Customer customer : customers) {
            customer.start();
        }
        
        // Wait for all to complete
        for (Customer customer : customers) {
            try {
                customer.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        
        // Check results
        int totalBooked = 50 - system.getAvailableTickets();
        System.out.printf("Final state: %d tickets remaining, %d total bookings, %d tickets sold%n", 
                        system.getAvailableTickets(), system.getTotalBookings(), totalBooked);
        
        if (system.getAvailableTickets() < 0) {
            System.out.println("❌ CRITICAL ERROR: Negative tickets (overbooking detected!)");
        }
        System.out.println();
    }
    
    /**
     * Test synchronized booking system
     */
    public static void testSafeBookingSystem() {
        System.out.println("=== Testing Safe Ticket Booking System ===");
        
        SafeTicketBookingSystem system = new SafeTicketBookingSystem(25);
        
        Customer[] customers = {
            new Customer(system, "Alice", new int[]{3, 2}),
            new Customer(system, "Bob", new int[]{2, 4}),
            new Customer(system, "Charlie", new int[]{1, 3}),
            new Customer(system, "Diana", new int[]{4, 1}),
            new Customer(system, "Eve", new int[]{2, 2})
        };
        
        // Start all customer threads
        for (Customer customer : customers) {
            customer.start();
        }
        
        // Wait for all to complete
        for (Customer customer : customers) {
            try {
                customer.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        
        system.displayStatus();
        
        if (system.getAvailableTickets() >= 0) {
            System.out.println("✓ No overbooking detected - synchronization successful!");
        } else {
            System.out.println("❌ Synchronization failed!");
        }
        System.out.println();
    }
    
    /**
     * Test advanced booking system with ReentrantLock
     */
    public static void testAdvancedBookingSystem() {
        System.out.println("=== Testing Advanced Ticket Booking System ===");
        
        AdvancedTicketBookingSystem system = new AdvancedTicketBookingSystem(30);
        
        Customer[] customers = {
            new Customer(system, "VIP-Alice", new int[]{5, 3}),
            new Customer(system, "VIP-Bob", new int[]{4, 2}),
            new Customer(system, "Regular-Charlie", new int[]{2, 1}),
            new Customer(system, "Regular-Diana", new int[]{1, 3}),
            new Customer(system, "Regular-Eve", new int[]{3, 1})
        };
        
        // Monitor active bookings
        Thread monitor = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(100);
                    if (system.getActiveBookings() > 0) {
                        System.out.printf("Monitor: %d active bookings, %d tickets available%n", 
                                        system.getActiveBookings(), system.getAvailableTickets());
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        
        monitor.start();
        
        // Start all customer threads
        for (Customer customer : customers) {
            customer.start();
        }
        
        // Wait for all to complete
        for (Customer customer : customers) {
            try {
                customer.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        
        monitor.interrupt();
        
        System.out.printf("Final state: %d tickets available, %d total bookings%n", 
                        system.getAvailableTickets(), system.getTotalBookings());
        System.out.println();
    }
    
    /**
     * Test high-performance booking system
     */
    public static void testHighPerformanceBookingSystem() {
        System.out.println("=== Testing High-Performance Booking System ===");
        
        HighPerformanceTicketBookingSystem system = new HighPerformanceTicketBookingSystem(40);
        
        Customer[] customers = new Customer[10];
        for (int i = 0; i < 10; i++) {
            customers[i] = new Customer(system, "Customer-" + (i + 1), 
                                      new int[]{2, 1, 3});
        }
        
        long startTime = System.currentTimeMillis();
        
        // Start all customer threads
        for (Customer customer : customers) {
            customer.start();
        }
        
        // Wait for all to complete
        for (Customer customer : customers) {
            try {
                customer.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.printf("Completed in %d ms%n", endTime - startTime);
        System.out.printf("Final state: %d tickets available, %d total bookings%n", 
                        system.getAvailableTickets(), system.getTotalBookings());
        
        System.out.println("Customer booking summary:");
        system.getCustomerBookings().forEach((customer, tickets) -> 
            System.out.printf("  %s: %d tickets%n", customer, tickets));
        System.out.println();
    }
    
    /**
     * Performance comparison of different approaches
     */
    public static void performanceComparison() {
        System.out.println("=== Performance Comparison ===");
        
        int iterations = 3;
        
        for (int i = 0; i < iterations; i++) {
            System.out.printf("Iteration %d:%n", i + 1);
            
            // Test safe synchronized system
            SafeTicketBookingSystem safeSystem = new SafeTicketBookingSystem(100);
            Customer[] safeCustomers = new Customer[5];
            for (int j = 0; j < 5; j++) {
                safeCustomers[j] = new Customer(safeSystem, "Safe-C" + j, new int[]{4, 3, 2});
            }
            
            long start = System.currentTimeMillis();
            for (Customer customer : safeCustomers) customer.start();
            for (Customer customer : safeCustomers) {
                try { customer.join(); } catch (InterruptedException e) { break; }
            }
            long safeTime = System.currentTimeMillis() - start;
            
            // Test high-performance system
            HighPerformanceTicketBookingSystem perfSystem = new HighPerformanceTicketBookingSystem(100);
            Customer[] perfCustomers = new Customer[5];
            for (int j = 0; j < 5; j++) {
                perfCustomers[j] = new Customer(perfSystem, "Perf-C" + j, new int[]{4, 3, 2});
            }
            
            start = System.currentTimeMillis();
            for (Customer customer : perfCustomers) customer.start();
            for (Customer customer : perfCustomers) {
                try { customer.join(); } catch (InterruptedException e) { break; }
            }
            long perfTime = System.currentTimeMillis() - start;
            
            System.out.printf("  Synchronized: %d ms, High-Performance: %d ms%n", safeTime, perfTime);
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("=== Question 23: Thread Synchronization for Ticket Booking System ===\n");
        
        testUnsafeBookingSystem();
        testSafeBookingSystem();
        testAdvancedBookingSystem();
        testHighPerformanceBookingSystem();
        performanceComparison();
        
        System.out.println("=== Key Concepts Demonstrated ===");
        System.out.println("• Race conditions in unsynchronized systems");
        System.out.println("• synchronized methods for thread safety");
        System.out.println("• ReentrantLock for advanced synchronization");
        System.out.println("• ReadWriteLock for high-performance scenarios");
        System.out.println("• AtomicInteger for lock-free counters");
        System.out.println("• ConcurrentHashMap for thread-safe collections");
        System.out.println("• Real-world application of thread synchronization");
        System.out.println("• Performance comparison of synchronization methods");
    }
}
