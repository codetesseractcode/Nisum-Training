// 20. Use Predicate.and() and Predicate.or() to filter numbers divisible by both 2 and 3.
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Assignment20 {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 15, 18, 20, 24, 30);
        System.out.println("Original numbers: " + numbers);
        Predicate<Integer> isDivisibleBy2 = n -> n % 2 == 0;
        Predicate<Integer> isDivisibleBy3 = n -> n % 3 == 0;
        List<Integer> divisibleByBoth = numbers.stream()
                                              .filter(isDivisibleBy2.and(isDivisibleBy3))
                                              .collect(Collectors.toList());
        System.out.println("Numbers divisible by both 2 and 3: " + divisibleByBoth);
        List<Integer> divisibleByEither = numbers.stream()
                                                .filter(isDivisibleBy2.or(isDivisibleBy3))
                                                .collect(Collectors.toList());
        System.out.println("Numbers divisible by either 2 or 3: " + divisibleByEither);
        List<Integer> notDivisibleBy2 = numbers.stream()
                                              .filter(isDivisibleBy2.negate())
                                              .collect(Collectors.toList());
        System.out.println("Numbers not divisible by 2: " + notDivisibleBy2);
    }
}
