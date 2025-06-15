import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/**
 * Question 5: Multiple Exception Handling with Single Catch Block
 */

public class Question5_MultipleExceptionHandling {
    
    /**
     * Reads integers from a file and returns them as a list
     * Demonstrates multiple exception handling in a single catch block
     */
    public static List<Integer> readIntegersFromFile(String filename) {
        List<Integer> numbers = new ArrayList<>();
        int lineNumber = 0;
        
        System.out.printf("Reading integers from file: %s%n", filename);
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                
                // Skip empty lines and comments
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                
                try {
                    int number = Integer.parseInt(line);
                    numbers.add(number);
                    System.out.printf("  Line %d: Successfully parsed %d%n", lineNumber, number);
                    
                } catch (NumberFormatException e) {
                    System.err.printf("  Line %d: Invalid number format '%s' - %s%n", 
                                    lineNumber, line, e.getMessage());
                    // Continue processing other lines
                }
            }
            
        } catch (NoSuchFileException | FileNotFoundException e) {
            System.err.printf("File not found: %s%n", e.getMessage());
            throw new RuntimeException("Cannot process non-existent file: " + filename, e);
            
        } catch (IOException | SecurityException e) {
            // Multiple exception types in single catch block
            System.err.printf("File access error: %s%n", e.getMessage());
            throw new RuntimeException("Cannot access file: " + filename, e);
        }
        
        System.out.printf("Successfully read %d integers from %s%n", numbers.size(), filename);
        return numbers;
    }
    
    /**
     * Advanced file processing with comprehensive exception handling
     */
    public static Map<String, Object> processNumberFile(String filename) {
        Map<String, Object> result = new HashMap<>();
        List<Integer> validNumbers = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        int totalLines = 0;
        int validLines = 0;
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename));
             Scanner scanner = new Scanner(reader)) {
            
            while (scanner.hasNextLine()) {
                totalLines++;
                String line = scanner.nextLine().trim();
                
                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Skip empty lines and comments
                }
                
                try {
                    // Try parsing as integer
                    int number = Integer.parseInt(line);
                    validNumbers.add(number);
                    validLines++;
                    
                } catch (NumberFormatException e) {
                    errors.add(String.format("Line %d: '%s' - %s", 
                             totalLines, line, e.getMessage()));
                }
            }
            
        } catch (IOException | SecurityException | IllegalArgumentException e) {
            // Handle multiple file-related exceptions
            String errorMsg = String.format("File processing failed: %s", e.getMessage());
            errors.add(errorMsg);
            System.err.println(errorMsg);
            
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            String errorMsg = String.format("Unexpected error: %s", e.getMessage());
            errors.add(errorMsg);
            System.err.println(errorMsg);
        }
        
        // Populate result map
        result.put("filename", filename);
        result.put("totalLines", totalLines);
        result.put("validLines", validLines);
        result.put("validNumbers", validNumbers);
        result.put("errors", errors);
        result.put("hasErrors", !errors.isEmpty());
        
        // Calculate statistics if we have valid numbers
        if (!validNumbers.isEmpty()) {
            IntSummaryStatistics stats = validNumbers.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics();
                
            result.put("sum", stats.getSum());
            result.put("average", stats.getAverage());
            result.put("min", stats.getMin());
            result.put("max", stats.getMax());
            result.put("count", stats.getCount());
        }
        
        return result;
    }
    
    /**
     * Processes multiple files and aggregates results
     */
    public static void processMultipleFiles(String... filenames) {
        System.out.println("\n=== Processing Multiple Files ===");
        
        List<Map<String, Object>> allResults = new ArrayList<>();
        
        for (String filename : filenames) {
            try {
                System.out.printf("%nProcessing: %s%n", filename);
                Map<String, Object> result = processNumberFile(filename);
                allResults.add(result);
                
                // Display results for this file
                displayFileResults(result);
                
            } catch (Exception e) {
                System.err.printf("Failed to process %s: %s%n", filename, e.getMessage());
                
                // Create error result
                Map<String, Object> errorResult = new HashMap<>();
                errorResult.put("filename", filename);
                errorResult.put("hasErrors", true);
                errorResult.put("errors", Arrays.asList("Processing failed: " + e.getMessage()));
                allResults.add(errorResult);
            }
        }
        
        // Display aggregate statistics
        displayAggregateResults(allResults);
    }
    
    /**
     * Displays results for a single file
     */
    private static void displayFileResults(Map<String, Object> result) {
        String filename = (String) result.get("filename");
        boolean hasErrors = (Boolean) result.get("hasErrors");
        
        System.out.printf("Results for %s:%n", filename);
        System.out.printf("  Total lines: %s%n", result.get("totalLines"));
        System.out.printf("  Valid lines: %s%n", result.get("validLines"));
        
        if (result.containsKey("count")) {
            System.out.printf("  Numbers found: %s%n", result.get("count"));
            System.out.printf("  Sum: %s%n", result.get("sum"));
            System.out.printf("  Average: %.2f%n", (Double) result.get("average"));
            System.out.printf("  Range: %s to %s%n", result.get("min"), result.get("max"));
        }
        
        if (hasErrors) {
            @SuppressWarnings("unchecked")
            List<String> errors = (List<String>) result.get("errors");
            System.out.printf("  Errors (%d):%n", errors.size());
            errors.forEach(error -> System.out.printf("    - %s%n", error));
        }
    }
    
    /**
     * Displays aggregate results across all files
     */
    private static void displayAggregateResults(List<Map<String, Object>> allResults) {
        System.out.println("\n=== Aggregate Results ===");
        
        int totalFilesProcessed = allResults.size();
        long filesWithErrors = allResults.stream()
            .mapToLong(result -> (Boolean) result.get("hasErrors") ? 1 : 0)
            .sum();
        
        List<Integer> allNumbers = allResults.stream()
            .filter(result -> result.containsKey("validNumbers"))
            .flatMap(result -> {
                @SuppressWarnings("unchecked")
                List<Integer> numbers = (List<Integer>) result.get("validNumbers");
                return numbers.stream();
            })
            .collect(Collectors.toList());
        
        System.out.printf("Files processed: %d%n", totalFilesProcessed);
        System.out.printf("Files with errors: %d%n", filesWithErrors);
        System.out.printf("Total valid numbers: %d%n", allNumbers.size());
        
        if (!allNumbers.isEmpty()) {
            IntSummaryStatistics overallStats = allNumbers.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics();
                
            System.out.printf("Overall sum: %d%n", overallStats.getSum());
            System.out.printf("Overall average: %.2f%n", overallStats.getAverage());
            System.out.printf("Overall range: %d to %d%n", 
                            overallStats.getMin(), overallStats.getMax());
        }
    }
    
    /**
     * Creates test files with various content scenarios
     */
    public static void createTestFiles() {
        try {
            // File 1: Valid numbers with some invalid entries
            Files.writeString(Paths.get("numbers1.txt"), 
                "# This file contains numbers\n" +
                "10\n" +
                "20\n" +
                "abc\n" +  // Invalid
                "30\n" +
                "\n" +     // Empty line
                "40\n" +
                "not_a_number\n" +  // Invalid
                "50\n");
            
            // File 2: Mix of valid and invalid data
            Files.writeString(Paths.get("numbers2.txt"),
                "100\n" +
                "200.5\n" +  // Invalid (decimal)
                "300\n" +
                "# Comment line\n" +
                "400\n" +
                "500 extra text\n" +  // Invalid
                "600\n");
            
            // File 3: All valid numbers
            Files.writeString(Paths.get("numbers3.txt"),
                "1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n");
            
            System.out.println("Test files created successfully");
            
        } catch (IOException e) {
            System.err.println("Failed to create test files: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Multiple Exception Handling Demonstration ===");
        
        // Create test files
        createTestFiles();
        
        System.out.println("\n=== Single File Processing ===");
        
        // Test with individual files
        String[] testFiles = {"numbers1.txt", "numbers2.txt", "numbers3.txt", "nonexistent.txt"};
        
        for (String filename : testFiles) {
            try {
                List<Integer> numbers = readIntegersFromFile(filename);
                System.out.printf("Successfully processed %s: %s%n", filename, numbers);
                
            } catch (RuntimeException e) {
                System.err.printf("Failed to process %s: %s%n", filename, e.getMessage());
                
                // Examine the cause chain
                Throwable cause = e.getCause();
                if (cause != null) {
                    System.err.printf("  Caused by: %s - %s%n", 
                                    cause.getClass().getSimpleName(), cause.getMessage());
                }
            }
            System.out.println(); // Add spacing
        }
        
        // Test advanced processing
        processMultipleFiles("numbers1.txt", "numbers2.txt", "numbers3.txt", "nonexistent.txt");
        
        System.out.println("\n=== Exception Handling Benefits ===");
        System.out.println("✓ Single catch block for related exceptions");
        System.out.println("✓ Reduced code duplication");
        System.out.println("✓ Cleaner exception handling logic");
        System.out.println("✓ Comprehensive error reporting");
        System.out.println("✓ Graceful degradation on errors");
        
        // Cleanup
        System.out.println("\n=== Cleanup ===");
        String[] filesToClean = {"numbers1.txt", "numbers2.txt", "numbers3.txt"};
        for (String file : filesToClean) {
            try {
                Files.deleteIfExists(Paths.get(file));
                System.out.printf("Cleaned up: %s%n", file);
            } catch (IOException e) {
                System.err.printf("Could not clean up %s: %s%n", file, e.getMessage());
            }
        }
    }
}
