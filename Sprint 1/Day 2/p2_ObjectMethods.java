/*
 2.This program demonstrates some common methods from the Object class
   Methods demonstrated:
   1. toString() - returns a string representation of the object
   2. equals() - compares two objects for equality
   3. hashCode() - returns a hash code value for the object
   4. getClass() - returns the runtime class of the object
*/
public class p2_ObjectMethods 
{
    public static void main(String[] args) 
    {
        System.out.println("=== Object Class Methods Demonstration ===\n");
        
        // Create two Person objects with the same data
        Person person1 = new Person("John", 25);
        Person person2 = new Person("John", 25);
        // Create a different Person object
        Person person3 = new Person("Alice", 30);
        
        // 1. Demonstrate toString() method
        System.out.println("=== toString() Method ===");
        System.out.println("Default toString() for person1: " + person1.toString());
        System.out.println("Default toString() for person2: " + person2.toString());
        
        // 2. Demonstrate equals() method
        System.out.println("\n=== equals() Method ===");
        System.out.println("person1 equals person1 (same reference): " + person1.equals(person1));
        System.out.println("person1 equals person2 (different reference, same data): " + person1.equals(person2));
        System.out.println("person1 equals person3 (different data): " + person1.equals(person3));
        
        // 3. Demonstrate hashCode() method
        System.out.println("\n=== hashCode() Method ===");
        System.out.println("HashCode for person1: " + person1.hashCode());
        System.out.println("HashCode for person2: " + person2.hashCode());
        System.out.println("HashCode for person3: " + person3.hashCode());
        System.out.println("Note: In our implementation, objects with equal values have equal hash codes");
        
        // 4. Demonstrate getClass() method
        System.out.println("\n=== getClass() Method ===");
        System.out.println("Class of person1: " + person1.getClass().getName());
        System.out.println("Is person1 instance of Person: " + (person1 instanceof Person));
        System.out.println("Is person1 instance of Object: " + (person1 instanceof Object));
        
        // Create a different type of object to compare classes
        String text = "Hello";
        System.out.println("Class of text (String): " + text.getClass().getName());
        System.out.println("Is String == Person class: " + (text.getClass().equals(person1.getClass())));
    }
}

/*
 A simple Person class that overrides some Object methods
*/
class Person 
{
    private String name;
    private int age;
    
    public Person(String name, int age) 
    {
        this.name = name;
        this.age = age;
    }
    
    // Override toString() method from Object class
    @Override
    public String toString() 
    {
        return "Person [name=" + name + ", age=" + age + "]";
    }
    
    // Override equals() method from Object class
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Person other = (Person) obj;
        return age == other.age && 
               (name == null ? other.name == null : name.equals(other.name));
    }
    
    // Override hashCode() method from Object class
    @Override
    public int hashCode() 
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
    }
}
