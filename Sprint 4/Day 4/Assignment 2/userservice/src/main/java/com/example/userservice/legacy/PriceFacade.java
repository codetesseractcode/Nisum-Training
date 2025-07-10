package com.example.userservice.legacy;

import java.math.BigDecimal;

/**
 * Facade to wrap legacy PriceCalculator and provide exception handling
 * Follows Facade pattern from SOLID principles
 */
public class PriceFacade {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal("100.00");
    private final PriceCalculator priceCalculator;

    public PriceFacade() {
        this.priceCalculator = new PriceCalculator();
    }

    public PriceFacade(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    /**
     * Gets price with exception handling and fallback
     */
    public BigDecimal getPrice(BigDecimal basePrice) {
        try {
            return calculateTax(basePrice);
        } catch (ArithmeticException e) {
            // Return default value instead of propagating exception
            return DEFAULT_PRICE;
        }
    }

    /**
     * Protected method that can be spied on for testing
     * Wraps the legacy calculator's functionality
     */
    protected BigDecimal calculateTax(BigDecimal basePrice) {
        return priceCalculator.calculateTotalPrice(basePrice);
    }

    public BigDecimal getDefaultPrice() {
        return DEFAULT_PRICE;
    }
}
