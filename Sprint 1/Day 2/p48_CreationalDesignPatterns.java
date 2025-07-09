/**
 * Program to demonstrate Creational Design Patterns
 * 
 * This program covers the five main creational design patterns:
 * 1. Factory Pattern
 * 2. Abstract Factory Pattern
 * 3. Singleton Pattern
 * 4. Prototype Pattern
 * 5. Builder Pattern
 * 
 * Each pattern is demonstrated with a practical example.
 */

import java.util.HashMap;
import java.util.Map;

public class p48_CreationalDesignPatterns {
    public static void main(String[] args) {
        System.out.println("=== Creational Design Patterns ===\n");
        
        // 1. Factory Pattern
        demonstrateFactoryPattern();
        
        // 2. Abstract Factory Pattern
        demonstrateAbstractFactoryPattern();
        
        // 3. Singleton Pattern
        demonstrateSingletonPattern();
        
        // 4. Prototype Pattern
        demonstratePrototypePattern();
        
        // 5. Builder Pattern
        demonstrateBuilderPattern();
        
        System.out.println("\n=== End of Creational Design Patterns ===");
    }
    
    /**
     * 1. Factory Pattern
     * 
     * Factory Pattern provides a way to create objects without specifying the exact class to create.
     * It encapsulates object creation logic and makes it more flexible and reusable.
     */
    private static void demonstrateFactoryPattern() {
        System.out.println("1. FACTORY PATTERN");
        System.out.println("-----------------");
        System.out.println("The Factory Pattern provides an interface for creating objects without");
        System.out.println("specifying their concrete classes. It centralizes object creation logic.");
        System.out.println();
        
        // Using the factory to create vehicles
        VehicleFactory factory = new VehicleFactory();
        
        // Create a car
        Vehicle car = factory.createVehicle("car");
        car.start();
        car.accelerate();
        car.stop();
        
        // Create a motorcycle
        Vehicle motorcycle = factory.createVehicle("motorcycle");
        motorcycle.start();
        motorcycle.accelerate();
        motorcycle.stop();
        
        // Try creating an unknown vehicle type
        Vehicle unknown = factory.createVehicle("submarine");
        if (unknown != null) {
            unknown.start();
        }
        
        System.out.println();
    }
    
    /**
     * 2. Abstract Factory Pattern
     * 
     * Abstract Factory Pattern provides an interface for creating families of related
     * or dependent objects without specifying their concrete classes.
     */
    private static void demonstrateAbstractFactoryPattern() {
        System.out.println("2. ABSTRACT FACTORY PATTERN");
        System.out.println("-------------------------");
        System.out.println("The Abstract Factory Pattern provides an interface for creating families");
        System.out.println("of related objects without specifying their concrete classes.");
        System.out.println();
        
        // Create Modern GUI Factory
        GUIFactory modernFactory = new ModernGUIFactory();
        createAndDisplayGUI(modernFactory, "Modern");
        
        // Create Classic GUI Factory
        GUIFactory classicFactory = new ClassicGUIFactory();
        createAndDisplayGUI(classicFactory, "Classic");
        
        System.out.println();
    }
    
    /**
     * Helper method for Abstract Factory demonstration
     */
    private static void createAndDisplayGUI(GUIFactory factory, String style) {
        Button button = factory.createButton();
        TextField textField = factory.createTextField();
        
        System.out.println("Created " + style + " GUI elements:");
        button.render();
        textField.render();
        System.out.println();
    }
    
    /**
     * 3. Singleton Pattern
     * 
     * Singleton Pattern ensures a class has only one instance and provides
     * a global point of access to that instance.
     */
    private static void demonstrateSingletonPattern() {
        System.out.println("3. SINGLETON PATTERN");
        System.out.println("-------------------");
        System.out.println("The Singleton Pattern ensures a class has only one instance and provides");
        System.out.println("a global point of access to it. It's useful for centralized management.");
        System.out.println();
        
        // Try to get the instance multiple times - should be the same object
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        db1.setConnectionString("jdbc:mysql://localhost:3306/db1");
        db1.connect();
        
        // Get another reference - it should be the same instance
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        System.out.println("Are references the same? " + (db1 == db2));
        System.out.println("Current connection string: " + db2.getConnectionString());
        
        // Disconnect
        db1.disconnect();
        
        System.out.println();
    }
    
    /**
     * 4. Prototype Pattern
     * 
     * Prototype Pattern creates new objects by copying an existing object, known as the prototype.
     */
    private static void demonstratePrototypePattern() {
        System.out.println("4. PROTOTYPE PATTERN");
        System.out.println("-------------------");
        System.out.println("The Prototype Pattern creates new objects by copying an existing object.");
        System.out.println("It's useful when object creation is more expensive than copying.");
        System.out.println();
        
        // Create a document prototype registry
        DocumentRegistry registry = new DocumentRegistry();
        
        // Clone a resume document
        Document resumeClone = registry.getDocument("RESUME");
        resumeClone.setContent("John Doe's Resume");
        System.out.println(resumeClone);
        
        // Clone a report document
        Document reportClone = registry.getDocument("REPORT");
        reportClone.setContent("Sales Report 2025");
        System.out.println(reportClone);
        
        // Clone the resume again - should be a new object with default content
        Document anotherResume = registry.getDocument("RESUME");
        System.out.println("Are resume clones different objects? " + (resumeClone != anotherResume));
        System.out.println(anotherResume);
        
        System.out.println();
    }
    
    /**
     * 5. Builder Pattern
     * 
     * Builder Pattern separates the construction of a complex object from its representation,
     * allowing the same construction process to create different representations.
     */
    private static void demonstrateBuilderPattern() {
        System.out.println("5. BUILDER PATTERN");
        System.out.println("-----------------");
        System.out.println("The Builder Pattern separates complex object construction from its representation.");
        System.out.println("It creates objects step by step and allows different representations.");
        System.out.println();
        
        // Build a computer with all specifications
        Computer gamingPC = new Computer.Builder()
                .setCPU("Intel i9-13900K")
                .setRAM("32GB DDR5")
                .setStorage("2TB NVMe SSD")
                .setGPU("NVIDIA RTX 4080")
                .build();
        System.out.println("Gaming PC:");
        System.out.println(gamingPC);
        
        // Build a basic office computer
        Computer officePC = new Computer.Builder()
                .setCPU("Intel i5-12400")
                .setRAM("16GB DDR4")
                .setStorage("512GB SSD")
                .build(); // No GPU specified
        System.out.println("\nOffice PC:");
        System.out.println(officePC);
        
        System.out.println();
    }
}

// -------- Factory Pattern Implementation --------

// Vehicle interface
interface Vehicle {
    void start();
    void accelerate();
    void stop();
}

// Concrete Vehicle - Car
class Car implements Vehicle {
    @Override
    public void start() {
        System.out.println("Car: Starting engine");
    }
    
    @Override
    public void accelerate() {
        System.out.println("Car: Accelerating on the road");
    }
    
    @Override
    public void stop() {
        System.out.println("Car: Applying brakes and stopping");
    }
}

// Concrete Vehicle - Motorcycle
class Motorcycle implements Vehicle {
    @Override
    public void start() {
        System.out.println("Motorcycle: Kick-starting the engine");
    }
    
    @Override
    public void accelerate() {
        System.out.println("Motorcycle: Twisting throttle and accelerating");
    }
    
    @Override
    public void stop() {
        System.out.println("Motorcycle: Applying brakes and stopping");
    }
}

// Factory class that creates vehicles
class VehicleFactory {
    public Vehicle createVehicle(String vehicleType) {
        if (vehicleType == null) {
            return null;
        }
        
        if (vehicleType.equalsIgnoreCase("CAR")) {
            return new Car();
        } else if (vehicleType.equalsIgnoreCase("MOTORCYCLE")) {
            return new Motorcycle();
        }
        
        System.out.println("Unknown vehicle type: " + vehicleType);
        return null;
    }
}

// -------- Abstract Factory Pattern Implementation --------

// Abstract product - Button
interface Button {
    void render();
}

// Abstract product - TextField
interface TextField {
    void render();
}

// Abstract Factory - GUI Factory
interface GUIFactory {
    Button createButton();
    TextField createTextField();
}

// Concrete products - Modern style
class ModernButton implements Button {
    @Override
    public void render() {
        System.out.println("  - Rendering a modern flat button with rounded corners");
    }
}

class ModernTextField implements TextField {
    @Override
    public void render() {
        System.out.println("  - Rendering a modern borderless text field");
    }
}

// Concrete products - Classic style
class ClassicButton implements Button {
    @Override
    public void render() {
        System.out.println("  - Rendering a classic 3D button with shadow");
    }
}

class ClassicTextField implements TextField {
    @Override
    public void render() {
        System.out.println("  - Rendering a classic bordered text field");
    }
}

// Concrete factories
class ModernGUIFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new ModernButton();
    }
    
    @Override
    public TextField createTextField() {
        return new ModernTextField();
    }
}

class ClassicGUIFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new ClassicButton();
    }
    
    @Override
    public TextField createTextField() {
        return new ClassicTextField();
    }
}

// -------- Singleton Pattern Implementation --------

class DatabaseConnection {
    // The single instance
    private static DatabaseConnection instance;
    private String connectionString;
    private boolean connected;
    
    // Private constructor prevents instantiation from outside
    private DatabaseConnection() {
        this.connectionString = "";
        this.connected = false;
    }
    
    // The global access point
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    // Methods for the database connection
    public void connect() {
        this.connected = true;
        System.out.println("Connected to database: " + connectionString);
    }
    
    public void disconnect() {
        if (this.connected) {
            this.connected = false;
            System.out.println("Disconnected from database");
        }
    }
    
    public String getConnectionString() {
        return connectionString;
    }
    
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
}

// -------- Prototype Pattern Implementation --------

// Prototype interface
interface Cloneable {
    Object clone();
}

// Concrete prototype - Document
abstract class Document implements Cloneable {
    private String title;
    private String content;
    
    public Document() {
        this.content = "Default content";
    }
    
    public Document(String title) {
        this.title = title;
        this.content = "Default content";
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getContent() {
        return content;
    }
    
    public String getTitle() {
        return title;
    }
    
    @Override
    public String toString() {
        return "Document [type=" + this.getClass().getSimpleName() + 
               ", title=" + title + 
               ", content=" + content + "]";
    }
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

class ResumeDocument extends Document {
    public ResumeDocument() {
        super("Resume Template");
    }
    
    // Additional resume-specific methods could go here
}

class ReportDocument extends Document {
    public ReportDocument() {
        super("Report Template");
    }
    
    // Additional report-specific methods could go here
}

// Registry of prototypes - manages prototypes and cloning
class DocumentRegistry {
    private Map<String, Document> registry = new HashMap<>();
    
    public DocumentRegistry() {
        // Initialize with prototype documents
        registry.put("RESUME", new ResumeDocument());
        registry.put("REPORT", new ReportDocument());
    }
    
    public Document getDocument(String type) {
        Document doc = registry.get(type);
        if (doc != null) {
            // Return a clone of the prototype
            return (Document) doc.clone();
        }
        return null;
    }
}

// -------- Builder Pattern Implementation --------

// Product
class Computer {
    private final String CPU;
    private final String RAM;
    private final String storage;
    private final String GPU; // Optional
    
    private Computer(Builder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.storage = builder.storage;
        this.GPU = builder.GPU;
    }
    
    @Override
    public String toString() {
        return "  CPU: " + CPU + "\n" +
               "  RAM: " + RAM + "\n" +
               "  Storage: " + storage +
               (GPU != null ? "\n  GPU: " + GPU : "\n  GPU: Not installed");
    }
    
    // Builder class
    public static class Builder {
        private String CPU;
        private String RAM;
        private String storage;
        private String GPU;
        
        public Builder setCPU(String CPU) {
            this.CPU = CPU;
            return this;
        }
        
        public Builder setRAM(String RAM) {
            this.RAM = RAM;
            return this;
        }
        
        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }
        
        public Builder setGPU(String GPU) {
            this.GPU = GPU;
            return this;
        }
        
        public Computer build() {
            return new Computer(this);
        }
    }
}
