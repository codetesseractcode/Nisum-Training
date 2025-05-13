/**
 * Program to demonstrate Structural Design Patterns
 * 
 * This program covers the seven main structural design patterns:
 * 1. Adapter Pattern
 * 2. Bridge Pattern
 * 3. Composite Pattern
 * 4. Decorator Pattern
 * 5. Facade Pattern
 * 6. Flyweight Pattern
 * 7. Proxy Pattern
 * 
 * Each pattern is demonstrated with a practical example.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class p49_StructuralDesignPatterns {
    public static void main(String[] args) {
        System.out.println("=== Structural Design Patterns ===\n");
        
        // 1. Adapter Pattern
        demonstrateAdapterPattern();
        
        // 2. Bridge Pattern
        demonstrateBridgePattern();
        
        // 3. Composite Pattern
        demonstrateCompositePattern();
        
        // 4. Decorator Pattern
        demonstrateDecoratorPattern();
        
        // 5. Facade Pattern
        demonstrateFacadePattern();
        
        // 6. Flyweight Pattern
        demonstrateFlyweightPattern();
        
        // 7. Proxy Pattern
        demonstrateProxyPattern();
        
        System.out.println("\n=== End of Structural Design Patterns ===");
    }
    
    /**
     * 1. Adapter Pattern
     * 
     * Adapter Pattern allows classes with incompatible interfaces to work together
     * by converting the interface of a class into another interface that clients expect.
     */
    private static void demonstrateAdapterPattern() {
        System.out.println("1. ADAPTER PATTERN");
        System.out.println("----------------");
        System.out.println("The Adapter Pattern converts the interface of a class into another interface");
        System.out.println("clients expect. It allows classes to work together that couldn't otherwise.");
        System.out.println();
        
        // Old system with legacy JSON format
        LegacyJsonProvider legacyProvider = new LegacyJsonProvider();
        
        // Modern system expects XML format - we need an adapter
        XmlAdapter adapter = new JsonToXmlAdapter(legacyProvider);
        
        // Client code works with the XML interface
        System.out.println("Client requests data in XML format:");
        String xmlData = adapter.getXmlData();
        System.out.println("Received: " + xmlData);
        
        System.out.println();
    }
    
    /**
     * 2. Bridge Pattern
     * 
     * Bridge Pattern separates an abstraction from its implementation so that
     * they can vary independently.
     */
    private static void demonstrateBridgePattern() {
        System.out.println("2. BRIDGE PATTERN");
        System.out.println("---------------");
        System.out.println("The Bridge Pattern separates an abstraction from its implementation so");
        System.out.println("they can vary independently. It's useful for platform independence.");
        System.out.println();
        
        // Create different renderers (implementations)
        Renderer vectorRenderer = new VectorRenderer();
        Renderer rasterRenderer = new RasterRenderer();
        
        // Create different shapes (abstractions) with different renderers
        Shape circle = new Circle(vectorRenderer, "Large Circle", 10);
        Shape square = new Square(rasterRenderer, "Small Square", 5);
        
        // Draw the shapes - they will use their specific renderers
        circle.draw();
        square.draw();
        
        // Change the implementation for the circle
        System.out.println("\nChanging renderer for the circle:");
        circle = new Circle(rasterRenderer, "Large Circle", 10);
        circle.draw();
        
        System.out.println();
    }
    
    /**
     * 3. Composite Pattern
     * 
     * Composite Pattern composes objects into tree structures to represent
     * part-whole hierarchies. It lets clients treat individual objects and
     * compositions of objects uniformly.
     */
    private static void demonstrateCompositePattern() {
        System.out.println("3. COMPOSITE PATTERN");
        System.out.println("------------------");
        System.out.println("The Composite Pattern composes objects into tree structures to represent");
        System.out.println("part-whole hierarchies, allowing uniform treatment of objects and compositions.");
        System.out.println();
        
        // Create leaf components (individual files)
        FileSystemComponent file1 = new File("document.txt", 200);
        FileSystemComponent file2 = new File("image.jpg", 500);
        FileSystemComponent file3 = new File("video.mp4", 1500);
        
        // Create a composite (directory) and add files to it
        Directory documents = new Directory("Documents");
        documents.add(file1);
        
        Directory media = new Directory("Media");
        media.add(file2);
        media.add(file3);
        
        // Create a root directory and add subdirectories
        Directory root = new Directory("Root");
        root.add(documents);
        root.add(media);
        
        // Print the entire file structure and total size
        System.out.println("File system structure:");
        root.print(0);
        System.out.println("\nTotal size: " + root.getSize() + " KB");
        
        System.out.println();
    }
    
    /**
     * 4. Decorator Pattern
     * 
     * Decorator Pattern attaches additional responsibilities to an object
     * dynamically. It provides a flexible alternative to subclassing for
     * extending functionality.
     */
    private static void demonstrateDecoratorPattern() {
        System.out.println("4. DECORATOR PATTERN");
        System.out.println("------------------");
        System.out.println("The Decorator Pattern attaches additional responsibilities to objects");
        System.out.println("dynamically, providing a flexible alternative to subclassing.");
        System.out.println();
        
        // Create a basic coffee (the base component)
        Coffee simpleCoffee = new SimpleCoffee();
        System.out.println("Simple Coffee:");
        System.out.println("  Cost: $" + simpleCoffee.getCost());
        System.out.println("  Description: " + simpleCoffee.getDescription());
        
        // Decorate with milk
        Coffee milkCoffee = new MilkDecorator(simpleCoffee);
        System.out.println("\nCoffee with Milk:");
        System.out.println("  Cost: $" + milkCoffee.getCost());
        System.out.println("  Description: " + milkCoffee.getDescription());
        
        // Decorate with sugar
        Coffee sweetMilkCoffee = new SugarDecorator(milkCoffee);
        System.out.println("\nCoffee with Milk and Sugar:");
        System.out.println("  Cost: $" + sweetMilkCoffee.getCost());
        System.out.println("  Description: " + sweetMilkCoffee.getDescription());
        
        // Create a double sugar, milk and whipped cream coffee
        Coffee deluxeCoffee = new WhippedCreamDecorator(
                                  new SugarDecorator(
                                      new SugarDecorator(
                                          new MilkDecorator(new SimpleCoffee()))));
        System.out.println("\nDeluxe Coffee:");
        System.out.println("  Cost: $" + deluxeCoffee.getCost());
        System.out.println("  Description: " + deluxeCoffee.getDescription());
        
        System.out.println();
    }
    
    /**
     * 5. Facade Pattern
     * 
     * Facade Pattern provides a unified interface to a set of interfaces in a subsystem.
     * It defines a higher-level interface that makes the subsystem easier to use.
     */
    private static void demonstrateFacadePattern() {
        System.out.println("5. FACADE PATTERN");
        System.out.println("---------------");
        System.out.println("The Facade Pattern provides a unified interface to a set of interfaces");
        System.out.println("in a subsystem, making it easier to use by hiding complexity.");
        System.out.println();
        
        // Complex subsystems
        Engine engine = new Engine();
        FuelInjector fuelInjector = new FuelInjector();
        Starter starter = new Starter();
        CoolingSystem coolingSystem = new CoolingSystem();
        
        // Instead of working with each subsystem directly,
        // use a facade to simplify operations
        CarFacade carFacade = new CarFacade(engine, fuelInjector, starter, coolingSystem);
        
        System.out.println("Starting the car with facade:");
        carFacade.startCar();
        
        System.out.println("\nStopping the car with facade:");
        carFacade.stopCar();
        
        System.out.println();
    }
    
    /**
     * 6. Flyweight Pattern
     * 
     * Flyweight Pattern uses sharing to support large numbers of fine-grained
     * objects efficiently. It minimizes memory usage by sharing common parts of objects.
     */
    private static void demonstrateFlyweightPattern() {
        System.out.println("6. FLYWEIGHT PATTERN");
        System.out.println("------------------");
        System.out.println("The Flyweight Pattern minimizes memory use by sharing as much as possible");
        System.out.println("with similar objects. It's useful when you need many similar objects.");
        System.out.println();
        
        // Create a tree factory which will manage flyweight objects
        TreeFactory treeFactory = new TreeFactory();
        
        // Create a forest with many trees but few actual tree objects
        System.out.println("Creating a forest with different types of trees:");
        
        // Let's plant 10 trees of various types
        String[] treeTypes = {"Oak", "Maple", "Pine", "Oak", "Maple", "Pine", "Oak", "Pine", "Oak", "Maple"};
        int[] xCoordinates = {1, 5, 10, 15, 20, 25, 30, 35, 40, 45};
        int[] yCoordinates = {2, 7, 12, 17, 22, 27, 32, 37, 42, 47};
        
        // Plant the forest
        for (int i = 0; i < 10; i++) {
            Tree tree = treeFactory.getTree(treeTypes[i]);
            tree.plant(xCoordinates[i], yCoordinates[i]);
        }
        
        // Show how many tree objects were actually created
        System.out.println("\nUnique tree objects created: " + treeFactory.getTreeCount());
        
        System.out.println();
    }
    
    /**
     * 7. Proxy Pattern
     * 
     * Proxy Pattern provides a surrogate or placeholder for another object to control
     * access to it. It can be used for lazy loading, access control, logging, etc.
     */
    private static void demonstrateProxyPattern() {
        System.out.println("7. PROXY PATTERN");
        System.out.println("--------------");
        System.out.println("The Proxy Pattern provides a surrogate or placeholder for another object");
        System.out.println("to control access to it. It's useful for lazy loading and access control.");
        System.out.println();
        
        // Create a proxy for an expensive image object
        Image image1 = new ImageProxy("photo1.jpg");
        Image image2 = new ImageProxy("photo2.jpg");
        
        // Image is not loaded until it's displayed
        System.out.println("Images created but not loaded yet:");
        System.out.println("  Image 1: " + image1.getFilename());
        System.out.println("  Image 2: " + image2.getFilename());
        
        // Only when display is called will the real image be loaded
        System.out.println("\nDisplaying Image 1 (will trigger loading):");
        image1.display();
        
        System.out.println("\nDisplaying Image 1 again (already loaded):");
        image1.display();
        
        System.out.println("\nImage 2 is still not loaded");
        
        System.out.println();
    }
}

// -------- Adapter Pattern Implementation --------

// Target interface (what the client expects)
interface XmlAdapter {
    String getXmlData();
}

// Adaptee (the incompatible interface)
class LegacyJsonProvider {
    public String getJsonData() {
        return "{\"name\": \"John\", \"age\": 30}";
    }
}

// Adapter (converts one interface to another)
class JsonToXmlAdapter implements XmlAdapter {
    private LegacyJsonProvider legacyProvider;
    
    public JsonToXmlAdapter(LegacyJsonProvider legacyProvider) {
        this.legacyProvider = legacyProvider;
    }
    
    @Override
    public String getXmlData() {
        // Get the JSON data from legacy provider
        String jsonData = legacyProvider.getJsonData();
        System.out.println("  Original JSON: " + jsonData);
        
        // Convert JSON to XML (simplified for example)
        return convertJsonToXml(jsonData);
    }
    
    private String convertJsonToXml(String jsonData) {
        // This is a very simplified conversion for demonstration
        return "<user><name>John</name><age>30</age></user>";
    }
}

// -------- Bridge Pattern Implementation --------

// Implementation interface
interface Renderer {
    void renderCircle(String name, int radius);
    void renderSquare(String name, int side);
}

// Concrete Implementations
class VectorRenderer implements Renderer {
    @Override
    public void renderCircle(String name, int radius) {
        System.out.println("  Drawing " + name + " as vector circle with radius " + radius);
    }
    
    @Override
    public void renderSquare(String name, int side) {
        System.out.println("  Drawing " + name + " as vector square with side " + side);
    }
}

class RasterRenderer implements Renderer {
    @Override
    public void renderCircle(String name, int radius) {
        System.out.println("  Drawing " + name + " as rasterized circle with radius " + radius);
    }
    
    @Override
    public void renderSquare(String name, int side) {
        System.out.println("  Drawing " + name + " as rasterized square with side " + side);
    }
}

// Abstraction
abstract class Shape {
    protected Renderer renderer;
    protected String name;
    
    public Shape(Renderer renderer, String name) {
        this.renderer = renderer;
        this.name = name;
    }
    
    public abstract void draw();
}

// Refined Abstractions
class Circle extends Shape {
    private int radius;
    
    public Circle(Renderer renderer, String name, int radius) {
        super(renderer, name);
        this.radius = radius;
    }
    
    @Override
    public void draw() {
        renderer.renderCircle(name, radius);
    }
}

class Square extends Shape {
    private int side;
    
    public Square(Renderer renderer, String name, int side) {
        super(renderer, name);
        this.side = side;
    }
    
    @Override
    public void draw() {
        renderer.renderSquare(name, side);
    }
}

// -------- Composite Pattern Implementation --------

// Component interface
interface FileSystemComponent {
    String getName();
    int getSize();
    void print(int indentation);
}

// Leaf implementation
class File implements FileSystemComponent {
    private String name;
    private int size;
    
    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public int getSize() {
        return size;
    }
    
    @Override
    public void print(int indentation) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentation; i++) {
            sb.append("  ");
        }
        System.out.println(sb.toString() + "- " + name + " (" + size + " KB)");
    }
}

// Composite implementation
class Directory implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> children;
    
    public Directory(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }
    
    public void add(FileSystemComponent component) {
        children.add(component);
    }
    
    public void remove(FileSystemComponent component) {
        children.remove(component);
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public int getSize() {
        // Size is the sum of all children
        int totalSize = 0;
        for (FileSystemComponent component : children) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
    
    @Override
    public void print(int indentation) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentation; i++) {
            sb.append("  ");
        }
        System.out.println(sb.toString() + "+ " + name + " (Directory)");
        
        // Print all children with increased indentation
        for (FileSystemComponent component : children) {
            component.print(indentation + 1);
        }
    }
}

// -------- Decorator Pattern Implementation --------

// Component interface
interface Coffee {
    double getCost();
    String getDescription();
}

// Concrete Component
class SimpleCoffee implements Coffee {
    @Override
    public double getCost() {
        return 2.0;
    }
    
    @Override
    public String getDescription() {
        return "Simple coffee";
    }
}

// Decorator base class
abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;
    
    public CoffeeDecorator(Coffee decoratedCoffee) {
        this.decoratedCoffee = decoratedCoffee;
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost();
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription();
    }
}

// Concrete Decorators
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }
    
    @Override
    public double getCost() {
        return super.getCost() + 0.5;
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + ", with milk";
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }
    
    @Override
    public double getCost() {
        return super.getCost() + 0.2;
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + ", with sugar";
    }
}

class WhippedCreamDecorator extends CoffeeDecorator {
    public WhippedCreamDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }
    
    @Override
    public double getCost() {
        return super.getCost() + 1.0;
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + ", with whipped cream";
    }
}

// -------- Facade Pattern Implementation --------

// Complex subsystem classes
class Engine {
    public void start() {
        System.out.println("  Engine is starting...");
    }
    
    public void stop() {
        System.out.println("  Engine is stopping...");
    }
}

class FuelInjector {
    public void injectFuel() {
        System.out.println("  Fuel is being injected...");
    }
    
    public void stopInjecting() {
        System.out.println("  Fuel injection stopped...");
    }
}

class Starter {
    public void activate() {
        System.out.println("  Starter activated...");
    }
    
    public void deactivate() {
        System.out.println("  Starter deactivated...");
    }
}

class CoolingSystem {
    public void start() {
        System.out.println("  Cooling system started...");
    }
    
    public void stop() {
        System.out.println("  Cooling system stopped...");
    }
}

// Facade class
class CarFacade {
    private Engine engine;
    private FuelInjector fuelInjector;
    private Starter starter;
    private CoolingSystem coolingSystem;
    
    public CarFacade(Engine engine, FuelInjector fuelInjector, Starter starter, CoolingSystem coolingSystem) {
        this.engine = engine;
        this.fuelInjector = fuelInjector;
        this.starter = starter;
        this.coolingSystem = coolingSystem;
    }
    
    public void startCar() {
        System.out.println("  Starting the car sequence...");
        fuelInjector.injectFuel();
        starter.activate();
        engine.start();
        coolingSystem.start();
        System.out.println("  Car is ready to drive!");
    }
    
    public void stopCar() {
        System.out.println("  Stopping the car...");
        coolingSystem.stop();
        engine.stop();
        fuelInjector.stopInjecting();
        starter.deactivate();
        System.out.println("  Car has been shut down.");
    }
}

// -------- Flyweight Pattern Implementation --------

// Flyweight interface
interface Tree {
    void plant(int x, int y);
}

// Concrete Flyweight
class TreeType implements Tree {
    private final String name;
    private final String color;
    private final String texture;
    
    public TreeType(String name, String color, String texture) {
        this.name = name;
        this.color = color;
        this.texture = texture;
        System.out.println("  Creating new " + name + " tree type object");
    }
    
    @Override
    public void plant(int x, int y) {
        System.out.println("  Planting " + name + " tree at (" + x + "," + y + ")");
    }
}

// Flyweight Factory
class TreeFactory {
    private Map<String, TreeType> treeTypes = new HashMap<>();
    
    public Tree getTree(String name) {
        TreeType treeType = treeTypes.get(name);
        
        // If the tree type doesn't exist, create a new one
        if (treeType == null) {
            if ("Oak".equals(name)) {
                treeType = new TreeType("Oak", "Brown", "Rough");
            } else if ("Maple".equals(name)) {
                treeType = new TreeType("Maple", "Red", "Smooth");
            } else {
                treeType = new TreeType("Pine", "Green", "Needles");
            }
            treeTypes.put(name, treeType);
        }
        
        return treeType;
    }
    
    public int getTreeCount() {
        return treeTypes.size();
    }
}

// -------- Proxy Pattern Implementation --------

// Subject interface
interface Image {
    void display();
    String getFilename();
}

// Real Subject
class RealImage implements Image {
    private String filename;
    
    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }
    
    private void loadFromDisk() {
        System.out.println("  Loading image: " + filename);
        // Simulating a heavy operation
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void display() {
        System.out.println("  Displaying image: " + filename);
    }
    
    @Override
    public String getFilename() {
        return filename;
    }
}

// Proxy
class ImageProxy implements Image {
    private RealImage realImage;
    private String filename;
    
    public ImageProxy(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void display() {
        // Lazy loading: create the RealImage only when needed
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
    
    @Override
    public String getFilename() {
        return filename;
    }
}
