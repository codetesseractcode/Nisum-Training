import java.io.*;
import java.nio.file.*;
import java.util.function.*;

/**
 * Question 4: Wrapping checked exceptions into runtime exceptions
 */

/**
 * Legacy API simulation that throws checked exceptions
 */
class LegacyFileAPI {
    
    public static String readFile(String filename) throws IOException {
        return Files.readString(Paths.get(filename));
    }
    
    public static void writeFile(String filename, String content) throws IOException {
        Files.writeString(Paths.get(filename), content);
    }
    
    public static void copyFile(String source, String destination) throws IOException {
        Files.copy(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
    }
    
    public static boolean fileExists(String filename) throws IOException {
        // Simulate an API that might throw IOException for existence check
        Path path = Paths.get(filename);
        if (!Files.isReadable(path.getParent())) {
            throw new IOException("Cannot access parent directory of: " + filename);
        }
        return Files.exists(path);
    }
    
    public static long getFileSize(String filename) throws IOException {
        return Files.size(Paths.get(filename));
    }
}

/**
 * Utility class for wrapping checked exceptions into runtime exceptions
 */
public class Question4_UncheckedIOException {
    
    /**
     * Functional interface for operations that might throw IOException
     */
    @FunctionalInterface
    public interface IOOperation<T> {
        T execute() throws IOException;
    }
    
    /**
     * Functional interface for operations that don't return a value
     */
    @FunctionalInterface
    public interface IOAction {
        void execute() throws IOException;
    }
    
    /**
     * Wraps an IO operation and converts checked IOException to UncheckedIOException
     * @param operation The operation to execute
     * @return The result of the operation
     * @throws UncheckedIOException if IOException occurs
     */
    public static <T> T wrapIO(IOOperation<T> operation) {
        try {
            return operation.execute();
        } catch (IOException e) {
            throw new UncheckedIOException("IO operation failed", e);
        }
    }
    
    /**
     * Wraps an IO action and converts checked IOException to UncheckedIOException
     * @param action The action to execute
     * @throws UncheckedIOException if IOException occurs
     */
    public static void wrapIO(IOAction action) {
        try {
            action.execute();
        } catch (IOException e) {
            throw new UncheckedIOException("IO action failed", e);
        }
    }
    
    /**
     * Wraps an operation with custom error message
     */
    public static <T> T wrapIO(IOOperation<T> operation, String errorMessage) {
        try {
            return operation.execute();
        } catch (IOException e) {
            throw new UncheckedIOException(errorMessage + ": " + e.getMessage(), e);
        }
    }
    
    /**
     * Wraps an action with custom error message
     */
    public static void wrapIO(IOAction action, String errorMessage) {
        try {
            action.execute();
        } catch (IOException e) {
            throw new UncheckedIOException(errorMessage + ": " + e.getMessage(), e);
        }
    }
    
    /**
     * Creates a supplier that wraps IO operations
     */
    public static <T> Supplier<T> ioSupplier(IOOperation<T> operation) {
        return () -> wrapIO(operation);
    }
    
    /**
     * Creates a runnable that wraps IO actions
     */
    public static Runnable ioRunnable(IOAction action) {
        return () -> wrapIO(action);
    }
    
    // Utility methods for common file operations
    
    public static String readFileUnchecked(String filename) {
        return wrapIO(() -> LegacyFileAPI.readFile(filename), 
                     "Failed to read file: " + filename);
    }
    
    public static void writeFileUnchecked(String filename, String content) {
        wrapIO(() -> LegacyFileAPI.writeFile(filename, content),
               "Failed to write file: " + filename);
    }
    
    public static void copyFileUnchecked(String source, String destination) {
        wrapIO(() -> LegacyFileAPI.copyFile(source, destination),
               "Failed to copy file from " + source + " to " + destination);
    }
    
    public static boolean fileExistsUnchecked(String filename) {
        return wrapIO(() -> LegacyFileAPI.fileExists(filename),
                     "Failed to check file existence: " + filename);
    }
    
    public static long getFileSizeUnchecked(String filename) {
        return wrapIO(() -> LegacyFileAPI.getFileSize(filename),
                     "Failed to get file size: " + filename);
    }
    
    /**
     * Demonstrates functional programming with wrapped IO operations
     */
    public static void demonstrateFunctionalUsage() {
        System.out.println("\n=== Functional Programming with Wrapped IO ===");
        
        String[] files = {"test1.txt", "test2.txt", "nonexistent.txt"};
        
        // Using streams with wrapped operations
        java.util.Arrays.stream(files)
            .filter(filename -> {
                try {
                    return fileExistsUnchecked(filename);
                } catch (UncheckedIOException e) {
                    System.err.printf("Cannot check existence of %s: %s%n", 
                                    filename, e.getCause().getMessage());
                    return false;
                }
            })
            .forEach(filename -> {
                try {
                    long size = getFileSizeUnchecked(filename);
                    System.out.printf("File %s exists and is %d bytes%n", filename, size);
                } catch (UncheckedIOException e) {
                    System.err.printf("Cannot get size of %s: %s%n", 
                                    filename, e.getCause().getMessage());
                }
            });
        
        // Using method references with wrapped operations
        Supplier<String> contentReader = ioSupplier(() -> LegacyFileAPI.readFile("test1.txt"));
        Runnable fileWriter = ioRunnable(() -> LegacyFileAPI.writeFile("output.txt", "Hello World"));
        
        try {
            String content = contentReader.get();
            System.out.println("Content read successfully: " + content.length() + " characters");
        } catch (UncheckedIOException e) {
            System.err.println("Failed to read: " + e.getCause().getMessage());
        }
        
        try {
            fileWriter.run();
            System.out.println("File written successfully");
        } catch (UncheckedIOException e) {
            System.err.println("Failed to write: " + e.getCause().getMessage());
        }
    }
    
    /**
     * Creates test files for demonstration
     */
    public static void setupTestFiles() {
        try {
            writeFileUnchecked("test1.txt", "This is test file 1 content.\nLine 2 of test file 1.");
            writeFileUnchecked("test2.txt", "Test file 2 has different content.\nMultiple lines here too.");
            System.out.println("Test files created successfully");
        } catch (UncheckedIOException e) {
            System.err.println("Failed to create test files: " + e.getCause().getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== UncheckedIOException Wrapper Utility ===");
        
        // Setup test environment
        setupTestFiles();
        
        System.out.println("\n=== Basic Wrapper Usage ===");
        
        // Demonstrate wrapped operations
        String[] testFiles = {"test1.txt", "test2.txt", "nonexistent.txt"};
        
        for (String filename : testFiles) {
            try {
                // Check if file exists
                boolean exists = fileExistsUnchecked(filename);
                System.out.printf("File %s exists: %s%n", filename, exists);
                
                if (exists) {
                    // Get file size
                    long size = getFileSizeUnchecked(filename);
                    System.out.printf("File %s size: %d bytes%n", filename, size);
                    
                    // Read file content
                    String content = readFileUnchecked(filename);
                    System.out.printf("File %s content preview: %.50s...%n", 
                                    filename, content.replace("\n", "\\n"));
                }
                
            } catch (UncheckedIOException e) {
                System.err.printf("Error processing %s: %s%n", 
                                filename, e.getCause().getMessage());
                
                // Demonstrate access to original IOException
                IOException originalException = e.getCause();
                System.err.printf("Original exception type: %s%n", 
                                originalException.getClass().getSimpleName());
            }
        }
        
        System.out.println("\n=== File Operations ===");
        
        try {
            // Copy operation
            copyFileUnchecked("test1.txt", "test1_copy.txt");
            System.out.println("File copied successfully");
            
            // Modify copied file
            String originalContent = readFileUnchecked("test1_copy.txt");
            String modifiedContent = originalContent + "\n\nThis line was added after copying.";
            writeFileUnchecked("test1_copy.txt", modifiedContent);
            System.out.println("File modified successfully");
            
            // Verify modification
            long originalSize = getFileSizeUnchecked("test1.txt");
            long copiedSize = getFileSizeUnchecked("test1_copy.txt");
            System.out.printf("Original size: %d, Modified copy size: %d%n", originalSize, copiedSize);
            
        } catch (UncheckedIOException e) {
            System.err.println("File operation failed: " + e.getCause().getMessage());
        }
        
        // Demonstrate functional programming usage
        demonstrateFunctionalUsage();
        
        System.out.println("\n=== Benefits of UncheckedIOException Wrapper ===");
        System.out.println("✓ No need to catch IOException in every method call");
        System.out.println("✓ Compatible with functional programming (streams, lambdas)");
        System.out.println("✓ Preserves original exception information");
        System.out.println("✓ Cleaner, more readable code");
        System.out.println("✓ Easy integration with existing functional interfaces");
        
        // Cleanup demonstration
        System.out.println("\n=== Cleanup ===");
        String[] createdFiles = {"test1.txt", "test2.txt", "test1_copy.txt", "output.txt"};
        for (String file : createdFiles) {
            try {
                Files.deleteIfExists(Paths.get(file));
                System.out.printf("Cleaned up: %s%n", file);
            } catch (IOException e) {
                System.err.printf("Could not clean up %s: %s%n", file, e.getMessage());
            }
        }
    }
}
