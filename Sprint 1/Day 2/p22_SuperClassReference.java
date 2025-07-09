/*
 22. Write a Program to demonstrate whether we can call sub class methods 
     using super class object reference.
*/

public class p22_SuperClassReference 
{
    public static void main(String[] args) 
    {
        System.out.println("=== Super Class Reference & Sub Class Methods ===\n");
        
        // Create a super class object
        System.out.println("1. Creating a Parent class object with Parent reference:");
        Parent parentObj = new Parent();
        parentObj.display();  // Calls Parent's display method
        parentObj.parentMethod();  // Calls Parent's method
        System.out.println("Variable value: " + parentObj.value);
        
        // Create a sub class object with sub class reference
        System.out.println("\n2. Creating a Child class object with Child reference:");
        Child childObj = new Child();
        childObj.display();  // Calls Child's overridden display method
        childObj.parentMethod();  // Calls inherited parentMethod
        childObj.childMethod();  // Calls Child's specific method
        System.out.println("Variable value: " + childObj.value); // Accesses Child's value
        System.out.println("Parent's variable can be accessed using super: " + childObj.getParentValue());
        
        // Create a sub class object with super class reference
        System.out.println("\n3. Creating a Child class object with Parent reference (upcasting):");
        Parent parentRef = new Child();  // Upcasting
        parentRef.display();  // Calls Child's display method (runtime polymorphism)
        parentRef.parentMethod();  // Calls Parent's method
        
        // The following line would cause compilation error if uncommented:
        // parentRef.childMethod();  // Cannot call Child's specific method with Parent reference
        
        System.out.println("Variable value: " + parentRef.value); // Accesses Parent's value (no runtime polymorphism for variables)
        
        // Downcasting to access Child-specific methods
        System.out.println("\n4. Downcasting Parent reference to Child reference:");
        try 
        {
            Child castedChild = (Child) parentRef;  // Downcasting
            castedChild.childMethod();  // Now we can call childMethod
            System.out.println("Downcast successful - we can access Child methods now");
        } 
        catch (ClassCastException e) 
        {
            System.out.println("Cast failed: " + e.getMessage());
        }
        
        // Attempting invalid downcast
        System.out.println("\n5. Attempting invalid downcast (would fail at runtime):");
        try 
        {
            Parent pureParent = new Parent();
            Child invalidCast = (Child) pureParent;  // This will fail at runtime
            invalidCast.childMethod();
        } 
        catch (ClassCastException e) 
        {
            System.out.println("Cast failed: " + e.getMessage());
        }
        
        System.out.println("\n=== Conclusion ===");
        System.out.println("1. We CANNOT directly call subclass-specific methods using a superclass reference");
        System.out.println("2. We CAN call overridden methods (defined in both classes) - the subclass version will execute");
        System.out.println("3. We need to downcast the superclass reference to a subclass reference to access subclass-specific methods");
        System.out.println("4. Downcasting only works if the object was originally of the subclass type");
    }
}

class Parent 
{
    int value = 100;
    
    public void display() 
    {
        System.out.println("Parent's display method");
    }
    
    public void parentMethod() 
    {
        System.out.println("This is a method defined only in Parent");
    }
}

class Child extends Parent 
{
    int value = 200;  // Shadows the parent's value
    
    @Override
    public void display() 
    {
        System.out.println("Child's overridden display method");
    }
    
    public void childMethod() 
    {
        System.out.println("This is a method defined only in Child");
    }
    
    public int getParentValue() 
    {
        return super.value;  // Access parent's shadowed variable
    }
}
