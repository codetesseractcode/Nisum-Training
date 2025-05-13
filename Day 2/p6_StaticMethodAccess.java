/*
 6. Write a simple easy to understand program to access static method in parent class from child class.
    This program uses animals as an example to make it easy for kids to understand inheritance.
*/

public class p6_StaticMethodAccess 
{
    public static void main(String[] args) 
    {
        System.out.println("=== Animal Kingdom Program ===");
        System.out.println("Learning about static methods in parent and child classes\n");
        
        // Using the static method directly from Animal class
        System.out.println("1. Calling static method directly from Animal class:");
        Animal.makeNoise();
        
        System.out.println("\n2. Creating a Dog object and calling its methods:");
        Dog dog = new Dog();
        dog.introduceYourself();  // Non-static method of Dog
        dog.dogSpecificNoise();   // Non-static method of Dog calling parent's static method
        
        System.out.println("\n3. Calling static method from Dog class without creating object:");
        Dog.makeNoise();  // Static method inherited from Animal
    }
}

// Parent class with a static method
class Animal 
{
    // This is a static method in the parent class
    public static void makeNoise() 
    {
        System.out.println("Animals make different sounds!");
    }
}

// Child class that extends Animal
class Dog extends Animal 
{
    // Non-static method in the child class
    public void introduceYourself() 
    {
        System.out.println("Hi! I am a dog. I can bark!");
    }
    
    // Non-static method that calls the parent's static method
    public void dogSpecificNoise() 
    {
        System.out.println("As a dog, I'll use my parent's static method to explain:");
        
        // Three different ways to access the static method from parent class
        
        // Way 1: Using the parent class name (recommended way)
        System.out.println("Way 1 (using parent class name):");
        Animal.makeNoise();
        
        // Way 2: Using the child class name (works because static methods are inherited)
        System.out.println("Way 2 (using child class name):");
        Dog.makeNoise();
        
        // Way 3: Using without any class name (works but less clear)
        System.out.println("Way 3 (using without class name):");
        makeNoise();
        
        System.out.println("And specifically, I say WOOF WOOF!");
    }
}
