/**
 * Question 1: Division with ArithmeticException handling
 */
public class Question1_DivisionException {
    
    /**
     * Performs division of two integers
     * @param dividend The number to be divided
     * @param divisor The number to divide by
     * @return The result of division
     * @throws ArithmeticException if divisor is zero
     */
    public static double divide(int dividend, int divisor) throws ArithmeticException {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return (double) dividend / divisor;
    }
    
    public static void main(String[] args) {
        // Test cases with different inputs
        int[][] testCases = {
            {10, 2},    // Normal case
            {15, 3},    // Normal case
            {8, 0},     // Exception case
            {-10, 5},   // Negative dividend
            {20, -4},   // Negative divisor
            {0, 5},     // Zero dividend
            {100, 0}    // Another exception case
        };
        
        System.out.println("=== Division Operation Results ===");
        
        for (int[] testCase : testCases) {
            int dividend = testCase[0];
            int divisor = testCase[1];
            
            try {
                double result = divide(dividend, divisor);
                System.out.printf("Result of %d ÷ %d = %.2f%n", dividend, divisor, result);
            } catch (ArithmeticException e) {
                System.err.printf("Error: Cannot divide %d by %d - %s%n", 
                                dividend, divisor, e.getMessage());
            }
        }
        
        System.out.println("\n=== Interactive Division ===");
        // Additional interactive example
        try {
            System.out.println("Attempting risky division...");
            double result1 = divide(100, 0); // This will throw exception
            System.out.println("This line won't execute");
        } catch (ArithmeticException e) {
            System.err.println("Caught ArithmeticException: " + e.getMessage());
            System.out.println("Program continues after handling exception");
        } finally {
            System.out.println("Finally block always executes");
        }
    }
}
