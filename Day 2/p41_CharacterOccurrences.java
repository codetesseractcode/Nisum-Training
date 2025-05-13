/**
 * Program to count the number of occurrences of each character in a string
 * 
 * This program counts how many times each character appears in a given string,
 * demonstrating character frequency analysis with multiple approaches.
 */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class p41_CharacterOccurrences {
    public static void main(String[] args) {
        System.out.println("=== Character Occurrence Counter ===\n");
        
        // Example string to analyze
        String input = "Super Man Bat Man Spider Man";
        
        System.out.println("Input string: \"" + input + "\"");
        System.out.println("Length: " + input.length() + " characters");
        
        // Method 1: Using HashMap (unordered)
        System.out.println("\n1. Count using HashMap (unordered):");
        countCharactersWithHashMap(input);
        
        // Method 2: Using LinkedHashMap (preserves insertion order)
        System.out.println("\n2. Count using LinkedHashMap (preserves order):");
        countCharactersWithLinkedHashMap(input);
        
        // Method 3: Using array (for ASCII characters only)
        System.out.println("\n3. Count using Character Array (ASCII only):");
        countCharactersWithArray(input);
        
        // Method 4: Using nested loops (more explicit)
        System.out.println("\n4. Count using Nested Loops:");
        countCharactersWithNestedLoops(input);
        
        // Additional examples with different inputs
        System.out.println("\n--- Additional Examples ---");
        
        // Example with special characters and numbers
        String mixedInput = "Hello World! 123 #@%";
        System.out.println("\nString with special characters: \"" + mixedInput + "\"");
        countCharactersWithLinkedHashMap(mixedInput);
        
        // Example with repeated characters
        String repeatedInput = "aaabbbccc";
        System.out.println("\nString with repeated characters: \"" + repeatedInput + "\"");
        countCharactersWithLinkedHashMap(repeatedInput);
        
        // Example with case sensitivity
        String caseSensitiveInput = "aAbBcC";
        System.out.println("\nCase-sensitive count: \"" + caseSensitiveInput + "\"");
        countCharactersWithLinkedHashMap(caseSensitiveInput);
        
        System.out.println("\nCase-insensitive count: \"" + caseSensitiveInput + "\"");
        countCharactersWithLinkedHashMapIgnoreCase(caseSensitiveInput);
        
        System.out.println("\n=== End of Character Occurrence Counter ===");
    }
    
    // Method 1: Count characters using HashMap (unordered)
    private static void countCharactersWithHashMap(String input) {
        // Create a HashMap to store character counts
        Map<Character, Integer> charCountMap = new HashMap<>();
        
        // Convert string to char array and count each character
        char[] chars = input.toCharArray();
        for (char c : chars) {
            // Skip counting spaces
            if (c == ' ') continue;
            
            // If character exists in map, increment count; otherwise add with count 1
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }
        
        // Display the character counts
        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            System.out.println("'" + entry.getKey() + "' occurs " + entry.getValue() + " time(s)");
        }
    }
    
    // Method 2: Count characters using LinkedHashMap (preserves insertion order)
    private static void countCharactersWithLinkedHashMap(String input) {
        // Create a LinkedHashMap to store character counts in order of first appearance
        Map<Character, Integer> charCountMap = new LinkedHashMap<>();
        
        // Convert string to char array and count each character
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            
            // Skip counting spaces
            if (c == ' ') continue;
            
            // If character exists in map, increment count; otherwise add with count 1
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }
        
        // Display the character counts
        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            System.out.println("'" + entry.getKey() + "' occurs " + entry.getValue() + " time(s)");
        }
    }
    
    // Method 3: Count characters using array (for ASCII characters only)
    private static void countCharactersWithArray(String input) {
        // Create an array to store counts (ASCII has 128 characters)
        int[] counts = new int[128];
        
        // Count occurrences of each character
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            
            // Skip counting spaces
            if (c == ' ') continue;
            
            counts[c]++;
        }
        
        // Display character counts (only for characters that appear)
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                System.out.println("'" + (char)i + "' occurs " + counts[i] + " time(s)");
            }
        }
    }
    
    // Method 4: Count characters using nested loops
    private static void countCharactersWithNestedLoops(String input) {
        // Convert to char array for processing
        char[] chars = input.toCharArray();
        boolean[] counted = new boolean[chars.length];
        
        for (int i = 0; i < chars.length; i++) {
            // Skip spaces and already counted characters
            if (counted[i] || chars[i] == ' ') continue;
            
            int count = 1; // Start with 1 for the current character
            
            // Count same characters in the rest of the array
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[i] == chars[j]) {
                    count++;
                    counted[j] = true; // Mark as counted
                }
            }
            
            System.out.println("'" + chars[i] + "' occurs " + count + " time(s)");
        }
    }
    
    // Method to count characters ignoring case
    private static void countCharactersWithLinkedHashMapIgnoreCase(String input) {
        // Convert input to lowercase for case-insensitive counting
        input = input.toLowerCase();
        Map<Character, Integer> charCountMap = new LinkedHashMap<>();
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            
            if (c == ' ') continue;
            
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }
        
        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            System.out.println("'" + entry.getKey() + "' occurs " + entry.getValue() + " time(s)");
        }
    }
}
