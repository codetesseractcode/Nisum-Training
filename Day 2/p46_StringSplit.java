/**
 * Program to demonstrate how to split a string with a delimiter
 * 
 * This program shows different ways to split strings into parts
 * based on various delimiters and approaches.
 */

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class p46_StringSplit {
    public static void main(String[] args) {
        System.out.println("=== Splitting Strings with Delimiters ===\n");
        
        // Test strings with different delimiters
        String csvText = "apple,orange,banana,grape";
        String spaceText = "This is a simple sentence";
        String multiDelimiter = "apple,orange;banana|grape";
        String repeatedDelimiters = "apple,,orange,,,banana,grape";
        
        // Method 1: Using String.split() method with simple delimiter
        System.out.println("Method 1: Using String.split() method");
        System.out.println("Original: \"" + csvText + "\"");
        
        String[] fruits = csvText.split(",");
        System.out.println("After splitting by comma:");
        displayArray(fruits);
        
        // Method 2: Splitting by whitespace
        System.out.println("\nMethod 2: Splitting by whitespace");
        System.out.println("Original: \"" + spaceText + "\"");
        
        String[] words = spaceText.split("\\s");
        System.out.println("After splitting by space:");
        displayArray(words);
        
        // Method 3: Limit parameter in split method
        System.out.println("\nMethod 3: Using limit parameter in split() method");
        System.out.println("Original: \"" + csvText + "\"");
        
        // Split with limit 2 (resulting array length will be 2)
        String[] limitedFruits = csvText.split(",", 2);
        System.out.println("After splitting by comma with limit=2:");
        displayArray(limitedFruits);
        
        // Method 4: Using StringTokenizer
        System.out.println("\nMethod 4: Using StringTokenizer");
        System.out.println("Original: \"" + csvText + "\"");
        
        System.out.println("After tokenizing by comma:");
        StringTokenizer tokenizer = new StringTokenizer(csvText, ",");
        while (tokenizer.hasMoreTokens()) {
            System.out.println("  " + tokenizer.nextToken());
        }
        
        // Method 5: Multiple delimiters using split
        System.out.println("\nMethod 5: Multiple delimiters using split with regex");
        System.out.println("Original: \"" + multiDelimiter + "\"");
        
        String[] multiDelimFruits = multiDelimiter.split("[,;|]");
        System.out.println("After splitting by comma, semicolon, or pipe:");
        displayArray(multiDelimFruits);
        
        // Method 6: Handling empty fields when delimiters are repeated
        System.out.println("\nMethod 6: Handling repeated delimiters");
        System.out.println("Original: \"" + repeatedDelimiters + "\"");
        
        // This will include empty strings in the result
        String[] withEmpty = repeatedDelimiters.split(",");
        System.out.println("Split with empty strings preserved:");
        displayArray(withEmpty);
        
        // Method 7: Filtering out empty results
        System.out.println("\nMethod 7: Filtering out empty strings");
        System.out.println("Original: \"" + repeatedDelimiters + "\"");
        
        // This will exclude empty strings
        String[] withoutEmpty = repeatedDelimiters.split(",");
        withoutEmpty = Arrays.stream(withoutEmpty)
                             .filter(s -> !s.isEmpty())
                             .toArray(String[]::new);
        System.out.println("Split with empty strings removed:");
        displayArray(withoutEmpty);
        
        // Method 8: Using Pattern.compile for more complex splitting
        System.out.println("\nMethod 8: Using Pattern.compile");
        System.out.println("Original: \"" + multiDelimiter + "\"");
        
        Pattern pattern = Pattern.compile("[,;|]");
        String[] patternSplit = pattern.split(multiDelimiter);
        System.out.println("After splitting with pattern:");
        displayArray(patternSplit);
        
        System.out.println("\n=== End of String Splitting ===");
    }
    
    /**
     * Helper method to display array contents
     */
    private static void displayArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println("  [" + i + "]: \"" + array[i] + "\"");
        }
    }
}
