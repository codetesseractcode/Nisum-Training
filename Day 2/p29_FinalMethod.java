/**
 * Program to demonstrate final methods in Java with multiple use cases
 * 
 * Final methods cannot be overridden by subclasses. They are used to:
 * 1. Prevent method overriding
 * 2. Ensure consistent behavior across subclasses
 * 3. Improve performance (compiler optimizations)
 * 4. Protect critical functionality
 */

public class p29_FinalMethod {
    public static void main(String[] args) {
        System.out.println("=== Final Method Demonstration ===\n");
        
        // 1. Security use case - prevent overriding of security methods
        System.out.println("1. Security Use Case:");
        
        // Create and use a payment processor
        PaymentProcessor standardProcessor = new PaymentProcessor();
        standardProcessor.processPayment("Visa", "1234-5678-9012-3456", 5000.0);
        
        // Create and use a custom processor that can't modify encryption/validation
        CustomPaymentProcessor customProcessor = new CustomPaymentProcessor();
        customProcessor.processPayment("MasterCard", "9876-5432-1098-7654", 8000.0);
        customProcessor.addSpecialDiscount(1000.0);
        
        // 2. API Contract use case - ensure consistent behavior
        System.out.println("\n2. API Contract Use Case:");
        
        ShapeRenderer rectangle = new RectangleRenderer(5, 10);
        ShapeRenderer circle = new CircleRenderer(7);
        
        rectangle.render();
        circle.render();
        
        // 3. Template Method Pattern use case
        System.out.println("\n3. Template Method Pattern Use Case:");
        
        DataProcessor textProcessor = new TextDataProcessor("data.txt");
        DataProcessor imageProcessor = new ImageDataProcessor("image.jpg");
        
        textProcessor.processData();
        imageProcessor.processData();
        
        // 4. Utility methods use case
        System.out.println("\n4. Utility Methods Use Case:");
        
        FileManager pdfManager = new PDFFileManager();
        FileManager docManager = new DocFileManager();
        
        pdfManager.openFile("report.pdf");
        docManager.openFile("document.doc");
        
        System.out.println("\n=== End of Final Method Demonstration ===");
    }
}

/**
 * Use Case 1: Security - Prevent Overriding of Security Critical Methods
 */
class PaymentProcessor {
    // Final method to ensure security features can't be overridden
    final void validateCard(String cardNumber) {
        System.out.println("Validating card: " + maskCardNumber(cardNumber));
        // Complex validation logic would go here
        System.out.println("Card validated successfully");
    }
    
    // Final method for encryption that shouldn't be modified
    final void encryptData(String data) {
        System.out.println("Encrypting sensitive data");
        // Encryption algorithm would be here
        System.out.println("Data encrypted securely");
    }
    
    // Non-final method that can be customized
    void processPayment(String cardType, String cardNumber, double amount) {
        System.out.println("Processing ₹" + amount + " payment with " + cardType);
        validateCard(cardNumber);
        encryptData(cardNumber);
        System.out.println("Payment successful");
    }
    
    // Helper method to mask card number
    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() < 10) return "Invalid card";
        return cardNumber.substring(0, 4) + "-XXXX-XXXX-" + 
               cardNumber.substring(cardNumber.length() - 4);
    }
}

// Subclass can't override final methods but can add functionality
class CustomPaymentProcessor extends PaymentProcessor {
    // Can't override validateCard or encryptData because they are final
    
    // Override non-final method
    @Override
    void processPayment(String cardType, String cardNumber, double amount) {
        System.out.println("Custom processor handling payment");
        // Still uses the secure methods from parent class
        super.processPayment(cardType, cardNumber, amount);
        System.out.println("Custom payment receipt generated");
    }
    
    // Add new functionality
    void addSpecialDiscount(double discount) {
        System.out.println("Applied special discount of ₹" + discount);
    }
}

/**
 * Use Case 2: API Contract - Ensuring Consistent Behavior
 */
abstract class ShapeRenderer {
    // Common rendering setup that should be consistent for all shapes
    final void prepareRendering() {
        System.out.println("Initializing renderer");
        System.out.println("Setting up graphics context");
        System.out.println("Preparing canvas");
    }
    
    // Common cleanup that should be consistent for all shapes
    final void finishRendering() {
        System.out.println("Cleaning up resources");
        System.out.println("Refreshing display");
        System.out.println("Rendering complete");
    }
    
    // This method can be overridden to render specific shapes
    abstract void drawShape();
    
    // Template method that provides the algorithm structure
    public void render() {
        prepareRendering();
        drawShape();
        finishRendering();
    }
}

class RectangleRenderer extends ShapeRenderer {
    private int width;
    private int height;
    
    public RectangleRenderer(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    void drawShape() {
        System.out.println("Drawing rectangle with width " + width + " and height " + height);
    }
}

class CircleRenderer extends ShapeRenderer {
    private int radius;
    
    public CircleRenderer(int radius) {
        this.radius = radius;
    }
    
    @Override
    void drawShape() {
        System.out.println("Drawing circle with radius " + radius);
    }
}

/**
 * Use Case 3: Template Method Pattern with Final Methods
 */
abstract class DataProcessor {
    private String filename;
    
    public DataProcessor(String filename) {
        this.filename = filename;
    }
    
    // Final methods for steps that shouldn't be altered
    final void loadData() {
        System.out.println("Loading data from " + filename);
    }
    
    final void validateData() {
        System.out.println("Validating data integrity");
    }
    
    final void saveResults() {
        System.out.println("Saving processed results");
    }
    
    // Abstract method that subclasses must implement
    abstract void transform();
    
    // Template method defining the algorithm structure
    public final void processData() {
        System.out.println("Starting data processing for " + filename);
        loadData();
        validateData();
        transform();
        saveResults();
        System.out.println("Data processing complete");
    }
}

class TextDataProcessor extends DataProcessor {
    public TextDataProcessor(String filename) {
        super(filename);
    }
    
    @Override
    void transform() {
        System.out.println("Transforming text data: parsing, extracting keywords, analyzing sentiments");
    }
}

class ImageDataProcessor extends DataProcessor {
    public ImageDataProcessor(String filename) {
        super(filename);
    }
    
    @Override
    void transform() {
        System.out.println("Transforming image data: resizing, applying filters, extracting features");
    }
}

/**
 * Use Case 4: Utility Methods with Final Methods
 */
abstract class FileManager {
    // Utility method that should work consistently
    final String getFileExtension(String filename) {
        if (filename == null) return "";
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1) return "";
        return filename.substring(dotIndex + 1);
    }
    
    // Utility method to check file existence
    final void checkFileExists(String filename) {
        System.out.println("Checking if file exists: " + filename);
        // Logic to check file existence would be here
        System.out.println("File exists: " + filename);
    }
    
    // Method that can be customized
    abstract void processFileContent();
    
    // Template method for file operations
    public void openFile(String filename) {
        System.out.println("Opening file: " + filename);
        String extension = getFileExtension(filename);
        System.out.println("File extension: " + extension);
        checkFileExists(filename);
        processFileContent();
        System.out.println("File processed successfully");
    }
}

class PDFFileManager extends FileManager {
    @Override
    void processFileContent() {
        System.out.println("Processing PDF content: extracting text, parsing forms, reading metadata");
    }
}

class DocFileManager extends FileManager {
    @Override
    void processFileContent() {
        System.out.println("Processing DOC content: extracting text, processing styles, reading document properties");
    }
}
