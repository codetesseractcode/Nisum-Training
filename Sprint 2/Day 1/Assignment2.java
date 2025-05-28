// 2. Is it possible to create a custom functional interface? Give an example
public class Assignment2 {
    public static void main(String[] args) {
        System.out.println("Yes, it's possible to create a custom functional interface.");
        System.out.println("Example: Calculator interface with calculate() method");
        
        Calculator addition = (a, b) -> a + b;
        System.out.println("Addition result: " + addition.calculate(10, 5));
    }
}

@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}
