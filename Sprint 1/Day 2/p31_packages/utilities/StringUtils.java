package p31_packages.utilities;

/**
 * StringUtils utility class
 */
public class StringUtils {
    /**
     * Reverse a string
     */
    public static String reverse(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        StringBuilder reversed = new StringBuilder(input);
        return reversed.reverse().toString();
    }
      /**
     * Capitalize first letter of each word
     */
    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        String[] words = input.split("\\s");
        StringBuilder capitalized = new StringBuilder();
        
        for (String word : words) {
            if (word.isEmpty()) continue;
            capitalized.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1))
                      .append(" ");
        }
        
        return capitalized.toString().trim();
    }
      /**
     * Count occurrences of a character in a string
     */
    public static int countCharOccurrences(String input, char target) {
        if (input == null || input.isEmpty()) {
            return 0;
        }
        
        int count = 0;
        for (char c : input.toCharArray()) {
            if (c == target) {
                count++;
            }
        }
        
        return count;
    }
}
