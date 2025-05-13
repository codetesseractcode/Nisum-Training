/*
 17. Simple Java Program that demonstrates all basic core Java concepts:
     - Inheritance (multi-level hierarchy)
     - Static blocks, variables, and methods
     - All access modifiers (public, protected, default, private)
     - Encapsulation
     - Method overriding and overloading
     - Constructors and constructor chaining
*/

public class p17_CoreJavaConcepts 
{
    // Static variable example
    private static int programCounter = 0;
    
    // Static block - executes when class is loaded
    static 
    {
        System.out.println("=== Core Java Concepts Demo Program ===");
        System.out.println("Static block in main class is executing...");
        programCounter = 100;
    }
    
    public static void main(String[] args) 
    {
        System.out.println("\n--- Program Start ---");
        System.out.println("Program counter value: " + programCounter);
        
        // Create instances of different vehicles to demonstrate inheritance
        System.out.println("\n--- Inheritance Demonstration ---");
        
        // Base class instance
        System.out.println("Creating a Transport object (base class):");
        Transport transport = new Transport("General Transport", 1000);
        transport.displayInfo();
        transport.move();
        
        // Derived class instance
        System.out.println("\nCreating a LandVehicle object (derived from Transport):");
        LandVehicle landVehicle = new LandVehicle("Land Transport", 2000, 4);
        landVehicle.displayInfo();
        landVehicle.move();
        
        // Further derived class instance
        System.out.println("\nCreating a Car object (derived from LandVehicle):");
        Car car = new Car("Toyota Camry", 3000, 4, "Sedan");
        car.displayInfo();
        car.move();
        car.honkHorn();
        
        // Demonstrate encapsulation and access modifiers
        System.out.println("\n--- Access Modifiers and Encapsulation ---");
        AccessModifiersDemo demo = new AccessModifiersDemo();
        demo.demonstrateAccessModifiers();
        
        // Demonstrate method overloading
        System.out.println("\n--- Method Overloading ---");
        Calculator calc = new Calculator();
        System.out.println("Calculator.add(5, 10) = " + calc.add(5, 10));
        System.out.println("Calculator.add(5.5, 10.3) = " + calc.add(5.5, 10.3));
        System.out.println("Calculator.add(2, 3, 4) = " + calc.add(2, 3, 4));
        
        // Static method access
        System.out.println("\n--- Static Method Access ---");
        System.out.println("Direct static method call: " + StaticUtility.getUtilityInfo());
        StaticUtility.showDateTime();
        
        System.out.println("\n--- Program End ---");
    }
}

// Base class for inheritance demonstration
class Transport 
{
    // Protected variables - accessible in subclasses
    protected String name;
    protected int weight;
    
    // Private variable - not accessible in subclasses
    private int id;
    
    // Static variable
    public static int count = 0;
    
    // Static block
    static 
    {
        System.out.println("Static block in Transport class is executing...");
        count = 1;
    }
    
    // Constructor
    public Transport(String name, int weight) 
    {
        this.name = name;
        this.weight = weight;
        this.id = ++count;
        System.out.println("Transport constructor called");
    }
    
    // Public method
    public void displayInfo() 
    {
        System.out.println("Transport [ID: " + id + ", Name: " + name + 
                          ", Weight: " + weight + "kg]");
    }
    
    // Method that can be overridden
    public void move() 
    {
        System.out.println("Transport is moving");
    }
    
    // Private method - not accessible in subclasses
    private void serviceCheck() 
    {
        System.out.println("Transport service check");
    }
    
    // Protected method - accessible in subclasses
    protected void loadCargo(int weight) 
    {
        System.out.println("Loading " + weight + "kg of cargo");
    }
    
    // Default access method - accessible in same package
    void refuel() 
    {
        System.out.println("Refueling transport");
    }
}

// Derived class inheriting from Transport
class LandVehicle extends Transport 
{
    // Instance variable
    protected int wheels;
    
    // Static variable
    public static int landVehicleCount = 0;
    
    // Constructor
    public LandVehicle(String name, int weight, int wheels) 
    {
        // Call parent constructor
        super(name, weight);
        this.wheels = wheels;
        landVehicleCount++;
        System.out.println("LandVehicle constructor called");
    }
    
    // Override parent method
    @Override
    public void displayInfo() 
    {
        super.displayInfo();  // Call parent method
        System.out.println("LandVehicle [Wheels: " + wheels + "]");
    }
    
    // Override parent method
    @Override
    public void move() 
    {
        System.out.println("LandVehicle is moving on " + wheels + " wheels");
    }
    
    // New method not in parent
    public void checkTires() 
    {
        System.out.println("Checking " + wheels + " tires");
    }
}

// Further derived class
class Car extends LandVehicle 
{
    // Instance variable
    private String type;
    
    // Static variable
    public static int carCount = 0;
    
    // Static block
    static 
    {
        System.out.println("Static block in Car class is executing...");
        carCount = 0;
    }
    
    // Constructor
    public Car(String name, int weight, int wheels, String type) 
    {
        super(name, weight, wheels);
        this.type = type;
        carCount++;
        System.out.println("Car constructor called");
    }
    
    // Override method
    @Override
    public void displayInfo() 
    {
        super.displayInfo();  // Call parent method
        System.out.println("Car [Type: " + type + "]");
    }
    
    // New method specific to Car
    public void honkHorn() 
    {
        System.out.println("Car horn: Beep! Beep!");
    }
}

// Class to demonstrate different access modifiers
class AccessModifiersDemo 
{
    // Different access modifiers
    public String publicVar = "Public variable - accessible everywhere";
    protected String protectedVar = "Protected variable - accessible in package and subclasses";
    String defaultVar = "Default variable - accessible in package";
    private String privateVar = "Private variable - accessible only in this class";
    
    public void demonstrateAccessModifiers() 
    {
        System.out.println("Accessing variables within the class:");
        System.out.println(publicVar);     // Accessible
        System.out.println(protectedVar);  // Accessible
        System.out.println(defaultVar);    // Accessible
        System.out.println(privateVar);    // Accessible
        
        System.out.println("\nDemonstrating encapsulation with getter/setter:");
        EncapsulatedData data = new EncapsulatedData();
        data.setId(101);
        data.setName("Secure Data");
        System.out.println("ID: " + data.getId() + ", Name: " + data.getName());
    }
}

// Class to demonstrate encapsulation
class EncapsulatedData 
{
    private int id;
    private String name;
    
    // Getter methods
    public int getId() 
    {
        return id;
    }
    
    public String getName() 
    {
        return name;
    }
    
    // Setter methods
    public void setId(int id) 
    {
        if (id > 0) 
        {
            this.id = id;
        } 
        else 
        {
            System.out.println("Invalid ID. Must be positive.");
        }
    }
    
    public void setName(String name) 
    {
        if (name != null && !name.isEmpty()) 
        {
            this.name = name;
        } 
        else 
        {
            System.out.println("Invalid name. Cannot be empty.");
        }
    }
}

// Class to demonstrate method overloading
class Calculator 
{
    // Overloaded methods - same name but different parameters
    public int add(int a, int b) 
    {
        return a + b;
    }
    
    public double add(double a, double b) 
    {
        return a + b;
    }
    
    public int add(int a, int b, int c) 
    {
        return a + b + c;
    }
}

// Class to demonstrate static methods and variables
class StaticUtility 
{
    // Private static variable
    private static String utilityName = "Core Java Utility";
    
    // Static block
    static 
    {
        System.out.println("Static block in StaticUtility class is executing...");
        utilityName += " - Initialized";
    }
    
    // Static method
    public static String getUtilityInfo() 
    {
        return utilityName;
    }
    
    // Another static method
    public static void showDateTime() 
    {
        java.util.Date now = new java.util.Date();
        System.out.println("Current date and time: " + now);
    }
}
