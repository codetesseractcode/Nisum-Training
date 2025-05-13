/**
 * Program to demonstrate static method implementation in interface
 * 
 * Static methods in interfaces (introduced in Java 8):
 * - Belong to the interface rather than instances
 * - Cannot be overridden by implementing classes
 * - Must be called using the interface name
 * - Can be used to provide utility methods related to the interface
 */

public class p24_StaticMethodInterface {
    public static void main(String[] args) {
        System.out.println("=== Static Methods in Interfaces ===\n");
        
        // Call static methods directly from interfaces
        System.out.println("Calling static methods directly from interfaces:");
        System.out.println("Circle area: " + MathUtils.calculateCircleArea(5.0));
        System.out.println("Rectangle area: " + MathUtils.calculateRectangleArea(4.0, 6.0));
        System.out.println("Triangle area: " + MathUtils.calculateTriangleArea(5.0, 8.0));
        System.out.println("Square area: " + MathUtils.calculateSquareArea(7.0));
        
        // Constants from interface
        System.out.println("\nUsing constants from interface:");
        System.out.println("PI value: " + MathUtils.PI);
        
        // Create an implementing class
        AreaCalculator calculator = new AreaCalculator();
        
        System.out.println("\nUsing implementing class methods:");
        System.out.println("Circle with radius 10: " + calculator.getCircleArea(10.0));
        System.out.println("Rectangle 8x12: " + calculator.getRectangleArea(8.0, 12.0));
        
        // Note: Static methods can't be called from the implementing class reference
        // The following would not work:
        // calculator.calculateCircleArea(5.0); 
        
        System.out.println("\n=== End of Static Methods Demonstration ===");
    }
}

// Interface with static methods
interface MathUtils {
    // Constant (All interface variables are implicitly public, static and final)
    double PI = 3.14159;
    
    // Static methods - available through the interface name
    static double calculateCircleArea(double radius) {
        return PI * radius * radius;
    }
    
    static double calculateRectangleArea(double length, double width) {
        return length * width;
    }
    
    static double calculateTriangleArea(double base, double height) {
        return 0.5 * base * height;
    }
    
    static double calculateSquareArea(double side) {
        return side * side;
    }
    
    // Regular interface methods (abstract)
    double getCircleArea(double radius);
    double getRectangleArea(double length, double width);
}

// Class implementing the interface
class AreaCalculator implements MathUtils {
    @Override
    public double getCircleArea(double radius) {
        // Using the static method from interface
        return MathUtils.calculateCircleArea(radius);
    }
    
    @Override
    public double getRectangleArea(double length, double width) {
        // Using the static method from interface
        return MathUtils.calculateRectangleArea(length, width);
    }
    
    // Note: We don't implement the static methods as they belong to the interface
}
