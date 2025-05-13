/**
 * Program to convert Integer to Float using wrapper class methods
 * 
 * This program demonstrates:
 * 1. Converting Integer to Float using wrapper classes
 * 2. Converting primitive int to primitive float
 * 3. Various other wrapper class conversion methods
 * 4. Autoboxing and unboxing in Java
 */

public class p39_IntToFloatConversion {
    public static void main(String[] args) {
        System.out.println("=== Integer to Float Conversion Using Wrapper Classes ===\n");
        
        // Example 1: Converting Integer wrapper to Float wrapper
        System.out.println("1. Converting Integer wrapper to Float wrapper:");
        
        // Create an Integer wrapper object
        Integer integerObj = 500;
        
        // Method 1: Using intValue() and Float constructor
        Float floatObj1 = new Float(integerObj.intValue());
        System.out.println("Method 1 (intValue & Float constructor): " + integerObj + " -> " + floatObj1);
        
        // Method 2: Using valueOf() method
        Float floatObj2 = Float.valueOf(integerObj);
        System.out.println("Method 2 (Float.valueOf): " + integerObj + " -> " + floatObj2);
        
        // Method 3: Using floatValue() method
        Float floatObj3 = integerObj.floatValue();
        System.out.println("Method 3 (floatValue): " + integerObj + " -> " + floatObj3);
        System.out.println();
        
        // Example 2: Converting primitive int to primitive float
        System.out.println("2. Converting primitive int to primitive float:");
        
        int primitiveInt = 750;
        float primitiveFloat = (float) primitiveInt;
        
        System.out.println("Primitive conversion: " + primitiveInt + " -> " + primitiveFloat);
        
        // Another example with a larger number
        int largeInt = 2147483647; // Maximum value for int
        float largeFloat = (float) largeInt;
        System.out.println("Large int conversion: " + largeInt + " -> " + largeFloat);
        System.out.println();
        
        // Example 3: Working with negative numbers
        System.out.println("3. Working with negative numbers:");
        
        Integer negativeInt = -245;
        Float negativeFloat = negativeInt.floatValue();
        
        System.out.println("Negative conversion: " + negativeInt + " -> " + negativeFloat);
        System.out.println();
        
        // Example 4: Converting String to Integer and then to Float
        System.out.println("4. Converting String to Integer and then to Float:");
        
        String numberStr = "1000";
        
        // First convert String to Integer
        Integer parsedInt = Integer.parseInt(numberStr);
        
        // Then convert Integer to Float
        Float parsedFloat = parsedInt.floatValue();
        
        System.out.println("String -> Integer -> Float: " + numberStr + " -> " + parsedInt + " -> " + parsedFloat);
        System.out.println();
        
        // Example 5: Demonstrating precision issues
        System.out.println("5. Demonstrating precision issues:");
        
        Integer preciseInt = 123456789;
        Float preciseFloat = preciseInt.floatValue();
        
        System.out.println("Large int to float (may lose precision): " + preciseInt + " -> " + preciseFloat);
        
        // Converting back to int to see precision loss
        int backToInt = preciseFloat.intValue();
        System.out.println("Back to int: " + preciseFloat + " -> " + backToInt);
        System.out.println("Precision difference: " + (preciseInt - backToInt));
        System.out.println();
        
        // Example 6: Other useful wrapper class methods
        System.out.println("6. Other useful wrapper class methods:");
        
        // Convert Integer to binary string
        Integer numToConvert = 25;
        String binaryString = Integer.toBinaryString(numToConvert);
        System.out.println("Integer to binary: " + numToConvert + " -> " + binaryString);
        
        // Convert Integer to hexadecimal string
        String hexString = Integer.toHexString(numToConvert);
        System.out.println("Integer to hexadecimal: " + numToConvert + " -> " + hexString);
        
        // Convert Integer to octal string
        String octalString = Integer.toOctalString(numToConvert);
        System.out.println("Integer to octal: " + numToConvert + " -> " + octalString);
        
        // Convert Float to String
        Float floatToString = 123.456f;
        String floatStr = floatToString.toString();
        System.out.println("Float to String: " + floatToString + " -> " + floatStr);
        
        // Parse String to Float
        String floatParseStr = "987.654";
        Float parsedFloatObj = Float.parseFloat(floatParseStr);
        System.out.println("String to Float: " + floatParseStr + " -> " + parsedFloatObj);
        
        System.out.println("\n=== End of Integer to Float Conversion Demonstration ===");
    }
}
