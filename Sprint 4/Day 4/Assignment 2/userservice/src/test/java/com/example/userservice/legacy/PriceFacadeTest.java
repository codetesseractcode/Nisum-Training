package com.example.userservice.legacy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * JUnit 4 test demonstrating spy usage, exception handling, and reset functionality
 */
@RunWith(MockitoJUnitRunner.class)
public class PriceFacadeTest {

    @Spy
    private PriceFacade priceFacade;

    private BigDecimal testBasePrice;
    private BigDecimal expectedDefaultPrice;

    @Before
    public void setUp() {
        testBasePrice = new BigDecimal("100.00");
        expectedDefaultPrice = new BigDecimal("100.00");
    }

    @Test
    public void testCalculateTaxThrowsException_ReturnsDefaultValue() {
        // Given: Stub the calculateTax method to throw ArithmeticException
        doThrow(new ArithmeticException("Tax calculation failed"))
                .when(priceFacade).calculateTax(any(BigDecimal.class));

        // When: Call getPrice which should handle the exception
        BigDecimal result = priceFacade.getPrice(testBasePrice);

        // Then: Verify that default value is returned instead of propagating exception
        assertEquals("Should return default price when exception occurs", expectedDefaultPrice, result);

        // Verify that calculateTax was called
        verify(priceFacade, times(1)).calculateTax(testBasePrice);
    }

    @Test
    public void testCalculateTaxWithStubbing_ReturnsStubValue() {
        // Given: Stub the calculateTax method to return a specific value
        BigDecimal expectedPrice = new BigDecimal("150.00");
        doReturn(expectedPrice).when(priceFacade).calculateTax(any(BigDecimal.class));

        // When: Call getPrice
        BigDecimal result = priceFacade.getPrice(testBasePrice);

        // Then: Verify stubbed value is returned
        assertEquals("Should return stubbed price", expectedPrice, result);
        verify(priceFacade, times(1)).calculateTax(testBasePrice);
    }

    @Test
    public void testSpyReset_AndRealInvocation() {
        // Given: First stub the method to throw exception
        doThrow(new ArithmeticException("Initial exception"))
                .when(priceFacade).calculateTax(any(BigDecimal.class));

        // When: Call method with stubbed behavior
        BigDecimal resultWithException = priceFacade.getPrice(testBasePrice);

        // Then: Verify exception handling works
        assertEquals("Should return default price with exception", expectedDefaultPrice, resultWithException);

        // Reset the spy to clear all stubbing
        reset(priceFacade);

        // When: Call method after reset (should use real implementation)
        BigDecimal realResult = priceFacade.getPrice(testBasePrice);

        // Then: Verify real calculation is performed (100 + 15% tax = 115)
        assertEquals("Should return real calculated price after reset", 0, 
                    new BigDecimal("115.00").compareTo(realResult));
        
        // Verify that the method was called on the reset spy
        verify(priceFacade, times(1)).calculateTax(testBasePrice);
    }

    @Test
    public void testMultipleStubs_WithDifferentBehaviors() {
        // Given: Set up multiple different behaviors
        doThrow(new ArithmeticException("First call fails"))
                .doReturn(new BigDecimal("200.00"))
                .doThrow(new ArithmeticException("Third call fails"))
                .when(priceFacade).calculateTax(any(BigDecimal.class));

        // When & Then: First call throws exception
        BigDecimal firstResult = priceFacade.getPrice(testBasePrice);
        assertEquals("First call should return default price", expectedDefaultPrice, firstResult);

        // When & Then: Second call returns stubbed value
        BigDecimal secondResult = priceFacade.getPrice(testBasePrice);
        assertEquals("Second call should return stubbed price", new BigDecimal("200.00"), secondResult);

        // When & Then: Third call throws exception again
        BigDecimal thirdResult = priceFacade.getPrice(testBasePrice);
        assertEquals("Third call should return default price", expectedDefaultPrice, thirdResult);

        // Verify all interactions
        verify(priceFacade, times(3)).calculateTax(testBasePrice);
    }

    @Test
    public void testRealMethodExecution_WithoutStubbing() {
        // When: Call method without any stubbing (real method execution)
        BigDecimal result = priceFacade.getPrice(testBasePrice);

        // Then: Verify real calculation result (100 + 15% tax = 115)
        assertEquals("Should return real calculated price", 0, 
                    new BigDecimal("115.00").compareTo(result));
        
        // Verify method was called
        verify(priceFacade, times(1)).calculateTax(testBasePrice);
    }

    @Test
    public void testExceptionHandling_WithNegativePrice() {
        // Given: Use real implementation which should throw exception for negative price
        BigDecimal negativePrice = new BigDecimal("-50.00");

        // When: Call with negative price (real implementation throws exception)
        BigDecimal result = priceFacade.getPrice(negativePrice);

        // Then: Verify default value is returned due to exception handling
        assertEquals("Should return default price for negative input", expectedDefaultPrice, result);

        verify(priceFacade, times(1)).calculateTax(negativePrice);
    }

    @Test
    public void testPartialMocking_SomeMethodsStubbed() {
        // Given: Stub only calculateTax, leave other methods as real
        BigDecimal stubValue = new BigDecimal("250.00");
        doReturn(stubValue).when(priceFacade).calculateTax(any(BigDecimal.class));

        // When: Call getPrice (uses stubbed calculateTax)
        BigDecimal priceResult = priceFacade.getPrice(testBasePrice);

        // And: Call getDefaultPrice (uses real method)
        BigDecimal defaultResult = priceFacade.getDefaultPrice();

        // Then: Verify mixed behavior
        assertEquals("Should return stubbed price", stubValue, priceResult);
        assertEquals("Should return real default price", expectedDefaultPrice, defaultResult);

        verify(priceFacade, times(1)).calculateTax(testBasePrice);
        // getDefaultPrice is not spied, so no verification needed
    }
}
