// 8. Use Consumer<List<String>> to print list items in uppercase
import java.util.*;
import java.util.function.Consumer;

public class Assignment8 {
    public static void main(String[] args) {
        List<String> fruits = Arrays.asList("apple", "banana", "orange", "grape");
        System.out.println("Original fruits: " + fruits);
        
        Consumer<List<String>> upperCasePrinter = list -> 
            list.forEach(item -> System.out.println(item.toUpperCase()));
        
        System.out.println("\nFruits in uppercase:");
        upperCasePrinter.accept(fruits);
    }
}
