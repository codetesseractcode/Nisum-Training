/**
 * Program to get the character at the given index within a String
 * 
 * This program demonstrates:
 * 1. Using charAt() method to get a character at a specific index
 * 2. Handling exceptions for invalid indices
 * 3. Iterating through a string using charAt()
 */

import java.util.Scanner;

public class p36_StringCharAt {
    public static void main(String[] args) {
        System.out.println("=== Get Character at Given Index in String ===\n");
        
        // Example 1: Basic charAt() usage
        String message = "Welcome to India";
        
        System.out.println("String: \"" + message + "\"");
        System.out.println("Length: " + message.length());
        
        // Get characters at specific indexes
        System.out.println("\n1. Getting characters at specific indexes:");
        System.out.println("Character at index 0: " + message.charAt(0));
        System.out.println("Character at index 4: " + message.charAt(4));
        System.out.println("Character at index 8: " + message.charAt(8));
        System.out.println("Character at index 11: " + message.charAt(11));
        
        // Example 2: Using charAt() with user input
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n2. Get character at user-specified index:");
        System.out.print("Enter a string: ");
        String userInput = scanner.nextLine();
        
        System.out.print("Enter an index (0 to " + (userInput.length() - 1) + "): ");
        
        try {
            int index = scanner.nextInt();
            
            if (index >= 0 && index < userInput.length()) {
                char character = userInput.charAt(index);
                System.out.println("Character at index " + index + ": " + character);
            } else {
                System.out.println("Index out of bounds! Valid range is 0 to " + (userInput.length() - 1));
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a valid number.");
        }
        
        // Example 3: Iterating through a string using charAt()
        System.out.println("\n3. Iterating through a string using charAt():");
        String state = "Maharashtra";
        System.out.println("String: \"" + state + "\"");
        
        System.out.println("Characters in the string:");
        for (int i = 0; i < state.length(); i++) {
            char c = state.charAt(i);
            System.out.println("Index " + i + ": " + c);
        }
        
        // Example 4: Finding the first occurrence of a character
        System.out.println("\n4. Finding the first occurrence of a character:");
        String text = "Bangalore is the Silicon Valley of India";
        System.out.println("String: \"" + text + "\"");
        
        char searchChar = 'i';
        int firstOccurrence = -1;
        
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == searchChar) {
                firstOccurrence = i;
                break;
            }
        }
        
        if (firstOccurrence != -1) {
            System.out.println("First occurrence of '" + searchChar + "' is at index: " + firstOccurrence);
        } else {
            System.out.println("Character '" + searchChar + "' not found in the string.");
        }
        
        // Example 5: Count occurrences of a character
        System.out.println("\n5. Count occurrences of a character:");
        char countChar = 'a';
        int count = 0;
        
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == countChar) {
                count++;
            }
        }
        
        System.out.println("Character '" + countChar + "' appears " + count + " times in the string.");
        
        // Example 6: Exception handling with charAt()
        System.out.println("\n6. Exception handling with charAt():");
        String shortString = "Java";
        
        System.out.println("String: \"" + shortString + "\"");
        System.out.println("Length: " + shortString.length());
        
        try {
            // This will throw an exception
            char invalidChar = shortString.charAt(10);
            System.out.println("Character at index 10: " + invalidChar);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Exception caught: " + e.getMessage());
            System.out.println("Valid indexes for this string are 0 to " + (shortString.length() - 1));
        }
        
        System.out.println("\n=== End of String charAt() Demonstration ===");
    }
}
