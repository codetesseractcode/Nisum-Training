// 16. Define MathOperation with method int operate(int a, int b). 
// Implement it with lambdas for add, subtract, and multiply.
public class Assignment16 {
    public static void main(String[] args) {
        MathOperation addition = (a, b) -> a + b;
        MathOperation subtraction = (a, b) -> a - b;
        MathOperation multiplication = (a, b) -> a * b;
        
        System.out.println("Addition: " + addition.operate(10, 5));
        System.out.println("Subtraction: " + subtraction.operate(10, 5));
        System.out.println("Multiplication: " + multiplication.operate(10, 5));
    }
}

@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}
