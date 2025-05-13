/**
 * Program to demonstrate how to convert a numeric String to an int
 * 
 * This program shows different ways to convert string representations
 * of numbers into actual integer values.
 */

public class p44_StringToInt {
    public static void main(String[] args) {
        System.out.println("=== String to Integer Conversion ===\n");
        
        // Test strings
        String numString1 = "123";
        String numString2 = "456";
        String numString3 = "-789";
        String numString4 = "42";
        
        // Method 1: Using Integer.parseInt()
        System.out.println("Method 1: Using Integer.parseInt()");
        int value1 = Integer.parseInt(numString1);
        System.out.println("String \"" + numString1 + "\" converted to int: " + value1);
        System.out.println("Mathematical operation: " + value1 + " * 2 = " + (value1 * 2));
        
        // Method 2: Using Integer.valueOf()
        System.out.println("\nMethod 2: Using Integer.valueOf()");
        int value2 = Integer.valueOf(numString2);
        System.out.println("String \"" + numString2 + "\" converted to int: " + value2);
        System.out.println("Mathematical operation: " + value2 + " * 2 = " + (value2 * 2));
        
        // Method 3: Handling negative numbers
        System.out.println("\nMethod 3: Handling negative numbers");
        int value3 = Integer.parseInt(numString3);
        System.out.println("String \"" + numString3 + "\" converted to int: " + value3);
        System.out.println("Mathematical operation: " + value3 + " * 2 = " + (value3 * 2));
        
        // Method 4: Converting with different radix (base)
        System.out.println("\nMethod 4: Converting with different radix");
        String hexString = "2A"; // Hex for 42
        int hexValue = Integer.parseInt(hexString, 16);
        System.out.println("Hex String \"" + hexString + "\" converted to int: " + hexValue);
        
        String binaryString = "101010"; // Binary for 42
        int binaryValue = Integer.parseInt(binaryString, 2);
        System.out.println("Binary String \"" + binaryString + "\" converted to int: " + binaryValue);
        
        // Exception handling
        System.out.println("\nMethod 5: Exception handling during conversion");
        try {
            String invalidNum = "123abc";
            int value = Integer.parseInt(invalidNum);
            System.out.println("This won't be executed");
        } catch (NumberFormatException e) {
            System.out.println("Exception caught: Cannot convert \"123abc\" to int");
            System.out.println("Error message: " + e.getMessage());
        }
        
        System.out.println("\n=== End of String to Integer Conversion ===");
    }
}
