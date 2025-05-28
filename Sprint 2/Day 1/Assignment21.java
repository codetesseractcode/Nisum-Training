// 21. Use Supplier<Double> to generate and print 5 random numbers.
import java.util.function.Supplier;

public class Assignment21 {
    public static void main(String[] args) {
        Supplier<Double> randomSupplier = Math::random;
        System.out.println("Generating 5 random numbers using Supplier<Double>:");
        for (int i = 0; i < 5; i++) {
            System.out.println("Random #" + (i+1) + ": " + randomSupplier.get());
        }
        Supplier<Integer> randomIntSupplier = () -> (int) (Math.random() * 100) + 1;
        System.out.println("\nGenerating 5 random integers between 1 and 100:");
        for (int i = 0; i < 5; i++) {
            System.out.println("Random integer #" + (i+1) + ": " + randomIntSupplier.get());
        }
    }
}
