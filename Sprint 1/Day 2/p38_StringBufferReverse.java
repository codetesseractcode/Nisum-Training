/**
 * Program to print a given string in reverse order using StringBuffer methods
 * 
 * This program demonstrates:
 * 1. Using StringBuffer's reverse() method
 * 2. Comparing with manual reversal approaches
 * 3. Reversing individual words while preserving word order
 * 4. Checking for palindromes
 */

import java.util.Scanner;

public class p38_StringBufferReverse {
    public static void main(String[] args) {
        System.out.println("=== String Reversal using StringBuffer ===\n");
        
        // Example 1: Basic string reversal using StringBuffer
        System.out.println("1. Basic string reversal using StringBuffer:");
        String original = "Java Programming";
        
        StringBuffer buffer = new StringBuffer(original);
        buffer.reverse();
        String reversed = buffer.toString();
        
        System.out.println("Original string: " + original);
        System.out.println("Reversed string: " + reversed);
        System.out.println();
        
        // Example 2: User input string reversal
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("2. Reverse your own string:");
        System.out.print("Enter a string to reverse: ");
        String userInput = scanner.nextLine();
        
        StringBuffer userBuffer = new StringBuffer(userInput);
        String userReversed = userBuffer.reverse().toString();
        
        System.out.println("Original: " + userInput);
        System.out.println("Reversed: " + userReversed);
        System.out.println();
        
        // Example 3: Comparing with manual reversal (without StringBuffer)
        System.out.println("3. Comparing with manual reversal approach:");
        String text = "Hello from India";
        
        // Using StringBuffer
        long startTime = System.nanoTime();
        String reverseWithBuffer = new StringBuffer(text).reverse().toString();
        long endTimeBuffer = System.nanoTime();
        
        // Manual reversal
        startTime = System.nanoTime();
        String manualReverse = manuallyReverseString(text);
        long endTimeManual = System.nanoTime();
        
        System.out.println("Original text: " + text);
        System.out.println("Using StringBuffer: " + reverseWithBuffer);
        System.out.println("Manual reversal:   " + manualReverse);
        System.out.println();
        
        // Example 4: Reversing individual words while keeping word order
        System.out.println("4. Reversing individual words but keeping word order:");
        String sentence = "India is my country";
        
        String[] words = sentence.split(" ");
        StringBuilder resultBuilder = new StringBuilder();
        
        for (String word : words) {
            StringBuffer wordBuffer = new StringBuffer(word);
            resultBuilder.append(wordBuffer.reverse()).append(" ");
        }
        
        String reversedWords = resultBuilder.toString().trim();
        
        System.out.println("Original sentence: " + sentence);
        System.out.println("Words reversed:    " + reversedWords);
        System.out.println();
        
        // Example 5: Checking for palindromes using StringBuffer
        System.out.println("5. Checking for palindromes using StringBuffer:");
        
        checkPalindrome("radar");
        checkPalindrome("level");
        checkPalindrome("hello");
        checkPalindrome("Java");
        checkPalindrome("madam");
        System.out.println();
        
        // Example 6: Working with longer text using StringBuffer
        System.out.println("6. Reversing a longer text:");
        
        String longText = "The quick brown fox jumps over the lazy dog near the river bank";
        
        StringBuffer longBuffer = new StringBuffer(longText);
        String reversedLongText = longBuffer.reverse().toString();
        
        System.out.println("Original: " + longText);
        System.out.println("Reversed: " + reversedLongText);
        
        System.out.println("\n=== End of String Reversal Demonstration ===");
    }
    
    // Method to manually reverse a string (for comparison)
    private static String manuallyReverseString(String input) {
        char[] charArray = input.toCharArray();
        int left = 0;
        int right = charArray.length - 1;
        
        while (left < right) {
            // Swap characters
            char temp = charArray[left];
            charArray[left] = charArray[right];
            charArray[right] = temp;
            
            // Move pointers
            left++;
            right--;
        }
        
        return new String(charArray);
    }
    
    // Method to check if a string is a palindrome using StringBuffer
    private static void checkPalindrome(String word) {
        StringBuffer buffer = new StringBuffer(word);
        String reversed = buffer.reverse().toString();
        
        boolean isPalindrome = word.equalsIgnoreCase(reversed);
        
        System.out.println("\"" + word + "\" is" + (isPalindrome ? "" : " not") + " a palindrome");
    }
}
