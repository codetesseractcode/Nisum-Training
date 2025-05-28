// 1. What is a functional interface in Java 8? Give one example.
import java.util.function.*;

public class Assignment1 {
    public static void main(String[] args) {
        System.out.println("A functional interface is an interface with exactly one abstract method.");
        System.out.println("Example: Runnable interface with run() method");
        
        Runnable runnable = () -> System.out.println("This is a Runnable functional interface example");
        runnable.run();
    }
}
