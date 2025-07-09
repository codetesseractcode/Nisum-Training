/**
 * Program to demonstrate functional interfaces in Java
 * 
 * A functional interface has exactly one abstract method and can be used
 * with lambda expressions. They were formalized in Java 8 with @FunctionalInterface
 * annotation.
 * 
 * This program demonstrates:
 * 1. Creating custom functional interfaces
 * 2. Using built-in functional interfaces
 * 3. Implementing with lambda expressions
 * 4. Method references
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class p27_FunctionalInterface {
    public static void main(String[] args) {
        System.out.println("=== Functional Interfaces Demonstration ===\n");
        
        // 1. Custom functional interface with lambda expression
        System.out.println("1. Custom functional interfaces:");
        
        // Using lambda with our custom functional interface
        Greeting indianGreeting = name -> "Namaste, " + name + "!";
        Greeting englishGreeting = name -> "Hello, " + name + "!";
        Greeting teluguGreeting = name -> "Namaskaram, " + name + "!";
        
        System.out.println(indianGreeting.greet("Rahul"));
        System.out.println(englishGreeting.greet("Priya"));
        System.out.println(teluguGreeting.greet("Venkat"));
        
        // Using custom functional interface with method references
        StringProcessor toUpperCase = String::toUpperCase;
        StringProcessor toLowerCase = String::toLowerCase;
        
        System.out.println("Processed: " + toUpperCase.process("india is great"));
        System.out.println("Processed: " + toLowerCase.process("WELCOME TO BANGALORE"));
        System.out.println();
        
        // 2. Using built-in functional interfaces
        System.out.println("2. Built-in functional interfaces:");
        
        // Predicate - takes an object and returns boolean
        System.out.println("Predicate example:");
        Predicate<String> isLongName = name -> name.length() > 5;
        System.out.println("Is 'Suresh' a long name? " + isLongName.test("Suresh"));
        System.out.println("Is 'Ram' a long name? " + isLongName.test("Ram"));
        
        // Function - transforms a value
        System.out.println("\nFunction example:");
        Function<Integer, String> numberToString = num -> {
            if (num == 1) return "One";
            else if (num == 2) return "Two";
            else if (num == 3) return "Three";
            else return "Other number: " + num;
        };
        
        System.out.println("1 as string: " + numberToString.apply(1));
        System.out.println("3 as string: " + numberToString.apply(3));
        System.out.println("5 as string: " + numberToString.apply(5));
        
        // Consumer - accepts a value but doesn't return anything
        System.out.println("\nConsumer example:");
        Consumer<String> printWithPrefix = message -> System.out.println("Message: " + message);
        printWithPrefix.accept("Today is a good day");
        printWithPrefix.accept("Learning Java functional interfaces");
        
        // Supplier - provides a value
        System.out.println("\nSupplier example:");
        Supplier<Double> getRandomPrice = () -> Math.round(Math.random() * 1000 * 100.0) / 100.0;
        System.out.println("Random product price: ₹" + getRandomPrice.get());
        System.out.println("Random product price: ₹" + getRandomPrice.get());
        System.out.println();
        
        // 3. Practical example - data processing with functional interfaces
        System.out.println("3. Practical example - Student data processing:");
        List<Student> students = Arrays.asList(
            new Student("Amit Kumar", 85, "Computer Science"),
            new Student("Priya Shah", 92, "Electronics"),
            new Student("Rahul Singh", 78, "Computer Science"),
            new Student("Divya Patel", 95, "Mathematics"),
            new Student("Rohit Sharma", 67, "Physics"),
            new Student("Ananya Desai", 88, "Computer Science")
        );
        
        System.out.println("All students:");
        printStudents(students, s -> true);
        
        System.out.println("\nComputer Science students:");
        printStudents(students, s -> s.getDepartment().equals("Computer Science"));
        
        System.out.println("\nStudents with marks > 85:");
        printStudents(students, s -> s.getMarks() > 85);
        
        // 4. Functional composition
        System.out.println("\n4. Functional composition:");
        Function<Student, String> getStudentName = Student::getName;
        Function<String, String> introduceStudent = name -> "Student name is " + name;
        
        // Compose functions
        Function<Student, String> introduceStudentByObject = introduceStudent.compose(getStudentName);
        
        System.out.println(introduceStudentByObject.apply(students.get(0)));
        System.out.println(introduceStudentByObject.apply(students.get(1)));
        
        System.out.println("\n=== End of Functional Interfaces Demonstration ===");
    }
    
    // Method that accepts predicate for filtering
    private static void printStudents(List<Student> students, Predicate<Student> predicate) {
        for (Student student : students) {
            if (predicate.test(student)) {
                System.out.println(student);
            }
        }
    }
}

// Custom functional interface
@FunctionalInterface
interface Greeting {
    String greet(String name);
}

// Another custom functional interface
@FunctionalInterface
interface StringProcessor {
    String process(String input);
}

// Class for demonstration
class Student {
    private String name;
    private int marks;
    private String department;
    
    public Student(String name, int marks, String department) {
        this.name = name;
        this.marks = marks;
        this.department = department;
    }
    
    public String getName() {
        return name;
    }
    
    public int getMarks() {
        return marks;
    }
    
    public String getDepartment() {
        return department;
    }
    
    @Override
    public String toString() {
        return name + " (" + department + ") - " + marks + " marks";
    }
}
