/*
 7. Write a program to showcase the static member execution control flows.
    This demonstrates the order of execution for static blocks, variables, and methods.
*/

public class p7_StaticExecutionFlow 
{
    // Static variable - initialized when class is loaded
    private static int staticVar1 = initializeStaticVar1();
    
    // Static method to initialize staticVar1
    private static int initializeStaticVar1() 
    {
        System.out.println("1. Static variable 'staticVar1' is being initialized");
        return 10;
    }
    
    // First static block - executed when class is loaded, after static variables
    static 
    {
        System.out.println("2. First static block is executed");
        staticVar2 = 20; // We can assign to staticVar2 here, even before its declaration
    }
    
    // Another static variable - initialized after the first static block
    private static int staticVar2;
    
    // Second static block - executed after all static variables
    static 
    {
        System.out.println("3. Second static block is executed");
        System.out.println("   Value of staticVar1: " + staticVar1);
        System.out.println("   Value of staticVar2: " + staticVar2);
    }
    
    // Static method - called only when explicitly invoked
    public static void staticMethod() 
    {
        System.out.println("6. staticMethod() is called");
        System.out.println("   Current values - staticVar1: " + staticVar1 + ", staticVar2: " + staticVar2);
    }
    
    // Instance variable - initialized when an object is created
    private int instanceVar = initializeInstanceVar();
    
    // Method to initialize instanceVar
    private int initializeInstanceVar() 
    {
        System.out.println("7. Instance variable 'instanceVar' is being initialized");
        return 30;
    }
    
    // Instance block - executed when an object is created, after static blocks
    {
        System.out.println("8. Instance block is executed");
        System.out.println("   Value of instanceVar: " + instanceVar);
    }
    
    // Constructor - executed after instance blocks when an object is created
    public p7_StaticExecutionFlow() 
    {
        System.out.println("9. Constructor is executed");
        System.out.println("   Final values - staticVar1: " + staticVar1 + 
                         ", staticVar2: " + staticVar2 + ", instanceVar: " + instanceVar);
    }

    public static void main(String[] args) 
    {
        System.out.println("\n=== Static Execution Flow Demonstration ===\n");
        
        System.out.println("4. Main method starts executing");
        System.out.println("5. Calling static method");
        staticMethod();
        
        System.out.println("\n10. Creating first object of p7_StaticExecutionFlow");
        p7_StaticExecutionFlow obj1 = new p7_StaticExecutionFlow();
        System.out.println("   Instance variable value from obj1: " + obj1.instanceVar);
        
        System.out.println("\n11. Creating second object of p7_StaticExecutionFlow");
        p7_StaticExecutionFlow obj2 = new p7_StaticExecutionFlow();
        System.out.println("   Instance variable value from obj2: " + obj2.instanceVar);
        
        System.out.println("\n12. Changing static variable values");
        staticVar1 = 100;
        staticVar2 = 200;
        
        System.out.println("13. Calling static method again after changing values");
        staticMethod();
        
        System.out.println("\n=== End of Demonstration ===");
        System.out.println("Notice that static blocks executed only once when class was loaded");
        System.out.println("But instance blocks and constructor executed once per object creation");
    }
}
