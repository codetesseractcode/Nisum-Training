/**
 * Program to demonstrate how to remove multiple spaces in a string
 * 
 * This program shows different ways to remove or replace multiple
 * consecutive spaces in strings with a single space.
 */

public class p45_RemoveMultipleSpaces {
    public static void main(String[] args) {
        System.out.println("=== Removing Multiple Spaces from Strings ===\n");
        
        // Test strings with multiple spaces
        String text1 = "This    has    multiple    spaces";
        String text2 = " Leading and trailing    spaces   ";
        String text3 = "Tabs\t\tand\tnewlines\n\nincluded";
        
        // Method 1: Using replaceAll with regex
        System.out.println("Method 1: Using replaceAll with regex '\\\\s+'");
        System.out.println("Original: \"" + text1 + "\"");
        String result1 = text1.replaceAll("\\s+", " ");
        System.out.println("Modified: \"" + result1 + "\"");
        
        // Method 2: Using replaceAll with trim to handle leading/trailing spaces
        System.out.println("\nMethod 2: Combining replaceAll with trim()");
        System.out.println("Original: \"" + text2 + "\"");
        String result2 = text2.replaceAll("\\s+", " ").trim();
        System.out.println("Modified: \"" + result2 + "\"");
        
        // Method 3: Handling all whitespace characters (tabs, newlines, etc.)
        System.out.println("\nMethod 3: Handling all whitespace characters");
        System.out.println("Original: \"" + text3 + "\"");
        String result3 = text3.replaceAll("\\s+", " ");
        System.out.println("Modified: \"" + result3 + "\"");
        
        // Method 4: Using String.split() and String.join()
        System.out.println("\nMethod 4: Using split() and join()");
        System.out.println("Original: \"" + text1 + "\"");
        String[] words = text1.trim().split("\\s+");
        String result4 = String.join(" ", words);
        System.out.println("Modified: \"" + result4 + "\"");
        
        // Method 5: Manual approach using StringBuilder
        System.out.println("\nMethod 5: Manual approach with StringBuilder");
        System.out.println("Original: \"" + text1 + "\"");
        String result5 = removeMultipleSpacesManually(text1);
        System.out.println("Modified: \"" + result5 + "\"");
        
        System.out.println("\n=== End of Removing Multiple Spaces ===");
    }
    
    /**
     * Manually removes multiple spaces using StringBuilder and character evaluation
     */
    private static String removeMultipleSpacesManually(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        StringBuilder result = new StringBuilder();
        boolean lastWasSpace = false;
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isWhitespace(c)) {
                if (!lastWasSpace) {
                    result.append(' ');
                    lastWasSpace = true;
                }
            } else {
                result.append(c);
                lastWasSpace = false;
            }
        }
        
        return result.toString().trim();
    }
}
