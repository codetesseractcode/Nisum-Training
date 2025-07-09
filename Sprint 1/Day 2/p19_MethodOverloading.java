/*
 19. Write a Method Overloading Program in Java (compile time or static polymorphism)
     This program demonstrates different ways to overload methods in Java.
*/

public class p19_MethodOverloading 
{
    public static void main(String[] args) 
    {
        System.out.println("=== Method Overloading Demonstration ===\n");
        
        // Create an instance of CalculationDemo
        CalculationDemo calculator = new CalculationDemo();
        
        // Demonstrate method overloading with different parameter types
        System.out.println("1. Method Overloading with Different Parameter Types:");
        int sumIntegers = calculator.add(10, 20);
        double sumDoubles = calculator.add(10.5, 20.5);
        String combineStrings = calculator.add("Hello", "World");
        
        System.out.println("   add(10, 20) = " + sumIntegers);
        System.out.println("   add(10.5, 20.5) = " + sumDoubles);
        System.out.println("   add(\"Hello\", \"World\") = " + combineStrings);
        
        // Demonstrate method overloading with different number of parameters
        System.out.println("\n2. Method Overloading with Different Number of Parameters:");
        int sum2 = calculator.add(10, 20);
        int sum3 = calculator.add(10, 20, 30);
        int sum4 = calculator.add(10, 20, 30, 40);
        
        System.out.println("   add(10, 20) = " + sum2);
        System.out.println("   add(10, 20, 30) = " + sum3);
        System.out.println("   add(10, 20, 30, 40) = " + sum4);
        
        // Demonstrate method overloading with different parameter order
        System.out.println("\n3. Method Overloading with Different Parameter Order:");
        String result1 = calculator.format(10, "Apples");
        String result2 = calculator.format("Apples", 10);
        
        System.out.println("   format(10, \"Apples\") = " + result1);
        System.out.println("   format(\"Apples\", 10) = " + result2);
        
        // Demonstrate constructor overloading
        System.out.println("\n4. Constructor Overloading:");
        Person person1 = new Person();
        Person person2 = new Person("John");
        Person person3 = new Person("Jane", 25);
        Person person4 = new Person("Smith", 30, "Developer");
        
        System.out.println("   Person1: " + person1.getInfo());
        System.out.println("   Person2: " + person2.getInfo());
        System.out.println("   Person3: " + person3.getInfo());
        System.out.println("   Person4: " + person4.getInfo());
        
        // Demonstrate automatic type conversion
        System.out.println("\n5. Automatic Type Promotion in Method Overloading:");
        calculator.demonstrate(10);      // should call int version
        calculator.demonstrate(10.5f);   // should call float version
        calculator.demonstrate('A');     // should call int version (char is promoted to int)
        calculator.demonstrate(10.5);    // should call double version
        
        System.out.println("\n=== End of Method Overloading Demonstration ===");
    }
}

/**
 * Class to demonstrate method overloading
 */
class CalculationDemo 
{
    // Overloading by different parameter types
    public int add(int a, int b) 
    {
        System.out.println("Calling add(int, int)");
        return a + b;
    }
    
    public double add(double a, double b) 
    {
        System.out.println("Calling add(double, double)");
        return a + b;
    }
    
    public String add(String a, String b) 
    {
        System.out.println("Calling add(String, String)");
        return a + " " + b;
    }
    
    // Overloading by different number of parameters
    public int add(int a, int b, int c) 
    {
        System.out.println("Calling add(int, int, int)");
        return a + b + c;
    }
    
    public int add(int a, int b, int c, int d) 
    {
        System.out.println("Calling add(int, int, int, int)");
        return a + b + c + d;
    }
    
    // Overloading by different parameter order
    public String format(int count, String item) 
    {
        System.out.println("Calling format(int, String)");
        return "Count: " + count + " of " + item;
    }
    
    public String format(String item, int count) 
    {
        System.out.println("Calling format(String, int)");
        return "Item: " + item + ", Quantity: " + count;
    }
    
    // Overloading to demonstrate type promotion
    public void demonstrate(int x) 
    {
        System.out.println("demonstrate(int) called with value: " + x);
    }
    
    public void demonstrate(float x) 
    {
        System.out.println("demonstrate(float) called with value: " + x);
    }
    
    public void demonstrate(double x) 
    {
        System.out.println("demonstrate(double) called with value: " + x);
    }
}

/**
 * Class to demonstrate constructor overloading
 */
class Person 
{
    private String name;
    private int age;
    private String occupation;
    
    // Default constructor
    public Person() 
    {
        this.name = "Unknown";
        this.age = 0;
        this.occupation = "Not specified";
        System.out.println("Person() constructor called");
    }
    
    // Constructor with name parameter
    public Person(String name) 
    {
        this.name = name;
        this.age = 0;
        this.occupation = "Not specified";
        System.out.println("Person(String) constructor called");
    }
    
    // Constructor with name and age parameters
    public Person(String name, int age) 
    {
        this.name = name;
        this.age = age;
        this.occupation = "Not specified";
        System.out.println("Person(String, int) constructor called");
    }
    
    // Constructor with name, age, and occupation parameters
    public Person(String name, int age, String occupation) 
    {
        this.name = name;
        this.age = age;
        this.occupation = occupation;
        System.out.println("Person(String, int, String) constructor called");
    }
    
    // Method to get person information
    public String getInfo() 
    {
        return "Name: " + name + ", Age: " + age + ", Occupation: " + occupation;
    }
}
