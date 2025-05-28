// 23. Use Consumer<Integer> to double the value and print it for each number in a list.
import java.util.*;
import java.util.function.Consumer;

public class Assignment23 {
    public static void main(String[] args) {
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Original values: " + values);
        Consumer<Integer> doubleAndPrint = n -> System.out.println(n + " doubled is " + (n * 2));
        System.out.println("\nPrinting doubled values:");
        values.forEach(doubleAndPrint);
        System.out.println("\nCreating a new list with doubled values:");
        List<Integer> doubledValues = new ArrayList<>();
        Consumer<Integer> doubleAndAdd = n -> doubledValues.add(n * 2);
        values.forEach(doubleAndAdd);
        System.out.println("Doubled values list: " + doubledValues);
    }
}
