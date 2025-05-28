// 6. What's the difference between Function<T, R> and BiFunction<T, U, R>?
import java.util.function.*;

public class Assignment6 {
    public static void main(String[] args) {
  
        Function<Integer, String> function = num -> "Number: " + num;
        
        BiFunction<Integer, Integer, String> biFunction = 
            (num1, num2) -> "Sum: " + (num1 + num2);
        
        System.out.println("Function (takes one argument): " + function.apply(10));
        System.out.println("BiFunction (takes two arguments): " + biFunction.apply(10, 5));
        
        System.out.println("\nDifference:");
        System.out.println("Function<T,R> takes one input of type T and returns output of type R");
        System.out.println("BiFunction<T,U,R> takes two inputs of types T and U and returns output of type R");
    }
}
