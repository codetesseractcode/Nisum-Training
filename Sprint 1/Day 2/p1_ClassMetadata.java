/*
 1.This program demonstrates how to use reflection in Java to get class metadata.
   It retrieves the class name, methods, and checks if the class is an interface, array, or enum.
*/


public class p1_ClassMetadata 
{
    public static void main(String[] args) 
    {
        // Create an object of a sample class
        SampleClass obj = new SampleClass();

        // Get the Class object using getClass()
        Class<?> clazz = obj.getClass();

        // Print class metadata
        System.out.println("Full Name: " + clazz.getName());
        System.out.println("Simple Name: " + clazz.getSimpleName());
        System.out.println("Is Interface: " + clazz.isInterface());
        System.out.println("Is Array: " + clazz.isArray());
        System.out.println("Is Enum: " + clazz.isEnum());
        System.out.println("\nMethods:");
        java.lang.reflect.Method[] methods = clazz.getDeclaredMethods();
        for (java.lang.reflect.Method method : methods) {
            System.out.println("  " + method.getName());
        }
        
        // Call methodTwo once outside the loop
        int result = obj.methodTwo(5);
        System.out.println("\nResult of methodTwo(5): " + result);
    }
}

class SampleClass 
{
    public void methodOne()
    {
        
    }
    public int methodTwo(int x) 
    { 
        return x * 2; 
    }
}
