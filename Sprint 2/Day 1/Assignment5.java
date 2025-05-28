// 5. You want to filter a list of strings to only keep ones that start with "J".
// How can you use Predicate<String> for this?
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Assignment5 {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Jane", "Alex", "Joe", "Mike");
        
        Predicate<String> startsWithJ = name -> name.startsWith("J");
        
        List<String> filteredNames = names.stream()
                                         .filter(startsWithJ)
                                         .collect(Collectors.toList());
        
        System.out.println("Original names: " + names);
        System.out.println("Names starting with J: " + filteredNames);
    }
}
