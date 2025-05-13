/**
 * Program to demonstrate abstract class features in Java:
 * - Constructors in abstract classes
 * - Data members (fields)
 * - Abstract and concrete methods
 * - Creating objects of abstract class through subclasses
 * - Rules and restrictions of abstract classes
 */

public class p30_AbstractClass {
    public static void main(String[] args) {
        System.out.println("=== Abstract Class Demonstration ===\n");
        
        // We can't instantiate an abstract class directly
        // Vehicle vehicle = new Vehicle(); // This would cause compilation error
        
        // But we can create objects of concrete subclasses
        Car car = new Car("Tata Nexon", "SUV", 2023, 4, "Petrol");
        Motorcycle bike = new Motorcycle("Hero Splendor", "Commuter", 2022, 150);
        Truck truck = new Truck("Tata Prima", "Heavy Duty", 2021, 12000);
        
        // Using the objects of subclasses
        System.out.println("1. Car details:");
        car.displayDetails();
        car.start();
        car.accelerate(60);
        car.brake();
        car.stop();
        car.honk();
        System.out.println();
        
        System.out.println("2. Motorcycle details:");
        bike.displayDetails();
        bike.start();
        bike.accelerate(40);
        bike.performStunt("Wheelie");
        bike.brake();
        bike.stop();
        System.out.println();
        
        System.out.println("3. Truck details:");
        truck.displayDetails();
        truck.start();
        truck.loadCargo(5000);
        truck.accelerate(30);
        truck.brake();
        truck.unloadCargo();
        truck.stop();
        System.out.println();
        
        // Demonstrating polymorphic behavior - reference of abstract class type
        System.out.println("4. Polymorphism with abstract class references:");
        
        // Creating an array of Vehicle references
        Vehicle[] vehicles = new Vehicle[3];
        vehicles[0] = car;
        vehicles[1] = bike;
        vehicles[2] = truck;
        
        // Processing all vehicles polymorphically
        for (Vehicle vehicle : vehicles) {
            System.out.println("\nVehicle: " + vehicle.getModel() + " (" + vehicle.getType() + ")");
            vehicle.start();
            vehicle.accelerate(50);
            vehicle.brake();
            vehicle.stop();
            
            // Type-specific operations using instanceof
            if (vehicle instanceof Car) {
                ((Car) vehicle).honk();
            } else if (vehicle instanceof Motorcycle) {
                ((Motorcycle) vehicle).performStunt("Basic stunt");
            } else if (vehicle instanceof Truck) {
                ((Truck) vehicle).loadCargo(1000);
                ((Truck) vehicle).unloadCargo();
            }
        }
        
        System.out.println("\n=== End of Abstract Class Demonstration ===");
    }
}

// Abstract class with constructors, fields, abstract and concrete methods
abstract class Vehicle {
    // Data members (fields)
    protected String model;
    protected String type;
    protected int year;
    protected int currentSpeed;
    protected boolean engineRunning;
    
    // Static data member
    protected static int totalVehiclesCreated = 0;
    
    // Constructor in abstract class
    public Vehicle(String model, String type, int year) {
        this.model = model;
        this.type = type;
        this.year = year;
        this.currentSpeed = 0;
        this.engineRunning = false;
        
        totalVehiclesCreated++;
        System.out.println("Vehicle object created. Total vehicles: " + totalVehiclesCreated);
    }
    
    // Abstract methods (to be implemented by subclasses)
    public abstract void start();
    public abstract void stop();
    
    // Concrete methods with implementation
    public void accelerate(int speedIncrement) {
        if (engineRunning) {
            currentSpeed += speedIncrement;
            System.out.println("Vehicle accelerating. Current speed: " + currentSpeed + " km/h");
        } else {
            System.out.println("Cannot accelerate. Engine is not running!");
        }
    }
    
    public void brake() {
        if (currentSpeed > 0) {
            currentSpeed = Math.max(0, currentSpeed - 20);
            System.out.println("Applying brakes. Current speed: " + currentSpeed + " km/h");
        } else {
            System.out.println("Vehicle is already stationary");
        }
    }
    
    // Concrete method for displaying details
    public void displayDetails() {
        System.out.println("Model: " + model);
        System.out.println("Type: " + type);
        System.out.println("Year: " + year);
        System.out.println("Current Speed: " + currentSpeed + " km/h");
        System.out.println("Engine Status: " + (engineRunning ? "Running" : "Stopped"));
    }
    
    // Getters
    public String getModel() {
        return model;
    }
    
    public String getType() {
        return type;
    }
    
    public int getYear() {
        return year;
    }
    
    public int getCurrentSpeed() {
        return currentSpeed;
    }
    
    // Static method to get total vehicles
    public static int getTotalVehiclesCreated() {
        return totalVehiclesCreated;
    }
}

// Concrete subclass Car
class Car extends Vehicle {
    private int numDoors;
    private String fuelType;
    
    // Constructor calling the parent abstract class constructor
    public Car(String model, String type, int year, int numDoors, String fuelType) {
        super(model, type, year); // Call to abstract class constructor
        this.numDoors = numDoors;
        this.fuelType = fuelType;
    }
    
    // Implementation of abstract method
    @Override
    public void start() {
        engineRunning = true;
        System.out.println("Car engine started. Fuel type: " + fuelType);
    }
    
    // Implementation of abstract method
    @Override
    public void stop() {
        engineRunning = false;
        currentSpeed = 0;
        System.out.println("Car engine stopped");
    }
    
    // Class-specific method
    public void honk() {
        System.out.println("Car honking: Beep Beep!");
    }
    
    // Override displayDetails to add car-specific information
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Number of Doors: " + numDoors);
        System.out.println("Fuel Type: " + fuelType);
    }
}

// Another concrete subclass
class Motorcycle extends Vehicle {
    private int engineCapacity; // in cc
    
    public Motorcycle(String model, String type, int year, int engineCapacity) {
        super(model, type, year);
        this.engineCapacity = engineCapacity;
    }
    
    @Override
    public void start() {
        engineRunning = true;
        System.out.println("Motorcycle engine started. Engine capacity: " + engineCapacity + "cc");
    }
    
    @Override
    public void stop() {
        engineRunning = false;
        currentSpeed = 0;
        System.out.println("Motorcycle engine stopped");
    }
    
    // Class-specific method
    public void performStunt(String stuntName) {
        if (engineRunning) {
            System.out.println("Motorcycle performing stunt: " + stuntName);
        } else {
            System.out.println("Cannot perform stunt. Engine is not running!");
        }
    }
    
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Engine Capacity: " + engineCapacity + "cc");
    }
}

// Another concrete subclass
class Truck extends Vehicle {
    private int loadCapacity; // in kg
    private int currentLoad;
    
    public Truck(String model, String type, int year, int loadCapacity) {
        super(model, type, year);
        this.loadCapacity = loadCapacity;
        this.currentLoad = 0;
    }
    
    @Override
    public void start() {
        engineRunning = true;
        System.out.println("Truck engine started. Load capacity: " + loadCapacity + "kg");
    }
    
    @Override
    public void stop() {
        engineRunning = false;
        currentSpeed = 0;
        System.out.println("Truck engine stopped");
    }
    
    // Class-specific methods
    public void loadCargo(int weight) {
        if (weight > 0 && weight + currentLoad <= loadCapacity) {
            currentLoad += weight;
            System.out.println("Cargo loaded: " + weight + "kg. Current load: " + currentLoad + "kg");
        } else {
            System.out.println("Cannot load cargo. Exceeds capacity or invalid weight!");
        }
    }
    
    public void unloadCargo() {
        if (currentLoad > 0) {
            System.out.println("Unloaded cargo: " + currentLoad + "kg");
            currentLoad = 0;
        } else {
            System.out.println("No cargo to unload");
        }
    }
    
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Load Capacity: " + loadCapacity + "kg");
        System.out.println("Current Load: " + currentLoad + "kg");
    }
}
