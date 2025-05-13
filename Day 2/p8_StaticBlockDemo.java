/*
 8. Write a simple program using static block.
    This program demonstrates how static blocks are used for initialization.
*/

import java.util.ArrayList;
import java.util.List;

public class p8_StaticBlockDemo 
{
    // Static variable to hold a list of prime numbers
    private static List<Integer> primeNumbers;
    
    // Static counter to track how many times the class is used
    private static int accessCounter;
    
    // Static block - executed when the class is loaded into memory
    static 
    {
        System.out.println("Static block is executing...");
        
        // Initialize the static variables
        primeNumbers = new ArrayList<>();
        accessCounter = 0;
        
        // Populate the list with first 10 prime numbers
        System.out.println("Calculating first 10 prime numbers...");
        populatePrimeNumbers();
        
        System.out.println("Static initialization complete!");
    }
    
    // Method to populate prime numbers
    private static void populatePrimeNumbers() 
    {
        int count = 0;
        int num = 2;
        
        while (count < 10) 
        {
            if (isPrime(num)) 
            {
                primeNumbers.add(num);
                count++;
            }
            num++;
        }
    }
    
    // Method to check if a number is prime
    private static boolean isPrime(int number) 
    {
        if (number <= 1) 
        {
            return false;
        }
        
        for (int i = 2; i <= Math.sqrt(number); i++) 
        {
            if (number % i == 0) 
            {
                return false;
            }
        }
        
        return true;
    }
    
    // Static method to get the prime numbers
    public static List<Integer> getPrimeNumbers() 
    {
        accessCounter++;
        return primeNumbers;
    }
    
    // Static method to get the access count
    public static int getAccessCount() 
    {
        return accessCounter;
    }
    
    public static void main(String[] args) 
    {
        System.out.println("\n=== Static Block Demonstration ===\n");
        
        // Notice that the static block has already executed by this point
        System.out.println("Main method begins execution");
        
        System.out.println("\nFirst 10 prime numbers (first access):");
        List<Integer> primes = p8_StaticBlockDemo.getPrimeNumbers();
        for (Integer prime : primes) 
        {
            System.out.print(prime + " ");
        }
        
        System.out.println("\n\nAccessing prime numbers again (second access):");
        p8_StaticBlockDemo.getPrimeNumbers();
        
        System.out.println("Number of times prime numbers accessed: " + 
                         p8_StaticBlockDemo.getAccessCount());
        
        System.out.println("\n=== End of Demonstration ===");
        System.out.println("The static block executed only once when the class was loaded,");
        System.out.println("even though we accessed the static methods multiple times.");
    }
}
