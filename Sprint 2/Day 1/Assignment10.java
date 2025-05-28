// 10. Given a list of strings, sort it based on length (try using comparator).
import java.util.*;

public class Assignment10 {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>(Arrays.asList("banana", "apple", "pear", "strawberry", "kiwi"));
        System.out.println("Original words: " + words);
        
        words.sort(Comparator.comparingInt(String::length));
        
        System.out.println("Sorted by length: " + words);
        
        Comparator<String> lengthComparator = (s1, s2) -> Integer.compare(s1.length(), s2.length());
        words.sort(lengthComparator);
        
        System.out.println("Sorted by length (explicit comparator): " + words);
    }
}
