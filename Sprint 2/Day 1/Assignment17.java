// 17. Use lambda to sort a list of strings by their lengths.
import java.util.*;

public class Assignment17 {
    public static void main(String[] args) {
        List<String> cities = new ArrayList<>(Arrays.asList(
            "New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "San Francisco"
        ));
        System.out.println("Original cities: " + cities);
        cities.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));
        System.out.println("Sorted by length (ascending): " + cities);
        cities.sort((s1, s2) -> Integer.compare(s2.length(), s1.length()));
        System.out.println("Sorted by length (descending): " + cities);
    }
}
