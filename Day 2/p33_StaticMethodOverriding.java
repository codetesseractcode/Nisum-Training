/**
 * Program to demonstrate that static methods cannot be overridden in Java
 * 
 * Static methods belong to the class, not to objects, and are resolved at compile time.
 * When a static method is defined in a subclass with the same signature as in the parent class:
 * 1. It's called METHOD HIDING, not method overriding
 * 2. The method called depends on the reference type, not the object type
 * 3. The @Override annotation will cause a compiler error if used with static methods
 */

public class p33_StaticMethodOverriding {
    public static void main(String[] args) {
        System.out.println("=== Static Method Hiding vs Method Overriding ===\n");
        
        // Create parent and child objects
        Parent parent = new Parent();
        Child child = new Child();
        
        System.out.println("1. Calling methods on parent and child references:");
        System.out.println("Parent reference, parent object:");
        parent.staticMethod();    // Calls Parent's static method
        parent.nonStaticMethod(); // Calls Parent's instance method
        
        System.out.println("\nChild reference, child object:");
        child.staticMethod();     // Calls Child's static method
        child.nonStaticMethod();  // Calls Child's instance method (overridden)
        
        // Now the important part - polymorphism works only for instance methods, not static methods
        System.out.println("\n2. Demonstrating polymorphism vs static method resolution:");
        Parent polymorphicRef = new Child(); // Parent reference, Child object
        
        System.out.println("\nParent reference, child object:");
        polymorphicRef.staticMethod();    // Calls Parent's static method (based on reference type)
        polymorphicRef.nonStaticMethod(); // Calls Child's instance method (based on object type)
        
        // We can also call static methods directly from the class
        System.out.println("\n3. Calling static methods directly from classes:");
        Parent.staticMethod();  // Calls Parent's static method
        Child.staticMethod();   // Calls Child's static method
        
        // Example with another class to further demonstrate the concept
        System.out.println("\n4. Using static methods in a real-world example:");
        
        // Using the static utility methods
        double circleArea = MathOperations.calculateArea(5.0);
        System.out.println("Circle area (using parent class): " + circleArea);
        
        double rectangleArea = EnhancedMathOperations.calculateArea(4.0, 6.0);
        System.out.println("Rectangle area (using child class): " + rectangleArea);
        
        // This will call MathOperations' calculateArea method (not overridden, just hidden)
        MathOperations mathRef = new EnhancedMathOperations();
        double result = mathRef.calculateArea(3.0); // Uses MathOperations.calculateArea
        System.out.println("Area using parent reference: " + result + " (circle area)");
        
        System.out.println("\n=== End of Static Method Hiding Demonstration ===");
    }
}

class Parent {
    // Static method
    public static void staticMethod() {
        System.out.println("Parent's static method");
    }
    
    // Non-static (instance) method
    public void nonStaticMethod() {
        System.out.println("Parent's non-static method");
    }
}

class Child extends Parent {
    // This is method hiding, not overriding
    public static void staticMethod() {
        System.out.println("Child's static method");
    }
    
    // This is method overriding
    @Override
    public void nonStaticMethod() {
        System.out.println("Child's non-static method (overridden)");
    }
}

// Practical example with math utility classes

class MathOperations {
    public static double calculateArea(double radius) {
        // Calculate circle area
        return Math.PI * radius * radius;
    }
    
    public void displayFormula() {
        System.out.println("Circle area formula: π*r^2");
    }
}

class EnhancedMathOperations extends MathOperations {
    // Cannot use @Override here since it's static method hiding
    public static double calculateArea(double radius) {
        // Enhanced precision for circle area calculation
        return Math.PI * Math.pow(radius, 2);
    }
    
    // Overload - not hiding or overriding since signature is different
    public static double calculateArea(double length, double width) {
        // Rectangle area calculation
        return length * width;
    }
    
    @Override
    public void displayFormula() {
        System.out.println("Formulas:\nCircle: π*r^2\nRectangle: l*w");
    }
}
