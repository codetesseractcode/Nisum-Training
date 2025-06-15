/**
 * Question 6: Sealed Classes and Pattern Matching for Exception Handling
 */

/**
 * Sealed class for payment-related exceptions
 * Only permits specific exception types
 */
sealed class PaymentException extends Exception 
    permits InvalidPaymentMethodException, InvalidOfferAppliedException {
    
    private final String errorCode;
    private final long timestamp;
    
    public PaymentException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getErrorCode() { return errorCode; }
    public long getTimestamp() { return timestamp; }
}

/**
 * Specific exception for invalid payment methods
 */
final class InvalidPaymentMethodException extends PaymentException {
    private final String paymentMethod;
    private final String[] supportedMethods;
    
    public InvalidPaymentMethodException(String paymentMethod, String[] supportedMethods) {
        super(String.format("Invalid payment method: %s. Supported methods: %s", 
              paymentMethod, String.join(", ", supportedMethods)), 
              "INVALID_PAYMENT_METHOD");
        this.paymentMethod = paymentMethod;
        this.supportedMethods = supportedMethods.clone();
    }
    
    public String getPaymentMethod() { return paymentMethod; }
    public String[] getSupportedMethods() { return supportedMethods.clone(); }
}

/**
 * Specific exception for invalid offer applications
 */
final class InvalidOfferAppliedException extends PaymentException {
    private final String offerCode;
    private final String reason;
    private final double attemptedAmount;
    
    public InvalidOfferAppliedException(String offerCode, String reason, double attemptedAmount) {
        super(String.format("Cannot apply offer '%s': %s (Amount: $%.2f)", 
              offerCode, reason, attemptedAmount), 
              "INVALID_OFFER");
        this.offerCode = offerCode;
        this.reason = reason;
        this.attemptedAmount = attemptedAmount;
    }
    
    public String getOfferCode() { return offerCode; }
    public String getReason() { return reason; }
    public double getAttemptedAmount() { return attemptedAmount; }
}

/**
 * Payment service with comprehensive exception handling
 */
class PaymentService {
    
    private static final String[] SUPPORTED_PAYMENT_METHODS = {
        "CREDIT_CARD", "DEBIT_CARD", "PAYPAL", "BANK_TRANSFER", "DIGITAL_WALLET"
    };
    
    private static final String[] VALID_OFFERS = {
        "WELCOME10", "STUDENT20", "HOLIDAY15", "PREMIUM25"
    };
    
    /**
     * Payment method that throws different exceptions based on input
     */
    public void paymentMethod(String paymentType, String offerCode, double amount) 
            throws PaymentException {
        
        System.out.printf("Processing payment: Method=%s, Offer=%s, Amount=$%.2f%n", 
                        paymentType, offerCode, amount);
        
        // Validate payment method
        if (!isValidPaymentMethod(paymentType)) {
            throw new InvalidPaymentMethodException(paymentType, SUPPORTED_PAYMENT_METHODS);
        }
        
        // Validate offer if provided
        if (offerCode != null && !offerCode.isEmpty()) {
            validateOffer(offerCode, amount);
        }
        
        // Simulate payment processing
        System.out.printf("✓ Payment processed successfully: $%.2f via %s%n", amount, paymentType);
        if (offerCode != null && !offerCode.isEmpty()) {
            double discount = calculateDiscount(offerCode, amount);
            System.out.printf("✓ Offer '%s' applied: $%.2f discount%n", offerCode, discount);
        }
    }
    
    private boolean isValidPaymentMethod(String method) {
        for (String supported : SUPPORTED_PAYMENT_METHODS) {
            if (supported.equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }
    
    private void validateOffer(String offerCode, double amount) throws InvalidOfferAppliedException {
        // Check if offer code exists
        boolean validOffer = false;
        for (String valid : VALID_OFFERS) {
            if (valid.equalsIgnoreCase(offerCode)) {
                validOffer = true;
                break;
            }
        }
        
        if (!validOffer) {
            throw new InvalidOfferAppliedException(offerCode, "Offer code not found", amount);
        }
        
        // Apply business rules for offers
        switch (offerCode.toUpperCase()) {
            case "WELCOME10":
                if (amount < 50) {
                    throw new InvalidOfferAppliedException(offerCode, 
                        "Minimum purchase of $50 required", amount);
                }
                break;
                
            case "STUDENT20":
                if (amount > 500) {
                    throw new InvalidOfferAppliedException(offerCode, 
                        "Maximum purchase limit of $500 for student discount", amount);
                }
                break;
                
            case "HOLIDAY15":
                // Simulate time-based validation
                if (System.currentTimeMillis() % 3 == 0) { // Random condition for demo
                    throw new InvalidOfferAppliedException(offerCode, 
                        "Holiday offer has expired", amount);
                }
                break;
                
            case "PREMIUM25":
                if (amount < 200) {
                    throw new InvalidOfferAppliedException(offerCode, 
                        "Premium offer requires minimum $200 purchase", amount);
                }
                break;
        }
    }
    
    private double calculateDiscount(String offerCode, double amount) {
        return switch (offerCode.toUpperCase()) {
            case "WELCOME10" -> amount * 0.10;
            case "STUDENT20" -> amount * 0.20;
            case "HOLIDAY15" -> amount * 0.15;
            case "PREMIUM25" -> amount * 0.25;
            default -> 0.0;
        };
    }
    
    /**
     * Process payment with pattern matching exception handling
     */
    public void processPaymentMethod(String paymentType, String offerCode, double amount) {
        try {
            paymentMethod(paymentType, offerCode, amount);
            
        } catch (PaymentException e) {
            // Pattern matching on sealed class hierarchy
            handlePaymentException(e);
        }
    }
    
    /**
     * Pattern matching exception handler using if-else with instanceof pattern matching
     */
    private void handlePaymentException(PaymentException exception) {
        System.err.printf("%nPayment Exception Occurred:%n");
        System.err.printf("Error Code: %s%n", exception.getErrorCode());
        System.err.printf("Timestamp: %d%n", exception.getTimestamp());
        System.err.printf("Message: %s%n", exception.getMessage());
        
        // Pattern matching with instanceof (works in Java 16+)
        if (exception instanceof InvalidPaymentMethodException ipme) {
            System.err.println("Payment Method Error Details:");
            System.err.printf("  - Invalid method: %s%n", ipme.getPaymentMethod());
            System.err.printf("  - Supported methods: %s%n", 
                            String.join(", ", ipme.getSupportedMethods()));
            System.err.println("Suggestion: Please use one of the supported payment methods");
            
            // Suggest alternative
            suggestAlternativePaymentMethod(ipme.getPaymentMethod());
            
        } else if (exception instanceof InvalidOfferAppliedException ioae) {
            System.err.println("🔍 Offer Application Error Details:");
            System.err.printf("  - Offer code: %s%n", ioae.getOfferCode());
            System.err.printf("  - Reason: %s%n", ioae.getReason());
            System.err.printf("  - Attempted amount: $%.2f%n", ioae.getAttemptedAmount());
            
            // Suggest alternatives
            suggestAlternativeOffers(ioae.getOfferCode(), ioae.getAttemptedAmount());
        }
        
        System.err.println("Payment processing failed\n");
    }
    
    private void suggestAlternativePaymentMethod(String invalidMethod) {
        System.err.println("Alternative payment methods:");
        for (String method : SUPPORTED_PAYMENT_METHODS) {
            if (invalidMethod.length() >= 3 && 
                method.toLowerCase().contains(invalidMethod.toLowerCase().substring(0, 
                Math.min(3, invalidMethod.length())))) {
                System.err.printf("  - %s (similar to your input)%n", method);
            } else {
                System.err.printf("  - %s%n", method);
            }
        }
    }
    
    private void suggestAlternativeOffers(String invalidOffer, double amount) {
        System.err.println("Available offers for your purchase:");
        for (String offer : VALID_OFFERS) {
            String suggestion = switch (offer) {
                case "WELCOME10" -> amount >= 50 ? "✓ Eligible" : "✗ Needs $50+ purchase";
                case "STUDENT20" -> amount <= 500 ? "✓ Eligible" : "✗ Max $500 purchase";
                case "HOLIDAY15" -> "✓ Generally available";
                case "PREMIUM25" -> amount >= 200 ? "✓ Eligible" : "✗ Needs $200+ purchase";
                default -> "? Check eligibility";
            };
            System.err.printf("  - %s: %s%n", offer, suggestion);
        }
    }
}

/**
 * Main class demonstrating the payment service
 */
public class Question6_SealedClassPatternMatching {
    
    public static void main(String[] args) {
        System.out.println("=== Sealed Classes and Pattern Matching Exception Handling ===");
        
        PaymentService paymentService = new PaymentService();
        
        // Test scenarios
        Object[][] testCases = {
            // {paymentMethod, offerCode, amount, description}
            {"CREDIT_CARD", "WELCOME10", 100.0, "Valid payment with valid offer"},
            {"BITCOIN", null, 50.0, "Invalid payment method"},
            {"PAYPAL", "INVALID_OFFER", 75.0, "Valid payment with invalid offer"},
            {"DEBIT_CARD", "WELCOME10", 25.0, "Valid payment but offer requires $50+"},
            {"DIGITAL_WALLET", "STUDENT20", 600.0, "Valid payment but amount exceeds student limit"},
            {"BANK_TRANSFER", "PREMIUM25", 150.0, "Valid payment but premium requires $200+"},
            {"PAYPAL", "HOLIDAY15", 80.0, "Valid payment with time-sensitive offer"},
            {"CREDIT_CARD", "", 200.0, "Valid payment without offer"},
        };
        
        System.out.println("\n=== Testing Different Payment Scenarios ===");
        
        for (int i = 0; i < testCases.length; i++) {
            Object[] testCase = testCases[i];
            String paymentMethod = (String) testCase[0];
            String offerCode = (String) testCase[1];
            Double amount = (Double) testCase[2];
            String description = (String) testCase[3];
            
            System.out.printf("%n--- Test Case %d: %s ---%n", i + 1, description);
            paymentService.processPaymentMethod(paymentMethod, offerCode, amount);
        }
        
        System.out.println("\n=== Demonstrating Exception Hierarchy ===");
        
        try {
            // Create instances to show inheritance
            PaymentException baseException = new InvalidPaymentMethodException("TEST", new String[]{"VALID"});
            System.out.printf("InvalidPaymentMethodException is PaymentException: %s%n", 
                            baseException instanceof PaymentException);
            System.out.printf("Exception class: %s%n", baseException.getClass().getSimpleName());
            System.out.printf("Error code: %s%n", baseException.getErrorCode());
            
        } catch (Exception e) {
            System.err.println("Error demonstrating hierarchy: " + e.getMessage());
        }
        
        System.out.println("\n=== Benefits of Sealed Classes + Pattern Matching ===");
        System.out.println("✓ Controlled inheritance hierarchy");
        System.out.println("✓ Exhaustive pattern matching at compile time");
        System.out.println("✓ Type-safe exception handling");
        System.out.println("✓ Better IDE support and refactoring safety");
        System.out.println("✓ Clear exception taxonomy");
        System.out.println("✓ Prevents unauthorized exception extensions");
          System.out.println("\n=== Java Version Note ===");
        System.out.println("This example uses Java 17+ features:");
        System.out.println("- Sealed classes (Java 17)");
        System.out.println("- Pattern matching for instanceof (Java 16+)");
        System.out.println("- Enhanced switch expressions");
    }
}
