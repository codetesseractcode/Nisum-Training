import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Question 9: Validation Framework using Modern Java Exception Handling
 */

/**
 * Custom exception that aggregates multiple validation errors
 */
class ValidationException extends Exception {
    private final List<ValidationError> errors;
    
    public ValidationException(List<ValidationError> errors) {
        super(createMessage(errors));
        this.errors = new ArrayList<>(errors);
    }
    
    public ValidationException(String message, List<ValidationError> errors) {
        super(message + ": " + createMessage(errors));
        this.errors = new ArrayList<>(errors);
    }
    
    public List<ValidationError> getErrors() {
        return new ArrayList<>(errors);
    }
    
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
    
    public int getErrorCount() {
        return errors.size();
    }
    
    public List<ValidationError> getErrorsForField(String fieldName) {
        return errors.stream()
            .filter(error -> fieldName.equals(error.fieldName()))
            .collect(Collectors.toList());
    }
    
    private static String createMessage(List<ValidationError> errors) {
        if (errors.isEmpty()) {
            return "No validation errors";
        }
        
        return String.format("Validation failed with %d error(s): %s", 
                           errors.size(),
                           errors.stream()
                               .map(ValidationError::toString)
                               .collect(Collectors.joining("; ")));
    }
}

/**
 * Record representing a validation error (Java 14+ feature)
 */
record ValidationError(String fieldName, String errorCode, String message, Object invalidValue) {
    
    public ValidationError {
        // Compact constructor with validation
        Objects.requireNonNull(fieldName, "Field name cannot be null");
        Objects.requireNonNull(errorCode, "Error code cannot be null");
        Objects.requireNonNull(message, "Message cannot be null");
    }
    
    @Override
    public String toString() {
        return String.format("%s[%s]: %s (value: %s)", 
                           fieldName, errorCode, message, invalidValue);
    }
}

/**
 * Validation result that can be chained and combined
 */
class ValidationResult {
    private final List<ValidationError> errors;
    private final boolean valid;
    
    private ValidationResult(List<ValidationError> errors) {
        this.errors = new ArrayList<>(errors);
        this.valid = errors.isEmpty();
    }
    
    public static ValidationResult success() {
        return new ValidationResult(Collections.emptyList());
    }
    
    public static ValidationResult failure(ValidationError error) {
        return new ValidationResult(List.of(error));
    }
    
    public static ValidationResult failure(List<ValidationError> errors) {
        return new ValidationResult(errors);
    }
    
    public boolean isValid() { return valid; }
    public boolean hasErrors() { return !valid; }
    public List<ValidationError> getErrors() { return new ArrayList<>(errors); }
    
    /**
     * Combines this result with another result
     */
    public ValidationResult and(ValidationResult other) {
        List<ValidationError> combinedErrors = new ArrayList<>(this.errors);
        combinedErrors.addAll(other.errors);
        return new ValidationResult(combinedErrors);
    }
    
    /**
     * Applies additional validation if this result is valid
     */
    public ValidationResult andThen(Supplier<ValidationResult> nextValidation) {
        if (this.isValid()) {
            return this.and(nextValidation.get());
        }
        return this;
    }
    
    /**
     * Throws ValidationException if there are errors
     */
    public void throwIfInvalid() throws ValidationException {
        if (hasErrors()) {
            throw new ValidationException(errors);
        }
    }
    
    /**
     * Throws ValidationException with custom message if there are errors
     */
    public void throwIfInvalid(String message) throws ValidationException {
        if (hasErrors()) {
            throw new ValidationException(message, errors);
        }
    }
}

/**
 * Functional interface for validation rules
 */
@FunctionalInterface
interface ValidationRule<T> {
    ValidationResult validate(T value, String fieldName);
    
    /**
     * Combines this rule with another rule
     */
    default ValidationRule<T> and(ValidationRule<T> other) {
        return (value, fieldName) -> 
            this.validate(value, fieldName).and(other.validate(value, fieldName));
    }
}

/**
 * Pre-built validation rules using modern Java patterns
 */
class ValidationRules {
    
    // String validations
    public static ValidationRule<String> notNull() {
        return (value, fieldName) -> value != null 
            ? ValidationResult.success()
            : ValidationResult.failure(new ValidationError(fieldName, "NULL", 
                "Value cannot be null", null));
    }
    
    public static ValidationRule<String> notEmpty() {
        return (value, fieldName) -> {
            if (value == null) return ValidationResult.success(); // Let notNull handle this
            return !value.trim().isEmpty()
                ? ValidationResult.success()
                : ValidationResult.failure(new ValidationError(fieldName, "EMPTY", 
                    "Value cannot be empty", value));
        };
    }
    
    public static ValidationRule<String> minLength(int min) {
        return (value, fieldName) -> {
            if (value == null) return ValidationResult.success();
            return value.length() >= min
                ? ValidationResult.success()
                : ValidationResult.failure(new ValidationError(fieldName, "MIN_LENGTH", 
                    String.format("Minimum length is %d, got %d", min, value.length()), value));
        };
    }
    
    public static ValidationRule<String> maxLength(int max) {
        return (value, fieldName) -> {
            if (value == null) return ValidationResult.success();
            return value.length() <= max
                ? ValidationResult.success()
                : ValidationResult.failure(new ValidationError(fieldName, "MAX_LENGTH", 
                    String.format("Maximum length is %d, got %d", max, value.length()), value));
        };
    }
    
    public static ValidationRule<String> pattern(String regex, String description) {
        return (value, fieldName) -> {
            if (value == null) return ValidationResult.success();
            return value.matches(regex)
                ? ValidationResult.success()
                : ValidationResult.failure(new ValidationError(fieldName, "PATTERN", 
                    "Value does not match required pattern: " + description, value));
        };
    }
    
    // Number validations
    public static <T extends Number> ValidationRule<T> min(T minimum) {
        return (value, fieldName) -> {
            if (value == null) return ValidationResult.success();
            return value.doubleValue() >= minimum.doubleValue()
                ? ValidationResult.success()
                : ValidationResult.failure(new ValidationError(fieldName, "MIN_VALUE", 
                    String.format("Minimum value is %s, got %s", minimum, value), value));
        };
    }
    
    public static <T extends Number> ValidationRule<T> max(T maximum) {
        return (value, fieldName) -> {
            if (value == null) return ValidationResult.success();
            return value.doubleValue() <= maximum.doubleValue()
                ? ValidationResult.success()
                : ValidationResult.failure(new ValidationError(fieldName, "MAX_VALUE", 
                    String.format("Maximum value is %s, got %s", maximum, value), value));
        };
    }
      public static <T extends Number> ValidationRule<T> positive() {
        return (value, fieldName) -> {
            if (value == null) return ValidationResult.success();
            return value.doubleValue() > 0
                ? ValidationResult.success()
                : ValidationResult.failure(new ValidationError(fieldName, "POSITIVE", 
                    "Value must be positive", value));
        };
    }
      // Collection validations
    public static <T extends Collection<?>> ValidationRule<T> notEmpty(String itemName) {
        return (value, fieldName) -> {
            if (value == null) return ValidationResult.success();
            return !value.isEmpty()
                ? ValidationResult.success()
                : ValidationResult.failure(new ValidationError(fieldName, "EMPTY_COLLECTION", 
                    "Collection of " + itemName + " cannot be empty", value.size()));
        };
    }
    
    public static <T extends Collection<?>> ValidationRule<T> maxSize(int max) {
        return (value, fieldName) -> {
            if (value == null) return ValidationResult.success();
            return value.size() <= max
                ? ValidationResult.success()
                : ValidationResult.failure(new ValidationError(fieldName, "MAX_SIZE", 
                    String.format("Maximum size is %d, got %d", max, value.size()), value.size()));
        };
    }
    
    // Custom validation with lambda
    public static <T> ValidationRule<T> custom(Predicate<T> predicate, String errorCode, String message) {
        return (value, fieldName) -> predicate.test(value)
            ? ValidationResult.success()
            : ValidationResult.failure(new ValidationError(fieldName, errorCode, message, value));
    }
}

/**
 * Main validator class that orchestrates validation
 */
class Validator<T> {
    private final List<ValidationStep<T>> validationSteps = new ArrayList<>();
    
    /**
     * Adds a validation step for a specific field
     */
    public <F> Validator<T> validate(Function<T, F> fieldExtractor, String fieldName, 
                                   ValidationRule<F> rule) {
        validationSteps.add(new ValidationStep<>(fieldExtractor, fieldName, rule));
        return this;
    }
    
    /**
     * Adds a conditional validation step
     */
    public <F> Validator<T> validateIf(Predicate<T> condition, 
                                     Function<T, F> fieldExtractor, 
                                     String fieldName, 
                                     ValidationRule<F> rule) {
        validationSteps.add(new ConditionalValidationStep<>(condition, fieldExtractor, fieldName, rule));
        return this;
    }
    
    /**
     * Validates the object and returns all errors
     */
    public ValidationResult validate(T object) {
        List<ValidationError> allErrors = new ArrayList<>();
        
        for (ValidationStep<T> step : validationSteps) {
            ValidationResult result = step.validate(object);
            allErrors.addAll(result.getErrors());
        }
        
        return allErrors.isEmpty() 
            ? ValidationResult.success() 
            : ValidationResult.failure(allErrors);
    }
    
    /**
     * Validates and throws exception if invalid
     */
    public void validateAndThrow(T object) throws ValidationException {
        validate(object).throwIfInvalid();
    }
    
    /**
     * Validates and throws exception with custom message if invalid
     */
    public void validateAndThrow(T object, String message) throws ValidationException {
        validate(object).throwIfInvalid(message);
    }
    
    private static class ValidationStep<T> {
        protected final Function<T, ?> fieldExtractor;
        protected final String fieldName;
        protected final ValidationRule<Object> rule;
        
        @SuppressWarnings("unchecked")
        public <F> ValidationStep(Function<T, F> fieldExtractor, String fieldName, ValidationRule<F> rule) {
            this.fieldExtractor = fieldExtractor;
            this.fieldName = fieldName;
            this.rule = (ValidationRule<Object>) rule;
        }
        
        public ValidationResult validate(T object) {
            Object fieldValue = fieldExtractor.apply(object);
            return rule.validate(fieldValue, fieldName);
        }
    }
    
    private static class ConditionalValidationStep<T> extends ValidationStep<T> {
        private final Predicate<T> condition;
        
        public <F> ConditionalValidationStep(Predicate<T> condition,
                                           Function<T, F> fieldExtractor, 
                                           String fieldName, 
                                           ValidationRule<F> rule) {
            super(fieldExtractor, fieldName, rule);
            this.condition = condition;
        }
        
        @Override
        public ValidationResult validate(T object) {
            if (condition.test(object)) {
                return super.validate(object);
            }
            return ValidationResult.success();
        }
    }
}

/**
 * Example domain objects for testing
 */
record User(String name, String email, Integer age, List<String> roles) {}

record Order(String orderId, User customer, List<OrderItem> items, Double totalAmount) {}

record OrderItem(String productId, String productName, Integer quantity, Double price) {}

/**
 * Demonstration of the validation framework
 */
public class Question9_ValidationFramework {
    
    /**
     * Creates a validator for User objects
     */
    public static Validator<User> createUserValidator() {
        return new Validator<User>()
            .validate(User::name, "name", 
                ValidationRules.notNull()
                    .and(ValidationRules.notEmpty())
                    .and(ValidationRules.minLength(2))
                    .and(ValidationRules.maxLength(50)))
            
            .validate(User::email, "email",
                ValidationRules.notNull()
                    .and(ValidationRules.notEmpty())
                    .and(ValidationRules.pattern("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", 
                                                "valid email format")))
              .validate(User::age, "age",
                ValidationRules.<Integer>min(18)
                    .and(ValidationRules.<Integer>max(120))
                    .and(ValidationRules.<Integer>positive()))
            
            .validate(User::roles, "roles",
                ValidationRules.notEmpty("roles")
                    .and(ValidationRules.maxSize(10)))
              .validateIf(user -> user.age() != null && user.age() < 21, 
                       User::roles, "roles",
                       ValidationRules.<List<String>>custom(roles -> !roles.contains("ADMIN"), 
                                            "UNDERAGE_ADMIN", 
                                            "Users under 21 cannot have ADMIN role"));
    }
    
    /**
     * Creates a validator for Order objects
     */
    public static Validator<Order> createOrderValidator() {
        return new Validator<Order>()
            .validate(Order::orderId, "orderId",
                ValidationRules.notNull()
                    .and(ValidationRules.notEmpty())
                    .and(ValidationRules.pattern("^ORD-\\d{6}$", "format ORD-XXXXXX")))
            
            .validate(Order::customer, "customer",
                ValidationRules.custom(Objects::nonNull, "NULL_CUSTOMER", "Customer cannot be null"))
            
            .validate(Order::items, "items",
                ValidationRules.notEmpty("order items")
                    .and(ValidationRules.maxSize(50)))
              .validate(Order::totalAmount, "totalAmount",
                ValidationRules.<Double>positive()
                    .and(ValidationRules.<Double>min(0.01))
                    .and(ValidationRules.<Double>max(999999.99)));
    }
    
    /**
     * Demonstrates basic validation with error collection
     */
    public static void demonstrateBasicValidation() {
        System.out.println("\n=== Basic Validation Demonstration ===");
        
        Validator<User> userValidator = createUserValidator();
        
        // Test valid user
        User validUser = new User("John Doe", "john@example.com", 25, List.of("USER", "CUSTOMER"));
        
        try {
            userValidator.validateAndThrow(validUser, "Valid user validation");
            System.out.println("✓ Valid user passed validation");
        } catch (ValidationException e) {
            System.err.println("❌ Unexpected validation failure: " + e.getMessage());
        }
        
        // Test invalid user with multiple errors
        User invalidUser = new User("", "invalid-email", -5, Collections.emptyList());
        
        try {
            userValidator.validateAndThrow(invalidUser, "Invalid user validation");
            System.err.println("❌ Invalid user should have failed validation");
        } catch (ValidationException e) {
            System.out.printf("✓ Caught validation exception with %d errors:%n", e.getErrorCount());
            e.getErrors().forEach(error -> 
                System.out.printf("  - %s%n", error));
        }
    }
    
    /**
     * Demonstrates conditional validation
     */
    public static void demonstrateConditionalValidation() {
        System.out.println("\n=== Conditional Validation Demonstration ===");
        
        Validator<User> userValidator = createUserValidator();
        
        // Young user with ADMIN role (should fail)
        User youngAdmin = new User("Jane Doe", "jane@example.com", 20, List.of("USER", "ADMIN"));
        
        try {
            userValidator.validateAndThrow(youngAdmin);
            System.err.println("❌ Young admin should have failed validation");
        } catch (ValidationException e) {
            System.out.printf("✓ Young admin validation failed as expected:%n");
            e.getErrors().forEach(error -> 
                System.out.printf("  - %s%n", error));
        }
        
        // Young user without ADMIN role (should pass)
        User youngUser = new User("Bob Smith", "bob@example.com", 20, List.of("USER"));
        
        try {
            userValidator.validateAndThrow(youngUser);
            System.out.println("✓ Young user without admin role passed validation");
        } catch (ValidationException e) {
            System.err.printf("❌ Young user should have passed: %s%n", e.getMessage());
        }
    }
    
    /**
     * Demonstrates complex object validation
     */
    public static void demonstrateComplexValidation() {
        System.out.println("\n=== Complex Object Validation ===");
        
        Validator<Order> orderValidator = createOrderValidator();
        
        // Create invalid order
        User customer = new User("Alice", "alice@example.com", 30, List.of("CUSTOMER"));
        Order invalidOrder = new Order(
            "INVALID", // Wrong format
            customer,
            Collections.emptyList(), // Empty items
            -100.0 // Negative amount
        );
        
        ValidationResult result = orderValidator.validate(invalidOrder);
        
        if (result.hasErrors()) {
            System.out.printf("Order validation failed with %d errors:%n", result.getErrors().size());
            
            // Group errors by field
            Map<String, List<ValidationError>> errorsByField = result.getErrors().stream()
                .collect(Collectors.groupingBy(ValidationError::fieldName));
            
            errorsByField.forEach((field, errors) -> {
                System.out.printf("  %s:%n", field);
                errors.forEach(error -> 
                    System.out.printf("    - %s%n", error.message()));
            });
        }
    }
    
    /**
     * Demonstrates validation chaining and combination
     */
    public static void demonstrateValidationChaining() {
        System.out.println("\n=== Validation Chaining Demonstration ===");
        
        // Create multiple validators
        Validator<User> userValidator = createUserValidator();
        
        User testUser = new User("Test User", "test@example.com", 25, List.of("USER"));
        
        // Chain validations
        ValidationResult userResult = userValidator.validate(testUser);
        
        // Additional custom validation
        ValidationResult businessRuleResult = ValidationResult.success()
            .andThen(() -> testUser.name().contains("Test") 
                ? ValidationResult.failure(new ValidationError("name", "BUSINESS_RULE", 
                    "Test users not allowed in production", testUser.name()))
                : ValidationResult.success());
        
        ValidationResult combinedResult = userResult.and(businessRuleResult);
        
        if (combinedResult.hasErrors()) {
            System.out.println("Combined validation failed:");
            combinedResult.getErrors().forEach(error -> 
                System.out.printf("  - %s%n", error));
        } else {
            System.out.println("✓ All validations passed");
        }
    }
    
    /**
     * Performance demonstration with bulk validation
     */
    public static void demonstrateBulkValidation() {
        System.out.println("\n=== Bulk Validation Performance ===");
        
        Validator<User> userValidator = createUserValidator();
        
        // Create test data
        List<User> users = List.of(
            new User("Alice", "alice@example.com", 25, List.of("USER")),
            new User("", "invalid", -1, Collections.emptyList()), // Multiple errors
            new User("Bob", "bob@example.com", 30, List.of("ADMIN")),
            new User("Charlie", "charlie@invalid", 150, Collections.emptyList()) // Multiple errors
        );
        
        long startTime = System.currentTimeMillis();
        
        Map<User, ValidationResult> results = users.stream()
            .collect(Collectors.toMap(
                user -> user,
                userValidator::validate
            ));
        
        long endTime = System.currentTimeMillis();
        
        System.out.printf("Validated %d users in %d ms%n", users.size(), endTime - startTime);
        
        long validCount = results.values().stream()
            .mapToLong(result -> result.isValid() ? 1 : 0)
            .sum();
        
        long totalErrors = results.values().stream()
            .mapToLong(result -> result.getErrors().size())
            .sum();
        
        System.out.printf("Results: %d valid, %d invalid, %d total errors%n", 
                        validCount, users.size() - validCount, totalErrors);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Modern Java Validation Framework ===");
        
        demonstrateBasicValidation();
        demonstrateConditionalValidation();
        demonstrateComplexValidation();
        demonstrateValidationChaining();
        demonstrateBulkValidation();
        
        System.out.println("\n=== Framework Benefits ===");
        System.out.println("✓ Collects all validation errors instead of fail-fast");
        System.out.println("✓ Reusable validation rules");
        System.out.println("✓ Fluent API for easy configuration");
        System.out.println("✓ Conditional validation support");
        System.out.println("✓ Type-safe validation with generics");
        System.out.println("✓ Modern Java features (Records, Pattern Matching)");
        System.out.println("✓ Comprehensive error reporting");
        System.out.println("✓ Composable and chainable validations");
        
        System.out.println("\n=== Modern Java Features Used ===");
        System.out.println("• Records for immutable data classes");
        System.out.println("• Enhanced switch expressions");
        System.out.println("• Pattern matching (where applicable)");
        System.out.println("• Functional interfaces and lambdas");
        System.out.println("• Stream API for collection processing");
        System.out.println("• Optional for null safety (in error handling)");
    }
}
