// 7. Remove even numbers using Predicate and removeIf
import java.util.*;
import java.util.function.Predicate;

public class Assignment7 {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.println("Original numbers: " + numbers);
        
        Predicate<Integer> isEven = num -> num % 2 == 0;
        
        numbers.removeIf(isEven);
        
        System.out.println("After removing even numbers: " + numbers);
    }
}
