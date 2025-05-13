/*
 20. Write a Method Overriding Program in Java (runtime or dynamic polymorphism)
     This program demonstrates how method overriding works in inheritance hierarchies.
*/

public class p20_MethodOverriding 
{
    public static void main(String[] args) 
    {
        System.out.println("=== Method Overriding Demonstration ===\n");
        
        // Create instances of the parent and child classes
        System.out.println("Creating objects of different classes:");
        Animal animal = new Animal();
        Dog dog = new Dog();
        Cat cat = new Cat();
        
        // Call methods on each object directly
        System.out.println("\n1. Calling methods directly on objects:");
        System.out.println("Animal object:");
        animal.makeSound();
        animal.eat();
        animal.sleep();
        
        System.out.println("\nDog object:");
        dog.makeSound();
        dog.eat();
        dog.sleep();
        dog.wagTail(); // Dog-specific method
        
        System.out.println("\nCat object:");
        cat.makeSound();
        cat.eat();
        cat.sleep();
        cat.purr(); // Cat-specific method
        
        // Demonstrate polymorphism with parent class references
        System.out.println("\n2. Polymorphism - Using Parent Class References:");
        
        Animal animalRef1 = new Animal(); // Parent reference to parent object
        Animal animalRef2 = new Dog();    // Parent reference to Dog object
        Animal animalRef3 = new Cat();    // Parent reference to Cat object
        
        System.out.println("\nAnimal reference to Animal object:");
        animalRef1.makeSound();
        animalRef1.eat();
        
        System.out.println("\nAnimal reference to Dog object:");
        animalRef2.makeSound(); // Calls Dog's version (overridden)
        animalRef2.eat();       // Calls Dog's version (overridden)
        // animalRef2.wagTail(); // Error: Cannot access Dog-specific method with Animal reference
        
        System.out.println("\nAnimal reference to Cat object:");
        animalRef3.makeSound(); // Calls Cat's version (overridden)
        animalRef3.eat();       // Calls Cat's version (overridden)
        // animalRef3.purr();   // Error: Cannot access Cat-specific method with Animal reference
        
        // Demonstrate runtime determination of method call
        System.out.println("\n3. Runtime Method Determination:");
        Animal[] animals = new Animal[3];
        animals[0] = new Animal();
        animals[1] = new Dog();
        animals[2] = new Cat();
        
        for (int i = 0; i < animals.length; i++) 
        {
            System.out.println("\nAnimal at index " + i + ":");
            animals[i].makeSound(); // The actual method called is determined at runtime
        }
        
        // Demonstrate method overriding with super keyword
        System.out.println("\n4. Using super to Call Parent Method:");
        System.out.println("Creating special dog that uses super.makeSound():");
        SpecialDog specialDog = new SpecialDog();
        specialDog.makeSound();
        
        // Demonstrate overriding in a more complex hierarchy
        System.out.println("\n5. Method Overriding in a Multi-level Hierarchy:");
        Vehicle vehicle = new Vehicle();
        Car car = new Car();
        SportsCar sportsCar = new SportsCar();
        
        vehicle.drive();
        car.drive();
        sportsCar.drive();
        
        System.out.println("\n=== End of Method Overriding Demonstration ===");
    }
}

// Parent class
class Animal 
{
    public void makeSound() 
    {
        System.out.println("Animal makes a generic sound");
    }
    
    public void eat() 
    {
        System.out.println("Animal eats food");
    }
    
    public void sleep() 
    {
        System.out.println("Animal sleeps");
    }
}

// Child class overriding parent methods
class Dog extends Animal 
{
    // Override the makeSound method
    @Override
    public void makeSound() 
    {
        System.out.println("Dog barks: Woof! Woof!");
    }
    
    // Override the eat method
    @Override
    public void eat() 
    {
        System.out.println("Dog eats meat and bones");
    }
    
    // Dog-specific method
    public void wagTail() 
    {
        System.out.println("Dog wags its tail");
    }
}

// Another child class with different implementations
class Cat extends Animal 
{
    // Override the makeSound method
    @Override
    public void makeSound() 
    {
        System.out.println("Cat meows: Meow! Meow!");
    }
    
    // Override the eat method
    @Override
    public void eat() 
    {
        System.out.println("Cat eats fish");
    }
    
    // Cat-specific method
    public void purr() 
    {
        System.out.println("Cat purrs");
    }
}

// Special class that uses super to call parent method
class SpecialDog extends Dog 
{
    @Override
    public void makeSound() 
    {
        System.out.println("Special dog makes a special sound:");
        super.makeSound(); // Call the parent's (Dog) implementation
        System.out.println("And then howls: Hoooowwwl!");
    }
}

// Demonstrate method overriding in a multi-level hierarchy
class Vehicle 
{
    public void drive() 
    {
        System.out.println("Vehicle is moving");
    }
}

class Car extends Vehicle 
{
    @Override
    public void drive() 
    {
        System.out.println("Car is driving on the road");
    }
}

class SportsCar extends Car 
{
    @Override
    public void drive() 
    {
        System.out.println("SportsCar is racing at high speed");
    }
}
