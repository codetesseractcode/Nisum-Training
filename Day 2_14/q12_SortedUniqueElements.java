import java.util.TreeSet;
import java.util.Arrays;

public class q12_SortedUniqueElements {
    
    public static Integer[] getSortedUniqueElements(int[] array) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        
        for (int element : array) {
            treeSet.add(element);
        }
        
        return treeSet.toArray(new Integer[0]);
    }
      public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        boolean running = true;
        
        System.out.println("===== Sorted Unique Elements Finder =====");
        
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Enter numbers manually");
            System.out.println("2. Use sample array");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");
            
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
                    System.out.print("How many numbers do you want to enter? ");
                    int count = 0;
                    try {
                        count = scanner.nextInt();
                        scanner.nextLine(); // Clear the buffer
                        if (count <= 0) {
                            System.out.println("Please enter a positive number.");
                            continue;
                        }
                    } catch (Exception e) {
                        scanner.nextLine(); // Clear the buffer
                        System.out.println("Invalid input. Please enter a valid number.");
                        continue;
                    }
                    
                    int[] userNumbers = new int[count];
                    System.out.println("Enter " + count + " numbers:");
                    
                    for (int i = 0; i < count; i++) {
                        System.out.print("Number " + (i + 1) + ": ");
                        try {
                            userNumbers[i] = scanner.nextInt();
                            scanner.nextLine(); // Clear the buffer
                        } catch (Exception e) {
                            scanner.nextLine(); // Clear the buffer
                            System.out.println("Invalid input. Please enter a valid number.");
                            i--; // Retry the same position
                        }
                    }
                    
                    System.out.println("\nOriginal array: " + Arrays.toString(userNumbers));
                    Integer[] sortedUnique = getSortedUniqueElements(userNumbers);
                    System.out.println("Sorted unique elements: " + Arrays.toString(sortedUnique));
                    break;
                    
                case 2:
                    int[] sampleNumbers = {5, 2, 8, 2, 9, 1, 5, 6, 3, 8, 4};
                    System.out.println("\nSample array: " + Arrays.toString(sampleNumbers));
                    Integer[] sampleSortedUnique = getSortedUniqueElements(sampleNumbers);
                    System.out.println("Sorted unique elements: " + Arrays.toString(sampleSortedUnique));
                    break;
                    
                case 3:
                    running = false;
                    System.out.println("Exiting Sorted Unique Elements Finder.");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}
