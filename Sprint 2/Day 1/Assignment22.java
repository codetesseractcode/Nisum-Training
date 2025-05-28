// 22. Use Consumer<String> to print each element in a list.
import java.util.*;
import java.util.function.Consumer;

public class Assignment22 {
    public static void main(String[] args) {
        List<String> animals = Arrays.asList("Dog", "Cat", "Elephant", "Lion", "Tiger");
        System.out.println("Animals list: " + animals);
        Consumer<String> printer = System.out::println;
        System.out.println("\nPrinting each animal using Consumer:");
        animals.forEach(printer);
        System.out.println("\nPrinting each animal using lambda Consumer:");
        animals.forEach(animal -> System.out.println("Animal: " + animal));
        Consumer<String> logOperation = s -> System.out.print("Processing: " + s + " ");
        Consumer<String> chainedConsumer = logOperation.andThen(printer);
        System.out.println("\nPrinting with chained consumers:");
        animals.forEach(chainedConsumer);
    }
}
