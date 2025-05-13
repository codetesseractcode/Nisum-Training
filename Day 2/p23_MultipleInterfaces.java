/*
 23. Write a simple Program to create multiple interfaces and their implementation.
     This program demonstrates how to define and implement multiple interfaces,
     including default and static methods in interfaces introduced in Java 8.
*/

public class p23_MultipleInterfaces 
{
    public static void main(String[] args) 
    {
        System.out.println("=== Multiple Interfaces Demonstration ===\n");
        
        // Creating a SmartPhone object that implements multiple interfaces
        SmartPhone phone = new SmartPhone("Samsung Galaxy", "Android", 6.2);
        
        // Using methods from different interfaces
        System.out.println("1. Device Information:");
        phone.displayInfo();
        
        System.out.println("\n2. Camera Functionality:");
        phone.takePhoto();
        phone.recordVideo();
        
        System.out.println("\n3. Communication Functionality:");
        phone.makeCall("123-456-7890");
        phone.sendMessage("123-456-7890", "Hello there!");
        
        System.out.println("\n4. Internet Functionality:");
        phone.connect();
        phone.browse("www.example.com");
        phone.downloadFile("document.pdf");
        phone.disconnect();
        
        // Using default methods from interfaces
        System.out.println("\n5. Using Default Methods from Interfaces:");
        phone.showCameraInfo();
        phone.displayConnectionStatus();
        
        // Using static methods from interfaces
        System.out.println("\n6. Using Static Methods from Interfaces:");
        System.out.println("Camera version: " + Camera.getVersion());
        System.out.println("Internet standard: " + Internet.getStandard());
        
        // Creating a Tablet that implements some of the same interfaces
        System.out.println("\n7. Creating a Tablet (implements Device and Internet):");
        Tablet tablet = new Tablet("iPad", "iOS", 10.2);
        tablet.displayInfo();
        tablet.connect();
        tablet.browse("www.example.com");
        tablet.disconnect();
        
        System.out.println("\n=== End of Multiple Interfaces Demonstration ===");
    }
}

// Device Interface
interface Device 
{
    void displayInfo();
    
    // Default method in interface (Java 8+)
    default void turnOn() 
    {
        System.out.println("Device is turning on");
    }
    
    default void turnOff() 
    {
        System.out.println("Device is turning off");
    }
}

// Camera Interface
interface Camera 
{
    void takePhoto();
    void recordVideo();
    
    // Default method in interface (Java 8+)
    default void showCameraInfo() 
    {
        System.out.println("Camera: Standard digital camera interface");
    }
    
    // Static method in interface (Java 8+)
    static String getVersion() 
    {
        return "Camera Interface v2.0";
    }
}

// Communication Interface
interface Communication 
{
    void makeCall(String number);
    void sendMessage(String number, String message);
    
    // Default method
    default void showCallHistory() 
    {
        System.out.println("Displaying call history");
    }
}

// Internet Interface
interface Internet 
{
    void connect();
    void browse(String url);
    void downloadFile(String filename);
    void disconnect();
    
    // Default method
    default void displayConnectionStatus() 
    {
        System.out.println("Checking internet connection status...");
        System.out.println("Connection is active");
    }
    
    // Static method
    static String getStandard() 
    {
        return "IEEE 802.11";
    }
}

// SmartPhone class implementing multiple interfaces
class SmartPhone implements Device, Camera, Communication, Internet 
{
    private String model;
    private String operatingSystem;
    private double screenSize;
    private boolean isConnected;
    
    public SmartPhone(String model, String operatingSystem, double screenSize) 
    {
        this.model = model;
        this.operatingSystem = operatingSystem;
        this.screenSize = screenSize;
        this.isConnected = false;
    }
    
    // Implementing Device interface methods
    @Override
    public void displayInfo() 
    {
        System.out.println("SmartPhone Details:");
        System.out.println("- Model: " + model);
        System.out.println("- OS: " + operatingSystem);
        System.out.println("- Screen Size: " + screenSize + " inches");
    }
    
    // Implementing Camera interface methods
    @Override
    public void takePhoto() 
    {
        System.out.println("SmartPhone taking a photo");
    }
    
    @Override
    public void recordVideo() 
    {
        System.out.println("SmartPhone recording a video");
    }
    
    // Implementing Communication interface methods
    @Override
    public void makeCall(String number) 
    {
        System.out.println("SmartPhone calling " + number);
    }
    
    @Override
    public void sendMessage(String number, String message) 
    {
        System.out.println("SmartPhone sending message to " + number + ": " + message);
    }
    
    // Implementing Internet interface methods
    @Override
    public void connect() 
    {
        isConnected = true;
        System.out.println("SmartPhone connected to the internet");
    }
    
    @Override
    public void browse(String url) 
    {
        if (isConnected) 
        {
            System.out.println("SmartPhone browsing " + url);
        } 
        else 
        {
            System.out.println("Cannot browse. Connect to internet first.");
        }
    }
    
    @Override
    public void downloadFile(String filename) 
    {
        if (isConnected) 
        {
            System.out.println("SmartPhone downloading " + filename);
        } 
        else 
        {
            System.out.println("Cannot download. Connect to internet first.");
        }
    }
    
    @Override
    public void disconnect() 
    {
        isConnected = false;
        System.out.println("SmartPhone disconnected from the internet");
    }
}

// Another class implementing some of the same interfaces
class Tablet implements Device, Internet 
{
    private String model;
    private String operatingSystem;
    private double screenSize;
    private boolean isConnected;
    
    public Tablet(String model, String operatingSystem, double screenSize) 
    {
        this.model = model;
        this.operatingSystem = operatingSystem;
        this.screenSize = screenSize;
        this.isConnected = false;
    }
    
    // Implementing Device interface
    @Override
    public void displayInfo() 
    {
        System.out.println("Tablet Details:");
        System.out.println("- Model: " + model);
        System.out.println("- OS: " + operatingSystem);
        System.out.println("- Screen Size: " + screenSize + " inches");
    }
    
    // Implementing Internet interface
    @Override
    public void connect() 
    {
        isConnected = true;
        System.out.println("Tablet connected to the internet");
    }
    
    @Override
    public void browse(String url) 
    {
        if (isConnected) 
        {
            System.out.println("Tablet browsing " + url);
        } 
        else 
        {
            System.out.println("Cannot browse. Connect to internet first.");
        }
    }
    
    @Override
    public void downloadFile(String filename) 
    {
        if (isConnected) 
        {
            System.out.println("Tablet downloading " + filename);
        } 
        else 
        {
            System.out.println("Cannot download. Connect to internet first.");
        }
    }
    
    @Override
    public void disconnect() 
    {
        isConnected = false;
        System.out.println("Tablet disconnected from the internet");
    }
}
