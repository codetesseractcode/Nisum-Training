// 18. Use StringUtils::toUpperCase as a method reference in Function<String, String>.
import java.util.function.Function;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Assignment18 {
    public static void main(String[] args) {
        Function<String, String> toUpperCase = String::toUpperCase;
        String result = toUpperCase.apply("hello");
        System.out.println("Original: hello");
        System.out.println("Uppercase: " + result);
        List<String> words = Arrays.asList("apple", "banana", "orange", "grape");
        List<String> upperCaseWords = words.stream()
                                          .map(String::toUpperCase)
                                          .collect(Collectors.toList());
        System.out.println("\nOriginal list: " + words);
        System.out.println("Uppercase list: " + upperCaseWords);
    }
}
