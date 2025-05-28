// 4. You want to add two numbers, but the logic may change later (e.g., multiply instead of add). 
// How will you use a functional interface to make the operation flexible?
public class Assignment4 {
    public static void main(String[] args) {
        MathOperation addition = (a, b) -> a + b;
        MathOperation multiplication = (a, b) -> a * b;
        MathOperation subtraction = (a, b) -> a - b;
        
        System.out.println("Addition: " + performOperation(10, 5, addition));
        System.out.println("Multiplication: " + performOperation(10, 5, multiplication));
        System.out.println("Subtraction: " + performOperation(10, 5, subtraction));
    }
    
    private static int performOperation(int a, int b, MathOperation operation) {
        return operation.calculate(a, b);
    }
}

@FunctionalInterface
interface MathOperation {
    int calculate(int a, int b);
}
