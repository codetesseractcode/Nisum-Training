// 19. Filter a list of strings to find elements that start with "A".
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Assignment19 {
    public static void main(String[] args) {
        List<String> countries = Arrays.asList(
            "Australia", "Brazil", "Canada", "Argentina", "America", "India", "China", "Austria"
        );
        System.out.println("Original countries: " + countries);
        Predicate<String> startsWithA = country -> country.startsWith("A");
        List<String> countriesWithA = countries.stream()
                                              .filter(startsWithA)
                                              .collect(Collectors.toList());
        System.out.println("Countries starting with 'A': " + countriesWithA);
        List<String> result = countries.stream()
                                      .filter(Assignment19::startsWithA)
                                      .collect(Collectors.toList());
        System.out.println("Countries starting with 'A' (using method reference): " + result);
    }
    private static boolean startsWithA(String s) {
        return s.startsWith("A");
    }
}
