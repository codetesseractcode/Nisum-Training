/*
 10. Write a simple program to overload static methods.
     This program demonstrates how static methods can be overloaded with different parameters.
*/

public class p10_StaticMethodOverload 
{
    public static void main(String[] args) 
    {
        System.out.println("=== Static Method Overloading Demonstration ===\n");
        
        // Call the static methods with different parameters
        System.out.println("Calling calculate(int) with value 5:");
        int result1 = MathOperations.calculate(5);
        System.out.println("Result: " + result1);
        
        System.out.println("\nCalling calculate(int, int) with values 5 and 10:");
        int result2 = MathOperations.calculate(5, 10);
        System.out.println("Result: " + result2);
        
        System.out.println("\nCalling calculate(double) with value 5.5:");
        double result3 = MathOperations.calculate(5.5);
        System.out.println("Result: " + result3);
        
        System.out.println("\nCalling calculate(int, double) with values 5 and 2.5:");
        double result4 = MathOperations.calculate(5, 2.5);
        System.out.println("Result: " + result4);
        
        System.out.println("\nCalling calculate(String, int) with values \"5\" and 3:");
        int result5 = MathOperations.calculate("5", 3);
        System.out.println("Result: " + result5);
    }
}

class MathOperations 
{
    // Static method with one int parameter - squares the number
    public static int calculate(int a) 
    {
        System.out.println("Static method: calculate(int) - Squares the number");
        return a * a;
    }
    
    // Static method with two int parameters - adds the numbers
    public static int calculate(int a, int b) 
    {
        System.out.println("Static method: calculate(int, int) - Adds two integers");
        return a + b;
    }
    
    // Static method with one double parameter - rounds the number and returns double
    public static double calculate(double a) 
    {
        System.out.println("Static method: calculate(double) - Returns the rounded value");
        return Math.round(a);
    }
    
    // Static method with mixed parameters - multiplies int with double
    public static double calculate(int a, double b) 
    {
        System.out.println("Static method: calculate(int, double) - Multiplies int with double");
        return a * b;
    }
    
    // Static method with String and int - converts String to int and raises to power
    public static int calculate(String numStr, int power) 
    {
        System.out.println("Static method: calculate(String, int) - Raises string number to power");
        int num = Integer.parseInt(numStr);
        return (int)Math.pow(num, power);
    }
}
