import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class q7_WordFrequencyCounter {
    private HashMap<String, Integer> wordCounts;
    
    public q7_WordFrequencyCounter() {
        wordCounts = new HashMap<>();
    }
    
    public void processText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return;
        }
        
        String cleanText = text.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");
        String[] words = cleanText.split("\\s+");
        
        for (String word : words) {
            if (!word.isEmpty()) {
                wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
            }
        }
    }
    
    public void displayWordFrequencies() {
        if (wordCounts.isEmpty()) {
            System.out.println("No words have been processed.");
            return;
        }
        
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordCounts.entrySet());
        
        Collections.sort(sortedEntries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                int frequencyComparison = e2.getValue().compareTo(e1.getValue());
                if (frequencyComparison != 0) {
                    return frequencyComparison;
                }
                return e1.getKey().compareTo(e2.getKey());
            }
        });
        
        System.out.println("Word Frequency (Highest First):");
        System.out.println("-----------------------------");
        System.out.printf("%-15s %s%n", "Word", "Count");
        System.out.println("-----------------------------");
        
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            System.out.printf("%-15s %d%n", entry.getKey(), entry.getValue());
        }
    }
    
    public HashMap<String, Integer> getWordCounts() {
        return new HashMap<>(wordCounts);
    }
    public static void main(String[] args) {
        q7_WordFrequencyCounter counter = new q7_WordFrequencyCounter();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Word Frequency Counter =====");
        
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Enter text to analyze");
            System.out.println("2. Display word frequencies");
            System.out.println("3. Clear current data");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");
            
            int choice = 0;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
            } catch (Exception e) {
                scanner.nextLine(); // Clear the buffer
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    System.out.println("Enter text to analyze (press Enter twice when finished):");
                    StringBuilder textBuilder = new StringBuilder();
                    String line;
                    
                    while (true) {
                        line = scanner.nextLine();
                        if (line.isEmpty()) {
                            break;
                        }
                        textBuilder.append(line).append(" ");
                    }
                    
                    String text = textBuilder.toString();
                    if (!text.trim().isEmpty()) {
                        counter.processText(text);
                        System.out.println("Text processed successfully.");
                    } else {
                        System.out.println("No text entered.");
                    }
                    break;
                    
                case 2:
                    counter.displayWordFrequencies();
                    break;
                    
                case 3:
                    counter = new q7_WordFrequencyCounter();
                    System.out.println("Data cleared.");
                    break;
                    
                case 4:
                    running = false;
                    System.out.println("Exiting Word Frequency Counter.");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}
