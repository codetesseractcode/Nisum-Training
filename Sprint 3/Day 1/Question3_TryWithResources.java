import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Question 3: Try-with-resources for automatic file handling
 */

class InvalidAgeException extends RuntimeException {
    public InvalidAgeException(String message) {
        super(message);
    }
}

public class Question3_TryWithResources {
    
    private static final String LOG_FILE = "age_validation_log.txt";
    private static final String RESULTS_FILE = "validation_results.txt";
    
    /**
     * Validates age and logs results to file using try-with-resources
     */
    public static void validateAgeWithLogging(int age, String context) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        
        // Try-with-resources for automatic file closure
        try (FileWriter logWriter = new FileWriter(LOG_FILE, true);
             PrintWriter logPrinter = new PrintWriter(logWriter);
             BufferedWriter resultWriter = Files.newBufferedWriter(
                 Paths.get(RESULTS_FILE), 
                 StandardOpenOption.CREATE, 
                 StandardOpenOption.APPEND)) {
            
            logPrinter.printf("[%s] Validating age: %d (Context: %s)%n", 
                            timestamp, age, context);
            
            if (age <= 0) {
                String errorMsg = String.format("Invalid age: %d. Age must be positive.", age);
                logPrinter.printf("[%s] ERROR: %s%n", timestamp, errorMsg);
                resultWriter.write(String.format("FAILED - Age: %d, Error: %s%n", age, errorMsg));
                throw new InvalidAgeException(errorMsg);
            }
            
            if (age > 150) {
                String errorMsg = String.format("Invalid age: %d. Age seems unrealistic.", age);
                logPrinter.printf("[%s] ERROR: %s%n", timestamp, errorMsg);
                resultWriter.write(String.format("FAILED - Age: %d, Error: %s%n", age, errorMsg));
                throw new InvalidAgeException(errorMsg);
            }
            
            String successMsg = String.format("Valid age: %d", age);
            logPrinter.printf("[%s] SUCCESS: %s%n", timestamp, successMsg);
            resultWriter.write(String.format("PASSED - Age: %d%n", age));
            System.out.printf("✓ %s%n", successMsg);
            
        } catch (IOException e) {
            System.err.printf("File I/O error during validation: %s%n", e.getMessage());
            // Still validate the age even if logging fails
            if (age <= 0 || age > 150) {
                throw new InvalidAgeException("Invalid age: " + age);
            }
        }
    }
    
    /**
     * Reads validation results from file using try-with-resources
     */
    public static void readValidationResults() {
        System.out.println("\n=== Reading Validation Results ===");
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(RESULTS_FILE))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                System.out.printf("%d: %s%n", lineNumber++, line);
            }
        } catch (NoSuchFileException e) {
            System.out.println("Results file not found. No previous validations recorded.");
        } catch (IOException e) {
            System.err.printf("Error reading results file: %s%n", e.getMessage());
        }
    }
    
    /**
     * Demonstrates multiple resources in try-with-resources
     */
    public static void processAgeDataFromFile(String inputFile, String outputFile) {
        System.out.printf("%n=== Processing age data from %s ====%n", inputFile);
        
        // Multiple resources in single try-with-resources statement
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile));
             PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(outputFile)));
             FileWriter logWriter = new FileWriter(LOG_FILE, true);
             PrintWriter logger = new PrintWriter(logWriter)) {
            
            String line;
            int processedCount = 0;
            int validCount = 0;
            int errorCount = 0;
            
            writer.println("=== Age Validation Report ===");
            writer.printf("Generated: %s%n%n", LocalDateTime.now());
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                
                try {
                    int age = Integer.parseInt(line);
                    processedCount++;
                    
                    if (age > 0 && age <= 150) {
                        validCount++;
                        writer.printf("✓ Age %d: VALID%n", age);
                        logger.printf("Processed age %d: VALID%n", age);
                    } else {
                        errorCount++;
                        writer.printf("✗ Age %d: INVALID (out of range)%n", age);
                        logger.printf("Processed age %d: INVALID%n", age);
                    }
                    
                } catch (NumberFormatException e) {
                    errorCount++;
                    writer.printf("✗ '%s': INVALID (not a number)%n", line);
                    logger.printf("Processed '%s': FORMAT ERROR%n", line);
                }
            }
            
            writer.printf("%n=== Summary ===%n");
            writer.printf("Total processed: %d%n", processedCount);
            writer.printf("Valid ages: %d%n", validCount);
            writer.printf("Invalid ages: %d%n", errorCount);
            
            System.out.printf("Processed %d entries (%d valid, %d invalid)%n", 
                            processedCount, validCount, errorCount);
            
        } catch (NoSuchFileException e) {
            System.err.printf("Input file '%s' not found%n", inputFile);
        } catch (IOException e) {
            System.err.printf("I/O error processing files: %s%n", e.getMessage());
        }
    }
    
    /**
     * Creates sample input file for demonstration
     */
    public static void createSampleInputFile() {
        try (PrintWriter writer = new PrintWriter("sample_ages.txt")) {
            writer.println("# Sample age data for validation");
            writer.println("25");
            writer.println("30");
            writer.println("-5");
            writer.println("0");
            writer.println("45");
            writer.println("abc");
            writer.println("200");
            writer.println("35");
            writer.println("");
            writer.println("150");
        } catch (IOException e) {
            System.err.println("Error creating sample file: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Try-with-Resources Demonstration ===");
        
        // Clear previous logs
        try {
            Files.deleteIfExists(Paths.get(LOG_FILE));
            Files.deleteIfExists(Paths.get(RESULTS_FILE));
        } catch (IOException e) {
            System.err.println("Could not clear previous logs: " + e.getMessage());
        }
        
        // Test age validation with logging
        int[] testAges = {25, -5, 0, 45, 200, 30, 150};
        
        for (int age : testAges) {
            try {
                validateAgeWithLogging(age, "Manual test");
            } catch (InvalidAgeException e) {
                System.err.printf("✗ Validation failed: %s%n", e.getMessage());
            }
        }
        
        // Read and display results
        readValidationResults();
        
        // Demonstrate file processing with multiple resources
        createSampleInputFile();
        processAgeDataFromFile("sample_ages.txt", "age_report.txt");
        
        System.out.println("\n=== Resource Management Benefits ===");
        System.out.println("✓ Files automatically closed even if exceptions occur");
        System.out.println("✓ No need for explicit finally blocks");
        System.out.println("✓ Cleaner, more readable code");
        System.out.println("✓ Guaranteed resource cleanup");
        
        // Demonstrate that resources are properly closed
        System.out.println("\n=== Files created during execution ===");
        String[] fileNames = {LOG_FILE, RESULTS_FILE, "age_report.txt", "sample_ages.txt"};
        for (String fileName : fileNames) {
            if (Files.exists(Paths.get(fileName))) {
                try {
                    long size = Files.size(Paths.get(fileName));
                    System.out.printf("- %s (%d bytes)%n", fileName, size);
                } catch (IOException e) {
                    System.out.printf("- %s (size unknown)%n", fileName);
                }
            }
        }
    }
}
