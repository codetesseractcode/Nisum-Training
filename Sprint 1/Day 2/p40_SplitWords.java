/**
 * A simple Java program to split words from a given sentence
 * 
 * This program demonstrates:
 * 1. Splitting a sentence into words using String.split() method
 * 2. Splitting with different delimiters
 * 3. Processing the extracted words
 * 4. Handling special characters and multiple spaces
 */

import java.util.Arrays;
import java.util.Scanner;

public class p40_SplitWords {
    public static void main(String[] args) {
        System.out.println("=== Splitting Words from a Sentence ===\n");
        
        // Example 1: Basic sentence splitting
        System.out.println("1. Basic sentence splitting:");
        String sentence = "India is a diverse country with rich culture";
        
        // Split the sentence by space
        String[] words = sentence.split(" ");
        
        System.out.println("Original sentence: " + sentence);
        System.out.println("Words in the sentence:");
        
        // Display each word with its index
        for (int i = 0; i < words.length; i++) {
            System.out.println("Word " + (i + 1) + ": " + words[i]);
        }
        
        // Display the total count of words
        System.out.println("Total words: " + words.length);
        System.out.println();
        
        // Example 2: User input splitting
        Scanner scanner = new Scanner(System.in);
        System.out.println("2. Split your own sentence:");
        System.out.print("Enter a sentence: ");
        String userSentence = scanner.nextLine();
        
        String[] userWords = userSentence.split(" ");
        
        System.out.println("Words in your sentence:");
        for (String word : userWords) {
            if (!word.isEmpty()) {
                System.out.println("- " + word);
            }
        }
        
        System.out.println("Total words: " + userWords.length);
        System.out.println();
        
        // Example 3: Splitting with different delimiters
        System.out.println("3. Splitting with different delimiters:");
        
        // Sentence with comma-separated values
        String csvText = "Delhi,Mumbai,Chennai,Kolkata,Bangalore,Hyderabad";
        String[] cities = csvText.split(",");
        
        System.out.println("CSV text: " + csvText);
        System.out.println("Cities list: " + Arrays.toString(cities));
        System.out.println("Number of cities: " + cities.length);
        System.out.println();
        
        // Sentence with multiple delimiters
        String mixedText = "apple;banana,orange.grape:mango";
        String[] fruits = mixedText.split("[;,.:]+");
        
        System.out.println("Mixed text: " + mixedText);
        System.out.println("Fruits list: " + Arrays.toString(fruits));
        System.out.println("Number of fruits: " + fruits.length);
        System.out.println();
        
        // Example 4: Handling special cases
        System.out.println("4. Handling special cases:");
        
        // Handling multiple spaces
        String textWithSpaces = "Java   programming    is    fun";
        
        // Using split with regular expression for any number of spaces
        String[] wordsWithSpaces = textWithSpaces.split("\\s+");
        
        System.out.println("Text with multiple spaces: " + textWithSpaces);
        System.out.println("Words (handling multiple spaces): " + Arrays.toString(wordsWithSpaces));
        System.out.println();
        
        // Handling punctuation
        String textWithPunctuation = "Hello, world! How are you? I'm doing great.";
        
        // Split by spaces and remove punctuation
        String[] wordsWithPunctuation = textWithPunctuation.split("\\s+");
        
        System.out.println("Text with punctuation: " + textWithPunctuation);
        System.out.println("Words with punctuation: " + Arrays.toString(wordsWithPunctuation));
        
        // Remove punctuation and then split
        String cleanText = textWithPunctuation.replaceAll("[^a-zA-Z\\s]", "");
        String[] cleanWords = cleanText.split("\\s+");
        
        System.out.println("Text without punctuation: " + cleanText);
        System.out.println("Clean words: " + Arrays.toString(cleanWords));
        System.out.println();
        
        // Example 5: Count occurrences of each word
        System.out.println("5. Count occurrences of each word:");
        
        String paragraph = "Java is a programming language. Java is widely used. " +
                           "Java is platform-independent. Programming in Java is fun.";
        
        System.out.println("Paragraph: " + paragraph);
        
        // Convert to lowercase and split
        String[] paragraphWords = paragraph.toLowerCase().split("[\\s.,]+");
        
        // Count word occurrences
        System.out.println("Word counts:");
        countWordOccurrences(paragraphWords);
        
        System.out.println("\n=== End of Word Splitting Demonstration ===");
    }
    
    // Method to count and print occurrences of each word
    private static void countWordOccurrences(String[] words) {
        // Create an array to track counted words
        boolean[] counted = new boolean[words.length];
        
        for (int i = 0; i < words.length; i++) {
            // Skip if this word was already counted
            if (counted[i]) {
                continue;
            }
            
            // Count occurrences of the word
            int count = 1;
            for (int j = i + 1; j < words.length; j++) {
                if (words[i].equals(words[j])) {
                    count++;
                    counted[j] = true;
                }
            }
            
            System.out.println("\"" + words[i] + "\": " + count + " time(s)");
        }
    }
}
