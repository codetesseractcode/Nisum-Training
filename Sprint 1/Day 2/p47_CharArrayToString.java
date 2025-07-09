/**
 * Program to demonstrate how to convert Character array to String
 * 
 * This program shows different ways to convert character arrays
 * to String objects using various String methods.
 */

public class p47_CharArrayToString {
    public static void main(String[] args) {
        System.out.println("=== Converting Character Array to String ===\n");
        
        // Test char arrays
        char[] charArray1 = {'H', 'e', 'l', 'l', 'o'};
        char[] charArray2 = {'W', 'o', 'r', 'l', 'd', '!'};
        char[] emptyArray = {};
        char[] unicodeArray = {'\u0048', '\u0069', '\u0021'}; // "Hi!" in Unicode
        
        // Method 1: Using String constructor
        System.out.println("Method 1: Using String constructor");
        String string1 = new String(charArray1);
        System.out.println("Char Array: " + displayCharArray(charArray1));
        System.out.println("As String: \"" + string1 + "\"");
        
        // Method 2: Using String.valueOf()
        System.out.println("\nMethod 2: Using String.valueOf()");
        String string2 = String.valueOf(charArray2);
        System.out.println("Char Array: " + displayCharArray(charArray2));
        System.out.println("As String: \"" + string2 + "\"");
        
        // Method 3: Using String constructor with specific portion of array
        System.out.println("\nMethod 3: Using String constructor with offset and length");
        String string3 = new String(charArray2, 0, 5); // first 5 chars only (excludes '!')
        System.out.println("Char Array: " + displayCharArray(charArray2));
        System.out.println("As String (first 5 chars): \"" + string3 + "\"");
        
        // Method 4: Converting empty array
        System.out.println("\nMethod 4: Converting empty array");
        String string4 = new String(emptyArray);
        System.out.println("Empty Char Array: " + displayCharArray(emptyArray));
        System.out.println("As String: \"" + string4 + "\"");
        System.out.println("Is empty string: " + string4.isEmpty());
        
        // Method 5: Handling Unicode characters
        System.out.println("\nMethod 5: Handling Unicode characters");
        String string5 = new String(unicodeArray);
        System.out.println("Unicode Char Array: " + displayCharArray(unicodeArray));
        System.out.println("Unicode values: \\u0048 \\u0069 \\u0021");
        System.out.println("As String: \"" + string5 + "\"");
        
        // Method 6: Using StringBuilder
        System.out.println("\nMethod 6: Using StringBuilder");
        StringBuilder sb = new StringBuilder();
        for (char c : charArray1) {
            sb.append(c);
        }
        String string6 = sb.toString();
        System.out.println("Char Array: " + displayCharArray(charArray1));
        System.out.println("As String (via StringBuilder): \"" + string6 + "\"");
        
        // Method 7: Combining character arrays
        System.out.println("\nMethod 7: Combining multiple character arrays");
        String combinedString = new String(charArray1) + " " + new String(charArray2);
        System.out.println("Combined String: \"" + combinedString + "\"");
        
        System.out.println("\n=== End of Character Array to String Conversion ===");
    }
    
    /**
     * Helper method to display char array contents in a readable format
     */
    private static String displayCharArray(char[] array) {
        if (array.length == 0) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append('\'').append(array[i]).append('\'');
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
