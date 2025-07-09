/**
 * Program to demonstrate default methods in interfaces (Java 8 feature)
 * 
 * Default methods allow interfaces to have methods with implementation
 * without affecting the classes that implement the interface.
 * 
 * Benefits:
 * 1. Backward compatibility - Add new methods to existing interfaces without breaking implementations
 * 2. Multiple inheritance of behavior - Classes can inherit behavior from multiple interfaces
 * 3. Optional methods - Implementing classes can choose to override or use the default implementation
 */

public class p26_DefaultMethodInterface {
    public static void main(String[] args) {
        System.out.println("=== Default Methods in Interfaces ===\n");
        
        // Create different notification service implementations
        NotificationService emailService = new EmailService("user@example.com");
        NotificationService smsService = new SMSService("+91-9876543210");
        NotificationService pushService = new PushNotificationService("User123App");
        
        System.out.println("1. Email Notification Service:");
        emailService.sendNotification("Welcome to our application!");
        emailService.sendUrgentNotification("Account security alert!");
        emailService.scheduleNotification("Weekly newsletter", 7);
        System.out.println();
        
        System.out.println("2. SMS Notification Service:");
        smsService.sendNotification("Your OTP is 456789");
        smsService.sendUrgentNotification("Unusual login attempt detected");
        // Using default implementation for scheduleNotification
        smsService.scheduleNotification("Bill payment reminder", 3);
        System.out.println();
        
        System.out.println("3. Push Notification Service:");
        pushService.sendNotification("New message received");
        // Override default implementation
        pushService.sendUrgentNotification("Update available");
        pushService.scheduleNotification("Daily reminder", 1);
        System.out.println();
        
        // Demonstrating multiple interface inheritance with default methods
        System.out.println("4. Enhanced Notification Service (multiple inheritance):");
        EnhancedNotificationService enhancedService = new EnhancedEmailService("manager@company.com");
        enhancedService.sendNotification("Project status update");
        enhancedService.sendAnalytics(); // from AnalyticsProvider interface
        enhancedService.sendUrgentNotification("Critical system error!");
        enhancedService.logActivity("Notification sent"); // from Loggable interface
        
        System.out.println("\n=== End of Default Methods Demonstration ===");
    }
}

// Interface with default methods
interface NotificationService {
    // Abstract method (must be implemented)
    void sendNotification(String message);
    
    // Default method with implementation
    default void sendUrgentNotification(String message) {
        System.out.println("URGENT NOTIFICATION: " + message);
    }
    
    // Another default method
    default void scheduleNotification(String message, int days) {
        System.out.println("Scheduling notification \"" + message + "\" to be sent after " + days + " days");
    }
}

// Another interface with default methods
interface Loggable {
    // Default method
    default void logActivity(String activity) {
        System.out.println("LOG: " + activity + " at " + java.time.LocalDateTime.now());
    }
}

// Another interface with default methods
interface AnalyticsProvider {
    // Default method
    default void sendAnalytics() {
        System.out.println("Default analytics data sent");
    }
}

// Combined interface inheriting from multiple interfaces
interface EnhancedNotificationService extends NotificationService, Loggable, AnalyticsProvider {
    // Inherits all default methods from parent interfaces
}

// Basic implementation using default methods as-is
class EmailService implements NotificationService {
    private String emailAddress;
    
    public EmailService(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    @Override
    public void sendNotification(String message) {
        System.out.println("Sending EMAIL to " + emailAddress + ": " + message);
    }
}

// Implementation that overrides some default methods
class SMSService implements NotificationService {
    private String phoneNumber;
    
    public SMSService(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public void sendNotification(String message) {
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }
    
    // Override default method to provide specialized implementation
    @Override
    public void sendUrgentNotification(String message) {
        System.out.println("URGENT SMS ALERT to " + phoneNumber + ": " + message);
    }
}

// Another implementation
class PushNotificationService implements NotificationService {
    private String appId;
    
    public PushNotificationService(String appId) {
        this.appId = appId;
    }
    
    @Override
    public void sendNotification(String message) {
        System.out.println("Sending PUSH notification to app " + appId + ": " + message);
    }
    
    @Override
    public void sendUrgentNotification(String message) {
        System.out.println("URGENT PUSH ALERT to app " + appId + ": " + message + " [vibrate=true, sound=alarm]");
    }
    
    @Override
    public void scheduleNotification(String message, int days) {
        System.out.println("Scheduling PUSH notification to app " + appId + " for message: \"" + 
                          message + "\" after " + days + " days with user preferences");
    }
}

// Implementation of multiple inheritance with default methods
class EnhancedEmailService implements EnhancedNotificationService {
    private String emailAddress;
    
    public EnhancedEmailService(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    @Override
    public void sendNotification(String message) {
        System.out.println("Enhanced email service: Sending to " + emailAddress + ": " + message);
    }
    
    @Override
    public void sendAnalytics() {
        System.out.println("Sending custom email analytics for: " + emailAddress);
    }
}
