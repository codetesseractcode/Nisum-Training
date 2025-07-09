/*
 12. Java Program to Check Whether a Static Method Can Access the Instance Variable
     This program demonstrates the behavior and limitations of static methods when
     accessing instance variables.
*/

public class p12_StaticInstanceAccess 
{
    public static void main(String[] args) 
    {
        System.out.println("=== Static Method & Instance Variable Access Demonstration ===\n");
        
        // Create an instance of DemoClass
        System.out.println("Creating an instance of DemoClass:");
        DemoClass demo = new DemoClass();
        demo.instanceVar = 100;
        
        // Access the instance variable through the object
        System.out.println("Value of instance variable: " + demo.instanceVar);
        
        // Call the instance method (can access instance variable)
        System.out.println("\nCalling instance method:");
        demo.instanceMethod();
        
        // Call the static method directly (cannot access instance variable)
        System.out.println("\nCalling static method directly:");
        DemoClass.staticMethod();
        
        // Call the static method that uses parameter
        System.out.println("\nCalling static method with DemoClass parameter:");
        DemoClass.staticMethodWithParameter(demo);
        
        System.out.println("\n=== Conclusion ===");
        System.out.println("1. Static methods cannot directly access instance variables");
        System.out.println("2. Static methods can access instance variables only if:");
        System.out.println("   a. An object reference is passed as parameter");
        System.out.println("   b. An object is created inside the static method");
    }
}

class DemoClass 
{
    // Instance variable - belongs to each instance/object of the class
    public int instanceVar = 10;
    
    // Static variable - belongs to the class, shared by all instances
    public static int staticVar = 20;
    
    // Instance method - can access both instance and static variables
    public void instanceMethod() 
    {
        System.out.println("Inside instance method:");
        System.out.println("  Can access instance variable: instanceVar = " + instanceVar);
        System.out.println("  Can also access static variable: staticVar = " + staticVar);
    }
    
    // Static method - can only access static variables directly
    public static void staticMethod() 
    {
        System.out.println("Inside static method:");
        
        // This works - static methods can access static variables
        System.out.println("  Can access static variable: staticVar = " + staticVar);
        
        // This would cause a compile error if uncommented - static methods cannot
        // directly access instance variables because there's no instance context
        // System.out.println("  Cannot access instance variable directly: instanceVar = " + instanceVar);
        
        // However, static methods can access instance variables via an object:
        System.out.println("  To access instance variable, need an object reference");
        
        // Create an object inside the static method
        DemoClass newObj = new DemoClass();
        System.out.println("  Created new object, its instanceVar = " + newObj.instanceVar);
    }
    
    // Static method that receives an object as parameter
    public static void staticMethodWithParameter(DemoClass obj) 
    {
        System.out.println("Inside static method with parameter:");
        
        // This works because we have an object reference
        System.out.println("  Can access instance variable through parameter: obj.instanceVar = " + obj.instanceVar);
        
        // Modifying the instance variable
        obj.instanceVar += 5;
        System.out.println("  Modified instance variable: obj.instanceVar = " + obj.instanceVar);
    }
}
