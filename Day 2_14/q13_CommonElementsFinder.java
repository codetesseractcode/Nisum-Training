import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class q13_CommonElementsFinder {

    public static <T> List<T> findCommonElements(List<T> list1, List<T> list2) {
        List<T> commonElements = new ArrayList<>();
        HashSet<T> set = new HashSet<>(list1);
        
        for (T element : list2) {
            if (set.contains(element)) {
                commonElements.add(element);
            }
        }
        
        return commonElements;
    }    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Common Elements Finder =====");
        
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Find common elements between two custom lists");
            System.out.println("2. Use sample lists (Integer)");
            System.out.println("3. Use sample lists (String)");
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
                    System.out.print("Enter elements for the first list (separated by spaces): ");
                    String[] elements1 = scanner.nextLine().trim().split("\\s+");
                    
                    System.out.print("Enter elements for the second list (separated by spaces): ");
                    String[] elements2 = scanner.nextLine().trim().split("\\s+");
                    
                    // Convert arrays to lists
                    List<String> list1 = new ArrayList<>();
                    List<String> list2 = new ArrayList<>();
                    
                    for (String e : elements1) {
                        if (!e.isEmpty()) {
                            list1.add(e);
                        }
                    }
                    
                    for (String e : elements2) {
                        if (!e.isEmpty()) {
                            list2.add(e);
                        }
                    }
                    
                    List<String> commonElements = findCommonElements(list1, list2);
                    
                    System.out.println("\nList 1: " + list1);
                    System.out.println("List 2: " + list2);
                    System.out.println("Common elements: " + commonElements);
                    break;
                    
                case 2:
                    // Example with Integer lists
                    List<Integer> numbers1 = List.of(1, 2, 3, 4, 5, 6);
                    List<Integer> numbers2 = List.of(4, 5, 6, 7, 8, 9);
                    
                    List<Integer> commonNumbers = findCommonElements(numbers1, numbers2);
                    System.out.println("\nList 1: " + numbers1);
                    System.out.println("List 2: " + numbers2);
                    System.out.println("Common elements: " + commonNumbers);
                    break;
                    
                case 3:
                    // Example with String lists
                    List<String> fruits1 = List.of("apple", "banana", "cherry", "grape", "orange");
                    List<String> fruits2 = List.of("banana", "kiwi", "orange", "pear", "strawberry");
                    
                    List<String> commonFruits = findCommonElements(fruits1, fruits2);
                    System.out.println("\nList 1: " + fruits1);
                    System.out.println("List 2: " + fruits2);
                    System.out.println("Common elements: " + commonFruits);
                    break;
                    
                case 4:
                    running = false;
                    System.out.println("Exiting Common Elements Finder.");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}
