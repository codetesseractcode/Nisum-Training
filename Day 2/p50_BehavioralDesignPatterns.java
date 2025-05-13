/**
 * Program to demonstrate Behavioral Design Patterns
 * 
 * This program covers the eleven main behavioral design patterns:
 * 1. Chain of Responsibility Pattern
 * 2. Command Pattern
 * 3. Interpreter Pattern
 * 4. Iterator Pattern
 * 5. Mediator Pattern
 * 6. Memento Pattern
 * 7. Observer Pattern
 * 8. State Pattern
 * 9. Strategy Pattern
 * 10. Template Pattern
 * 11. Visitor Pattern
 * 
 * Each pattern is demonstrated with a practical example.
 */

import java.util.*;
import java.util.function.Function;

public class p50_BehavioralDesignPatterns {
    public static void main(String[] args) {
        System.out.println("=== Behavioral Design Patterns ===\n");
        
        // 1. Chain of Responsibility Pattern
        demonstrateChainOfResponsibility();
        
        // 2. Command Pattern
        demonstrateCommandPattern();
        
        // 3. Interpreter Pattern
        demonstrateInterpreterPattern();
        
        // 4. Iterator Pattern
        demonstrateIteratorPattern();
        
        // 5. Mediator Pattern
        demonstrateMediatorPattern();
        
        // 6. Memento Pattern
        demonstrateMementoPattern();
        
        // 7. Observer Pattern
        demonstrateObserverPattern();
        
        // 8. State Pattern
        demonstrateStatePattern();
        
        // 9. Strategy Pattern
        demonstrateStrategyPattern();
        
        // 10. Template Pattern
        demonstrateTemplatePattern();
        
        // 11. Visitor Pattern
        demonstrateVisitorPattern();
        
        System.out.println("\n=== End of Behavioral Design Patterns ===");
    }
    
    /**
     * 1. Chain of Responsibility Pattern
     * 
     * Chain of Responsibility Pattern avoids coupling the sender of a request to its receiver 
     * by giving more than one object a chance to handle the request. It passes the request 
     * along a chain of handlers until one of them handles it.
     */
    private static void demonstrateChainOfResponsibility() {
        System.out.println("1. CHAIN OF RESPONSIBILITY PATTERN");
        System.out.println("-------------------------------");
        System.out.println("The Chain of Responsibility Pattern passes requests along a chain of handlers.");
        System.out.println("Each handler decides either to process the request or to pass it to the next handler.");
        System.out.println();
        
        // Create a support chain
        SupportHandler technicalSupport = new TechnicalSupportHandler();
        SupportHandler billingSupport = new BillingSupportHandler();
        SupportHandler generalSupport = new GeneralSupportHandler();
        
        // Set up the chain
        technicalSupport.setNextHandler(billingSupport);
        billingSupport.setNextHandler(generalSupport);
        
        // Process support requests
        System.out.println("1. Customer has a technical issue:");
        technicalSupport.handleRequest("My printer is not working");
        
        System.out.println("\n2. Customer has a billing question:");
        technicalSupport.handleRequest("I was charged twice for my subscription");
        
        System.out.println("\n3. Customer has a general inquiry:");
        technicalSupport.handleRequest("What are your office hours?");
        
        System.out.println("\n4. Customer has an unknown issue:");
        technicalSupport.handleRequest("I need help with something else");
        
        System.out.println();
    }
    
    /**
     * 2. Command Pattern
     * 
     * Command Pattern encapsulates a request as an object, allowing for
     * parameterization of clients with different requests, queuing of requests,
     * and logging of operations. It also supports undoable operations.
     */
    private static void demonstrateCommandPattern() {
        System.out.println("2. COMMAND PATTERN");
        System.out.println("---------------");
        System.out.println("The Command Pattern encapsulates a request as an object, allowing clients");
        System.out.println("to parameterize operations and queue or log requests. It supports undo operations.");
        System.out.println();
        
        // Create the receiver
        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        
        // Create commands
        Command livingRoomLightOn = new LightOnCommand(livingRoomLight);
        Command livingRoomLightOff = new LightOffCommand(livingRoomLight);
        Command kitchenLightOn = new LightOnCommand(kitchenLight);
        Command kitchenLightOff = new LightOffCommand(kitchenLight);
        
        // Create invoker (remote control)
        RemoteControl remote = new RemoteControl();
        
        // Test the commands
        System.out.println("Testing light commands:");
        
        remote.setCommand(livingRoomLightOn);
        remote.pressButton();
        
        remote.setCommand(kitchenLightOn);
        remote.pressButton();
        
        remote.setCommand(livingRoomLightOff);
        remote.pressButton();
        
        remote.setCommand(kitchenLightOff);
        remote.pressButton();
        
        System.out.println();
    }
    
    /**
     * 3. Interpreter Pattern
     * 
     * Interpreter Pattern defines a grammatical representation for a language
     * and provides an interpreter to deal with this grammar. It's useful for
     * domain-specific languages and parsing expressions.
     */
    private static void demonstrateInterpreterPattern() {
        System.out.println("3. INTERPRETER PATTERN");
        System.out.println("-------------------");
        System.out.println("The Interpreter Pattern defines a grammar for a language and provides an interpreter");
        System.out.println("to evaluate expressions in that language. It's useful for DSLs and parsing.");
        System.out.println();
        
        // Define variables
        Map<String, Integer> context = new HashMap<>();
        context.put("x", 10);
        context.put("y", 20);
        context.put("z", 30);
        
        // Create interpreter for: (x + y) - z
        Expression addExpression = new AddExpression(
            new VariableExpression("x"),
            new VariableExpression("y")
        );
        
        Expression subtractExpression = new SubtractExpression(
            addExpression,
            new VariableExpression("z")
        );
        
        // Interpret the expression
        int result = subtractExpression.interpret(context);
        
        System.out.println("Interpreting expression: (x + y) - z");
        System.out.println("where x = 10, y = 20, z = 30");
        System.out.println("Result: " + result);
        
        System.out.println();
    }
    
    /**
     * 4. Iterator Pattern
     * 
     * Iterator Pattern provides a way to access elements of an aggregate object
     * sequentially without exposing its underlying representation.
     */
    private static void demonstrateIteratorPattern() {
        System.out.println("4. ITERATOR PATTERN");
        System.out.println("-----------------");
        System.out.println("The Iterator Pattern provides a way to access elements of a collection");
        System.out.println("sequentially without exposing its underlying representation.");
        System.out.println();
        
        // Create a collection
        Playlist playlist = new Playlist();
        playlist.add(new Song("Bohemian Rhapsody", "Queen"));
        playlist.add(new Song("Stairway to Heaven", "Led Zeppelin"));
        playlist.add(new Song("Hotel California", "Eagles"));
        playlist.add(new Song("Sweet Child O' Mine", "Guns N' Roses"));
        
        // Get iterator and iterate through the songs
        System.out.println("Songs in the playlist:");
        
        Iterator<Song> iterator = playlist.iterator();
        while (iterator.hasNext()) {
            Song song = iterator.next();
            System.out.println("  " + song.getTitle() + " by " + song.getArtist());
        }
        
        System.out.println();
    }
    
    /**
     * 5. Mediator Pattern
     * 
     * Mediator Pattern defines an object that encapsulates how a set of objects interact.
     * It promotes loose coupling by keeping objects from referring to each other explicitly.
     */
    private static void demonstrateMediatorPattern() {
        System.out.println("5. MEDIATOR PATTERN");
        System.out.println("-----------------");
        System.out.println("The Mediator Pattern defines an object that encapsulates how a set of objects interact.");
        System.out.println("It promotes loose coupling by keeping objects from referring to each other directly.");
        System.out.println();
        
        // Create the mediator
        AirTrafficControlMediator mediator = new AirTrafficControlTower();
        
        // Create flights
        Flight f1 = new Flight("F001", mediator);
        Flight f2 = new Flight("F002", mediator);
        Flight f3 = new Flight("F003", mediator);
        
        // Register flights with mediator
        mediator.registerFlight(f1);
        mediator.registerFlight(f2);
        mediator.registerFlight(f3);
        
        // Simulate flights sending messages
        System.out.println("Flight communication via mediator:");
        f1.send("Requesting permission to land");
        f2.send("Entering holding pattern");
        
        System.out.println();
    }
    
    /**
     * 6. Memento Pattern
     * 
     * Memento Pattern captures and externalizes an object's internal state
     * without violating encapsulation, allowing the object to return to this state later.
     */
    private static void demonstrateMementoPattern() {
        System.out.println("6. MEMENTO PATTERN");
        System.out.println("----------------");
        System.out.println("The Memento Pattern captures an object's internal state so it can be restored later");
        System.out.println("without violating encapsulation. It's useful for implementing undo mechanisms.");
        System.out.println();
        
        // Create originator and caretaker
        TextEditor editor = new TextEditor();
        TextEditorHistory history = new TextEditorHistory();
        
        // Edit the text and save snapshots
        editor.setText("First draft of document");
        history.saveState(editor.createSnapshot());
        System.out.println("Current Text: " + editor.getText());
        
        editor.setText("Second draft with more content");
        history.saveState(editor.createSnapshot());
        System.out.println("Current Text: " + editor.getText());
        
        editor.setText("Final version with corrections");
        System.out.println("Current Text: " + editor.getText());
        
        // Restore to previous states
        System.out.println("\nRestoring to previous version:");
        editor.restoreFromSnapshot(history.getState(1));
        System.out.println("Current Text: " + editor.getText());
        
        System.out.println("\nRestoring to first version:");
        editor.restoreFromSnapshot(history.getState(0));
        System.out.println("Current Text: " + editor.getText());
        
        System.out.println();
    }
    
    /**
     * 7. Observer Pattern
     * 
     * Observer Pattern defines a one-to-many dependency between objects so that
     * when one object changes state, all its dependents are notified and updated automatically.
     */
    private static void demonstrateObserverPattern() {
        System.out.println("7. OBSERVER PATTERN");
        System.out.println("-----------------");
        System.out.println("The Observer Pattern defines a one-to-many dependency between objects");
        System.out.println("so that when one object changes state, all dependents are notified.");
        System.out.println();
        
        // Create subject
        WeatherStation weatherStation = new WeatherStation();
        
        // Create observers
        WeatherDisplay phoneApp = new PhoneApp();
        WeatherDisplay webDisplay = new WebDisplay();
        WeatherDisplay digitalSignage = new DigitalSignage();
        
        // Register observers
        weatherStation.addObserver(phoneApp);
        weatherStation.addObserver(webDisplay);
        weatherStation.addObserver(digitalSignage);
        
        // Change weather data - should notify all observers
        System.out.println("Weather station updates:");
        weatherStation.setWeatherData(28.5f, 65.0f, 1013.2f);
        
        // Remove one observer
        System.out.println("\nRemoving the web display from observers");
        weatherStation.removeObserver(webDisplay);
        
        // Update again
        System.out.println("\nWeather station updates again:");
        weatherStation.setWeatherData(27.8f, 70.5f, 1010.0f);
        
        System.out.println();
    }
    
    /**
     * 8. State Pattern
     * 
     * State Pattern allows an object to alter its behavior when its internal
     * state changes. The object appears to change its class.
     */
    private static void demonstrateStatePattern() {
        System.out.println("8. STATE PATTERN");
        System.out.println("--------------");
        System.out.println("The State Pattern allows an object to alter its behavior when its internal");
        System.out.println("state changes. It appears to the client as if the object changed its class.");
        System.out.println();
        
        // Create a vending machine
        VendingMachine vendingMachine = new VendingMachine(5);
        
        // Operate the vending machine in different states
        System.out.println("Vending Machine operations:");
        
        // Try to dispense without inserting money
        vendingMachine.dispense();
        
        // Insert coin
        vendingMachine.insertCoin();
        
        // Try inserting another coin before selecting item
        vendingMachine.insertCoin();
        
        // Select item
        vendingMachine.selectItem();
        
        // Dispense item
        vendingMachine.dispense();
        
        // Machine should return to waiting for coin
        System.out.println("\nCurrent item count: " + vendingMachine.getItemCount());
        vendingMachine.selectItem();  // Should not work before inserting coin
        
        System.out.println();
    }
    
    /**
     * 9. Strategy Pattern
     * 
     * Strategy Pattern defines a family of algorithms, encapsulates each one, and makes
     * them interchangeable. It lets the algorithm vary independently from clients that use it.
     */
    private static void demonstrateStrategyPattern() {
        System.out.println("9. STRATEGY PATTERN");
        System.out.println("-----------------");
        System.out.println("The Strategy Pattern defines a family of algorithms, encapsulates each one,");
        System.out.println("and makes them interchangeable. Algorithms can vary independently of clients.");
        System.out.println();
        
        // Sample data
        int[] data = {9, 1, 5, 3, 7, 6, 8, 2, 4};
        System.out.println("Original data: " + arrayToString(data));
        
        // Create a sorter with different strategies
        Sorter sorter = new Sorter();
        
        // Sort using bubble sort strategy
        sorter.setStrategy(new BubbleSortStrategy());
        int[] bubbleSorted = sorter.sort(data.clone());
        System.out.println("Bubble sort result: " + arrayToString(bubbleSorted));
        
        // Change strategy to quick sort
        sorter.setStrategy(new QuickSortStrategy());
        int[] quickSorted = sorter.sort(data.clone());
        System.out.println("Quick sort result: " + arrayToString(quickSorted));
        
        // Change strategy to merge sort
        sorter.setStrategy(new MergeSortStrategy());
        int[] mergeSorted = sorter.sort(data.clone());
        System.out.println("Merge sort result: " + arrayToString(mergeSorted));
        
        System.out.println();
    }
    
    /**
     * Helper method to convert array to string
     */
    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * 10. Template Pattern
     * 
     * Template Pattern defines the skeleton of an algorithm in a method, deferring
     * some steps to subclasses. It lets subclasses redefine certain steps of an algorithm
     * without changing the algorithm's structure.
     */
    private static void demonstrateTemplatePattern() {
        System.out.println("10. TEMPLATE PATTERN");
        System.out.println("------------------");
        System.out.println("The Template Pattern defines the skeleton of an algorithm, deferring some steps");
        System.out.println("to subclasses. Subclasses can redefine steps without changing the structure.");
        System.out.println();
        
        // Create data processors
        DataProcessor csvProcessor = new CSVDataProcessor();
        DataProcessor xmlProcessor = new XMLDataProcessor();
        DataProcessor jsonProcessor = new JSONDataProcessor();
        
        // Process different data formats
        System.out.println("Processing CSV data:");
        csvProcessor.processData();
        
        System.out.println("\nProcessing XML data:");
        xmlProcessor.processData();
        
        System.out.println("\nProcessing JSON data:");
        jsonProcessor.processData();
        
        System.out.println();
    }
    
    /**
     * 11. Visitor Pattern
     * 
     * Visitor Pattern represents an operation to be performed on elements of an object
     * structure. It lets you define a new operation without changing the classes of the
     * elements on which it operates.
     */
    private static void demonstrateVisitorPattern() {
        System.out.println("11. VISITOR PATTERN");
        System.out.println("-----------------");
        System.out.println("The Visitor Pattern represents an operation on elements of an object structure.");
        System.out.println("It lets you define new operations without changing the element classes.");
        System.out.println();
        
        // Create a computer structure
        Computer computer = new Computer();
        computer.add(new Keyboard());
        computer.add(new Monitor());
        computer.add(new Mouse());
        
        // Create visitors
        ComputerPartVisitor displayVisitor = new ComputerPartDisplayVisitor();
        ComputerPartVisitor priceVisitor = new ComputerPartPriceVisitor();
        
        // Visit with different visitors
        System.out.println("Display visitor:");
        computer.accept(displayVisitor);
        
        System.out.println("\nPrice visitor:");
        computer.accept(priceVisitor);
        
        System.out.println();
    }
}

// -------- Chain of Responsibility Pattern Implementation --------

// Handler interface
abstract class SupportHandler {
    protected SupportHandler nextHandler;
    
    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
    
    public abstract void handleRequest(String request);
}

// Concrete Handlers
class TechnicalSupportHandler extends SupportHandler {
    @Override
    public void handleRequest(String request) {
        if (request.contains("printer") || request.contains("computer") || request.contains("software")) {
            System.out.println("  Technical Support: Handling technical issue - " + request);
        } else if (nextHandler != null) {
            System.out.println("  Technical Support: Not a technical issue, passing to next handler");
            nextHandler.handleRequest(request);
        } else {
            System.out.println("  Technical Support: No handler found for request");
        }
    }
}

class BillingSupportHandler extends SupportHandler {
    @Override
    public void handleRequest(String request) {
        if (request.contains("bill") || request.contains("charge") || request.contains("subscription")) {
            System.out.println("  Billing Support: Handling billing issue - " + request);
        } else if (nextHandler != null) {
            System.out.println("  Billing Support: Not a billing issue, passing to next handler");
            nextHandler.handleRequest(request);
        } else {
            System.out.println("  Billing Support: No handler found for request");
        }
    }
}

class GeneralSupportHandler extends SupportHandler {
    @Override
    public void handleRequest(String request) {
        if (request.contains("hours") || request.contains("location") || request.contains("policy")) {
            System.out.println("  General Support: Handling general inquiry - " + request);
        } else {
            System.out.println("  General Support: We'll have someone contact you about your issue.");
        }
    }
}

// -------- Command Pattern Implementation --------

// Command interface
interface Command {
    void execute();
}

// Receiver class
class Light {
    private String location;
    
    public Light(String location) {
        this.location = location;
    }
    
    public void turnOn() {
        System.out.println("  " + location + " light is now ON");
    }
    
    public void turnOff() {
        System.out.println("  " + location + " light is now OFF");
    }
}

// Concrete Commands
class LightOnCommand implements Command {
    private Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.turnOn();
    }
}

class LightOffCommand implements Command {
    private Light light;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.turnOff();
    }
}

// Invoker
class RemoteControl {
    private Command command;
    
    public void setCommand(Command command) {
        this.command = command;
    }
    
    public void pressButton() {
        command.execute();
    }
}

// -------- Interpreter Pattern Implementation --------

// Abstract Expression interface
interface Expression {
    int interpret(Map<String, Integer> context);
}

// Terminal Expression
class VariableExpression implements Expression {
    private String variableName;
    
    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }
    
    @Override
    public int interpret(Map<String, Integer> context) {
        if (context.containsKey(variableName)) {
            return context.get(variableName);
        }
        return 0;
    }
}

// Non-terminal Expressions
class AddExpression implements Expression {
    private Expression leftExpression;
    private Expression rightExpression;
    
    public AddExpression(Expression left, Expression right) {
        this.leftExpression = left;
        this.rightExpression = right;
    }
    
    @Override
    public int interpret(Map<String, Integer> context) {
        return leftExpression.interpret(context) + rightExpression.interpret(context);
    }
}

class SubtractExpression implements Expression {
    private Expression leftExpression;
    private Expression rightExpression;
    
    public SubtractExpression(Expression left, Expression right) {
        this.leftExpression = left;
        this.rightExpression = right;
    }
    
    @Override
    public int interpret(Map<String, Integer> context) {
        return leftExpression.interpret(context) - rightExpression.interpret(context);
    }
}

// -------- Iterator Pattern Implementation --------

// Element class
class Song {
    private String title;
    private String artist;
    
    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getArtist() {
        return artist;
    }
}

// Collection class with custom iterator
class Playlist implements Iterable<Song> {
    private List<Song> songs;
    
    public Playlist() {
        songs = new ArrayList<>();
    }
    
    public void add(Song song) {
        songs.add(song);
    }
    
    @Override
    public Iterator<Song> iterator() {
        return new PlaylistIterator();
    }
    
    // Custom Iterator
    private class PlaylistIterator implements Iterator<Song> {
        private int currentIndex = 0;
        
        @Override
        public boolean hasNext() {
            return currentIndex < songs.size();
        }
        
        @Override
        public Song next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return songs.get(currentIndex++);
        }
    }
}

// -------- Mediator Pattern Implementation --------

// Mediator interface
interface AirTrafficControlMediator {
    void sendMessage(String message, Flight flight);
    void registerFlight(Flight flight);
}

// Colleague class
class Flight {
    private String flightNumber;
    private AirTrafficControlMediator mediator;
    
    public Flight(String flightNumber, AirTrafficControlMediator mediator) {
        this.flightNumber = flightNumber;
        this.mediator = mediator;
    }
    
    public void send(String message) {
        System.out.println("  Flight " + flightNumber + " sends message: " + message);
        mediator.sendMessage(message, this);
    }
    
    public void receive(String message) {
        System.out.println("  Flight " + flightNumber + " receives message: " + message);
    }
    
    public String getFlightNumber() {
        return flightNumber;
    }
}

// Concrete Mediator
class AirTrafficControlTower implements AirTrafficControlMediator {
    private List<Flight> flights;
    
    public AirTrafficControlTower() {
        this.flights = new ArrayList<>();
    }
    
    @Override
    public void registerFlight(Flight flight) {
        flights.add(flight);
    }
    
    @Override
    public void sendMessage(String message, Flight sender) {
        for (Flight flight : flights) {
            // Don't send message back to sender
            if (flight != sender) {
                flight.receive("Flight " + sender.getFlightNumber() + ": " + message);
            }
        }
    }
}

// -------- Memento Pattern Implementation --------

// Memento class
class TextEditorMemento {
    private final String text;
    
    public TextEditorMemento(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
}

// Originator class
class TextEditor {
    private String text;
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
    
    public TextEditorMemento createSnapshot() {
        return new TextEditorMemento(text);
    }
    
    public void restoreFromSnapshot(TextEditorMemento memento) {
        this.text = memento.getText();
    }
}

// Caretaker class
class TextEditorHistory {
    private List<TextEditorMemento> mementos = new ArrayList<>();
    
    public void saveState(TextEditorMemento memento) {
        mementos.add(memento);
    }
    
    public TextEditorMemento getState(int index) {
        return mementos.get(index);
    }
}

// -------- Observer Pattern Implementation --------

// Observer interface
interface WeatherDisplay {
    void update(float temperature, float humidity, float pressure);
}

// Concrete Observers
class PhoneApp implements WeatherDisplay {
    @Override
    public void update(float temperature, float humidity, float pressure) {
        System.out.println("  Phone App display updated: " +
                         temperature + "°C, " +
                         humidity + "% humidity, " +
                         pressure + " hPa");
    }
}

class WebDisplay implements WeatherDisplay {
    @Override
    public void update(float temperature, float humidity, float pressure) {
        System.out.println("  Web Display updated: " +
                         temperature + "°C, " +
                         humidity + "% humidity, " +
                         pressure + " hPa");
    }
}

class DigitalSignage implements WeatherDisplay {
    @Override
    public void update(float temperature, float humidity, float pressure) {
        System.out.println("  Digital Signage updated: " +
                         temperature + "°C, " +
                         humidity + "% humidity, " +
                         pressure + " hPa");
    }
}

// Subject class
class WeatherStation {
    private List<WeatherDisplay> observers;
    private float temperature;
    private float humidity;
    private float pressure;
    
    public WeatherStation() {
        observers = new ArrayList<>();
    }
    
    public void addObserver(WeatherDisplay observer) {
        observers.add(observer);
    }
    
    public void removeObserver(WeatherDisplay observer) {
        observers.remove(observer);
    }
    
    public void setWeatherData(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }
    
    private void notifyObservers() {
        for (WeatherDisplay observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }
}

// -------- State Pattern Implementation --------

// State interface
interface VendingMachineState {
    void insertCoin(VendingMachine machine);
    void selectItem(VendingMachine machine);
    void dispense(VendingMachine machine);
}

// Concrete States
class NoCoinState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("  Coin inserted");
        machine.setState(new HasCoinState());
    }
    
    @Override
    public void selectItem(VendingMachine machine) {
        System.out.println("  Please insert a coin first");
    }
    
    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("  Please insert a coin first");
    }
    
    @Override
    public String toString() {
        return "Waiting for coin";
    }
}

class HasCoinState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("  Coin already inserted, please select an item");
    }
    
    @Override
    public void selectItem(VendingMachine machine) {
        System.out.println("  Item selected");
        machine.setState(new ItemSelectedState());
    }
    
    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("  Please select an item first");
    }
    
    @Override
    public String toString() {
        return "Has coin, waiting for selection";
    }
}

class ItemSelectedState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("  Coin already inserted, please wait");
    }
    
    @Override
    public void selectItem(VendingMachine machine) {
        System.out.println("  Item already selected, please wait");
    }
    
    @Override
    public void dispense(VendingMachine machine) {
        if (machine.getItemCount() > 0) {
            System.out.println("  Item dispensed");
            machine.decrementItemCount();
            machine.setState(new NoCoinState());
        } else {
            System.out.println("  Out of items");
            machine.setState(new OutOfStockState());
        }
    }
    
    @Override
    public String toString() {
        return "Item selected, ready to dispense";
    }
}

class OutOfStockState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("  Machine is out of stock, returning coin");
    }
    
    @Override
    public void selectItem(VendingMachine machine) {
        System.out.println("  Machine is out of stock");
    }
    
    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("  Machine is out of stock");
    }
    
    @Override
    public String toString() {
        return "Out of stock";
    }
}

// Context class
class VendingMachine {
    private VendingMachineState state;
    private int itemCount;
    
    public VendingMachine(int itemCount) {
        this.itemCount = itemCount;
        this.state = new NoCoinState();
    }
    
    public void setState(VendingMachineState state) {
        this.state = state;
        System.out.println("  Machine state changed to: " + state);
    }
    
    public void insertCoin() {
        state.insertCoin(this);
    }
    
    public void selectItem() {
        state.selectItem(this);
    }
    
    public void dispense() {
        state.dispense(this);
    }
    
    public int getItemCount() {
        return itemCount;
    }
    
    public void decrementItemCount() {
        if (itemCount > 0) {
            itemCount--;
        }
    }
}

// -------- Strategy Pattern Implementation --------

// Strategy interface
interface SortingStrategy {
    int[] sort(int[] data);
}

// Concrete Strategies
class BubbleSortStrategy implements SortingStrategy {
    @Override
    public int[] sort(int[] data) {
        System.out.println("  Using bubble sort strategy");
        int[] result = data.clone();
        int n = result.length;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (result[j] > result[j + 1]) {
                    // Swap elements
                    int temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                }
            }
        }
        
        return result;
    }
}

class QuickSortStrategy implements SortingStrategy {
    @Override
    public int[] sort(int[] data) {
        System.out.println("  Using quick sort strategy");
        int[] result = data.clone();
        quickSort(result, 0, result.length - 1);
        return result;
    }
    
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }
    
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                
                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        // Swap arr[i+1] and arr[high] (pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
}

class MergeSortStrategy implements SortingStrategy {
    @Override
    public int[] sort(int[] data) {
        System.out.println("  Using merge sort strategy");
        int[] result = data.clone();
        mergeSort(result, 0, result.length - 1);
        return result;
    }
    
    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int middle = left + (right - left) / 2;
            
            mergeSort(arr, left, middle);
            mergeSort(arr, middle + 1, right);
            
            merge(arr, left, middle, right);
        }
    }
    
    private void merge(int[] arr, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;
        
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        
        for (int i = 0; i < n1; ++i) {
            leftArray[i] = arr[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            rightArray[j] = arr[middle + 1 + j];
        }
        
        int i = 0, j = 0;
        int k = left;
        
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }
        
        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }
}

// Context class
class Sorter {
    private SortingStrategy strategy;
    
    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }
    
    public int[] sort(int[] data) {
        if (strategy == null) {
            throw new IllegalStateException("Sorting strategy not set");
        }
        return strategy.sort(data);
    }
}

// -------- Template Pattern Implementation --------

// Abstract template class
abstract class DataProcessor {
    // Template method - defines the algorithm skeleton
    public final void processData() {
        readData();
        parseData();
        processSpecificData();
        writeData();
        closeResources();
    }
    
    // Common steps for all data processors
    private void readData() {
        System.out.println("  Reading data from source");
    }
    
    private void parseData() {
        System.out.println("  Parsing data structure");
    }
    
    // Abstract step to be implemented by subclasses
    protected abstract void processSpecificData();
    
    private void writeData() {
        System.out.println("  Writing processed data");
    }
    
    private void closeResources() {
        System.out.println("  Closing resources");
    }
}

// Concrete implementations
class CSVDataProcessor extends DataProcessor {
    @Override
    protected void processSpecificData() {
        System.out.println("  Processing CSV data: splitting by commas and handling quoted values");
    }
}

class XMLDataProcessor extends DataProcessor {
    @Override
    protected void processSpecificData() {
        System.out.println("  Processing XML data: parsing hierarchical tag structure");
    }
}

class JSONDataProcessor extends DataProcessor {
    @Override
    protected void processSpecificData() {
        System.out.println("  Processing JSON data: parsing object and array structures");
    }
}

// -------- Visitor Pattern Implementation --------

// Element interface
interface ComputerPart {
    void accept(ComputerPartVisitor visitor);
}

// Concrete Elements
class Keyboard implements ComputerPart {
    @Override
    public void accept(ComputerPartVisitor visitor) {
        visitor.visit(this);
    }
}

class Monitor implements ComputerPart {
    @Override
    public void accept(ComputerPartVisitor visitor) {
        visitor.visit(this);
    }
}

class Mouse implements ComputerPart {
    @Override
    public void accept(ComputerPartVisitor visitor) {
        visitor.visit(this);
    }
}

// Composite element
class Computer implements ComputerPart {
    private List<ComputerPart> parts = new ArrayList<>();
    
    public void add(ComputerPart part) {
        parts.add(part);
    }
    
    @Override
    public void accept(ComputerPartVisitor visitor) {
        for (ComputerPart part : parts) {
            part.accept(visitor);
        }
        visitor.visit(this);
    }
}

// Visitor interface
interface ComputerPartVisitor {
    void visit(Computer computer);
    void visit(Mouse mouse);
    void visit(Keyboard keyboard);
    void visit(Monitor monitor);
}

// Concrete Visitors
class ComputerPartDisplayVisitor implements ComputerPartVisitor {
    @Override
    public void visit(Computer computer) {
        System.out.println("  Displaying Computer");
    }
    
    @Override
    public void visit(Mouse mouse) {
        System.out.println("  Displaying Mouse");
    }
    
    @Override
    public void visit(Keyboard keyboard) {
        System.out.println("  Displaying Keyboard");
    }
    
    @Override
    public void visit(Monitor monitor) {
        System.out.println("  Displaying Monitor");
    }
}

class ComputerPartPriceVisitor implements ComputerPartVisitor {
    @Override
    public void visit(Computer computer) {
        System.out.println("  Computer assembly cost: $150");
    }
    
    @Override
    public void visit(Mouse mouse) {
        System.out.println("  Mouse price: $25");
    }
    
    @Override
    public void visit(Keyboard keyboard) {
        System.out.println("  Keyboard price: $45");
    }
    
    @Override
    public void visit(Monitor monitor) {
        System.out.println("  Monitor price: $250");
    }
}
