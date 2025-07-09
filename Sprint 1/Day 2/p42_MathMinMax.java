/**
 * Program to demonstrate use of Math.min() and Math.max() methods
 * 
 * This program shows how to find minimum and maximum between two numbers
 * using the built-in Math class methods.
 */

public class p42_MathMinMax {
    public static void main(String[] args) {
        System.out.println("=== Math Min and Max Demonstration ===\n");
        
        // Define integer values
        int i1 = 27;
        int i2 = -45;
        
        // Define double values
        double d1 = 84.6;
        double d2 = 0.45;
        
        // Find minimum and maximum for integers
        int minInt = Math.min(i1, i2);
        int maxInt = Math.max(i1, i2);
        
        // Find minimum and maximum for doubles
        double minDouble = Math.min(d1, d2);
        double maxDouble = Math.max(d1, d2);
        
        // Print the results in the specified format
        System.out.println("Minimum out of '" + i1 + "' and '" + i2 + "' = " + minInt);
        System.out.println("Maximum out of '" + i1 + "' and '" + i2 + "' = " + maxInt);
        System.out.println("Minimum out of '" + d1 + "' and '" + d2 + "' = " + minDouble);
        System.out.println("Maximum out of '" + d1 + "' and '" + d2 + "' = " + maxDouble);
        
        System.out.println("\n=== End of Math Min and Max Demonstration ===");
    }
}
