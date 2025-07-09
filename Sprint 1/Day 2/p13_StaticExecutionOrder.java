/*
 13. Static block and static method: Write program to illustrate which one executes first?
     This program demonstrates the execution order between static blocks and static methods.
*/

public class p13_StaticExecutionOrder 
{
    // First static block
    static 
    {
        System.out.println("1. First static block is executing");
        System.out.println("   Note: This executes when the class is loaded");
    }
    
    // Static variable initialization using a method
    private static int staticVar = initializeStaticVar();
    
    // Static method that initializes the variable
    private static int initializeStaticVar() 
    {
        System.out.println("2. Static variable initialization method is executing");
        return 100;
    }
    
    // Second static block
    static 
    {
        System.out.println("3. Second static block is executing");
        System.out.println("   Static variable value: " + staticVar);
    }
    
    // Static method that will be explicitly called
    public static void staticMethod() 
    {
        System.out.println("5. Static method explicitly called from main");
        System.out.println("   This only runs when specifically invoked");
    }
    
    // Main method - entry point of program
    public static void main(String[] args) 
    {
        System.out.println("\n=== Static Block vs Static Method Execution Order ===\n");
        
        System.out.println("4. Main method is executing");
        System.out.println("   Note: All static blocks executed before reaching this point");
        
        // Call the static method
        staticMethod();
        
        System.out.println("\n6. Creating an instance of StaticExecutionOrder");
        p13_StaticExecutionOrder instance = new p13_StaticExecutionOrder();
        
        System.out.println("\n7. Creating another class to demonstrate separate class loading");
        System.out.println("   Notice that AnotherClass's static blocks will execute now:");
        AnotherClass.accessMethod();
        
        System.out.println("\n=== Execution Order Summary ===");
        System.out.println("1. Static blocks and static variable initializers execute when class is loaded");
        System.out.println("2. They execute in the order they appear in the class");
        System.out.println("3. Static methods only execute when explicitly called");
        System.out.println("4. Class loading happens before any methods of that class can be called");
    }
    
    // Constructor
    public p13_StaticExecutionOrder() 
    {
        System.out.println("   Instance constructor is executing");
    }
}

// Another class to demonstrate class loading
class AnotherClass 
{
    // Static block in another class
    static 
    {
        System.out.println("   AnotherClass's static block is executing");
        System.out.println("   This happens when AnotherClass is first referenced");
    }
    
    // Static method that will be called
    public static void accessMethod() 
    {
        System.out.println("   AnotherClass's static method is executing");
    }
}
