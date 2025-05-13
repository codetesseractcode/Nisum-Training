/*
 14. Write a Java Program to display some basic information of car and bus 
     using inheritance concept
*/

public class p14_VehicleInheritance 
{
    public static void main(String[] args) 
    {
        System.out.println("=== Vehicle Information System ===\n");
        
        // Create a Car object
        Car myCar = new Car("Toyota", "Camry", 2023, "Blue", 5, 4, true);
        
        // Create a Bus object
        Bus cityBus = new Bus("Volvo", "City Express", 2022, "Red", 45, 2, true);
        
        // Display information about the vehicles
        System.out.println("Car Details:");
        myCar.displayInfo();
        
        System.out.println("\nBus Details:");
        cityBus.displayInfo();
        
        // Demonstrate some specific methods
        System.out.println("\nDemonstrating specific vehicle behaviors:");
        myCar.drive();
        myCar.honk();
        myCar.park();
        
        cityBus.drive();
        cityBus.openDoors();
        cityBus.announceStop("Downtown");
    }
}

// Base Vehicle class (parent class)
class Vehicle 
{
    // Common properties for all vehicles
    protected String make;
    protected String model;
    protected int year;
    protected String color;
    protected int seatingCapacity;
    protected int numOfDoors;
    
    // Constructor
    public Vehicle(String make, String model, int year, String color, 
                  int seatingCapacity, int numOfDoors) 
    {
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.seatingCapacity = seatingCapacity;
        this.numOfDoors = numOfDoors;
    }
    
    // Common method for all vehicles
    public void drive() 
    {
        System.out.println("The " + make + " " + model + " is moving.");
    }
    
    // Method to display basic information
    public void displayInfo() 
    {
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Color: " + color);
        System.out.println("Seating Capacity: " + seatingCapacity + " passengers");
        System.out.println("Number of Doors: " + numOfDoors);
    }
    
    // Common method for all vehicles
    public void honk() 
    {
        System.out.println("The vehicle honks!");
    }
}

// Car class (child class)
class Car extends Vehicle 
{
    // Additional property specific to cars
    private boolean hasSunroof;
    
    // Constructor
    public Car(String make, String model, int year, String color, 
              int seatingCapacity, int numOfDoors, boolean hasSunroof) 
    {
        // Call parent constructor
        super(make, model, year, color, seatingCapacity, numOfDoors);
        this.hasSunroof = hasSunroof;
    }
    
    // Override the displayInfo method to add car-specific information
    @Override
    public void displayInfo() 
    {
        // Call the parent method first
        super.displayInfo();
        
        // Add car-specific information
        System.out.println("Type: Car");
        System.out.println("Has Sunroof: " + (hasSunroof ? "Yes" : "No"));
    }
    
    // Override the honk method for cars
    @Override
    public void honk() 
    {
        System.out.println("The car honks: Beep Beep!");
    }
    
    // Car-specific method
    public void park() 
    {
        System.out.println("The " + color + " " + make + " " + model + 
                          " is parking in the garage.");
    }
}

// Bus class (child class)
class Bus extends Vehicle 
{
    // Additional property specific to buses
    private boolean isAccessible;
    
    // Constructor
    public Bus(String make, String model, int year, String color, 
              int seatingCapacity, int numOfDoors, boolean isAccessible) 
    {
        // Call parent constructor
        super(make, model, year, color, seatingCapacity, numOfDoors);
        this.isAccessible = isAccessible;
    }
    
    // Override the displayInfo method to add bus-specific information
    @Override
    public void displayInfo() 
    {
        // Call the parent method first
        super.displayInfo();
        
        // Add bus-specific information
        System.out.println("Type: Bus");
        System.out.println("Accessibility Features: " + (isAccessible ? "Yes" : "No"));
    }
    
    // Override the honk method for buses
    @Override
    public void honk() 
    {
        System.out.println("The bus honks: HOOOONK!");
    }
    
    // Bus-specific methods
    public void openDoors() 
    {
        System.out.println("The bus doors are opening.");
    }
    
    public void announceStop(String stopName) 
    {
        System.out.println("Next stop: " + stopName);
    }
}
