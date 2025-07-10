package com.example.userservice.legacy;

import java.math.BigDecimal;

/**
 * Legacy PriceCalculator with heavy private method
 */
public class PriceCalculator {

    /**
     * Heavy private method that performs complex tax calculations
     * In a real scenario, this might involve database calls, external API calls, etc.
     */
    private BigDecimal calculateTax(BigDecimal basePrice) {
        // Simulate heavy computation
        try {
            Thread.sleep(100); // Simulate slow operation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Complex tax calculation logic
        if (basePrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ArithmeticException("Invalid base price for tax calculation");
        }

        return basePrice.multiply(new BigDecimal("0.15")); // 15% tax
    }

    /**
     * Public method that uses the heavy private method
     */
    public BigDecimal calculateTotalPrice(BigDecimal basePrice) {
        BigDecimal tax = calculateTax(basePrice);
        return basePrice.add(tax);
    }
}
