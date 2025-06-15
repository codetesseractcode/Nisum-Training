import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;

/**
 * Question 10: Hierarchical (Layered) Exception Handling
 */

/**
 * Base exception for all application-specific exceptions
 */
abstract class ApplicationException extends Exception {
    private final String errorCode;
    private final LocalDateTime timestamp;
    private final Map<String, Object> context;
    
    protected ApplicationException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
        this.context = new HashMap<>();
    }
    
    protected ApplicationException(String message, String errorCode) {
        this(message, errorCode, null);
    }
    
    public String getErrorCode() { return errorCode; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Map<String, Object> getContext() { return new HashMap<>(context); }
    
    public ApplicationException addContext(String key, Object value) {
        this.context.put(key, value);
        return this;
    }
    
    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

// Data Layer Exceptions
class DataAccessException extends ApplicationException {
    public DataAccessException(String message, Throwable cause) {
        super(message, "DATA_ACCESS_ERROR", cause);
    }
    
    public DataAccessException(String message) {
        super(message, "DATA_ACCESS_ERROR");
    }
}

class EntityNotFoundException extends DataAccessException {
    public EntityNotFoundException(String entityType, Object id) {
        super(String.format("%s with ID '%s' not found", entityType, id));
        addContext("entityType", entityType);
        addContext("entityId", id);
    }
}

class DatabaseConnectionException extends DataAccessException {
    public DatabaseConnectionException(String message, Throwable cause) {
        super("Database connection failed: " + message, cause);
    }
}

// Business Layer Exceptions
class BusinessLogicException extends ApplicationException {
    public BusinessLogicException(String message, String errorCode) {
        super(message, errorCode);
    }
    
    public BusinessLogicException(String message, String errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}

class InsufficientFundsException extends BusinessLogicException {
    public InsufficientFundsException(double available, double requested) {
        super(String.format("Insufficient funds: Available $%.2f, Requested $%.2f", 
              available, requested), "INSUFFICIENT_FUNDS");
        addContext("availableAmount", available);
        addContext("requestedAmount", requested);
    }
}

class InvalidOperationException extends BusinessLogicException {
    public InvalidOperationException(String operation, String reason) {
        super(String.format("Invalid operation '%s': %s", operation, reason), "INVALID_OPERATION");
        addContext("operation", operation);
        addContext("reason", reason);
    }
}

// Presentation Layer Exceptions
class PresentationException extends ApplicationException {
    public PresentationException(String message, String errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}

class ValidationException extends PresentationException {
    public ValidationException(String message, Throwable cause) {
        super("Validation failed: " + message, "VALIDATION_ERROR", cause);
    }
}

class AuthorizationException extends PresentationException {
    public AuthorizationException(String message) {
        super("Access denied: " + message, "AUTHORIZATION_ERROR", null);
    }
}

// =========================== DATA LAYER ===========================

/**
 * Simulated database entities
 */
record User(String id, String name, String email, double balance) {}
record Transaction(String id, String userId, double amount, String type, LocalDateTime timestamp) {}

/**
 * Data Access Layer - handles raw data operations
 */
class UserRepository {
    private static final Map<String, User> users = new HashMap<>();
    private static final Map<String, List<Transaction>> transactions = new HashMap<>();
    private boolean simulateConnectionFailure = false;
    
    static {
        // Initialize test data
        users.put("user1", new User("user1", "Alice Johnson", "alice@example.com", 1000.0));
        users.put("user2", new User("user2", "Bob Smith", "bob@example.com", 500.0));
        transactions.put("user1", new ArrayList<>());
        transactions.put("user2", new ArrayList<>());
    }
    
    public User findById(String userId) throws DataAccessException {
        try {
            checkConnection();
            
            User user = users.get(userId);
            if (user == null) {
                throw new EntityNotFoundException("User", userId);
            }
            
            System.out.printf("[DATA] Retrieved user: %s%n", user.name());
            return user;
            
        } catch (RuntimeException e) {
            throw new DataAccessException("Failed to retrieve user: " + userId, e);
        }
    }
    
    public void updateUserBalance(String userId, double newBalance) throws DataAccessException {
        try {
            checkConnection();
            
            User existingUser = findById(userId);
            User updatedUser = new User(userId, existingUser.name(), existingUser.email(), newBalance);
            users.put(userId, updatedUser);
            
            System.out.printf("[DATA] Updated balance for %s: $%.2f%n", existingUser.name(), newBalance);
            
        } catch (EntityNotFoundException e) {
            throw e; // Re-throw as-is
        } catch (Exception e) {
            throw new DataAccessException("Failed to update user balance", e);
        }
    }
    
    public void saveTransaction(Transaction transaction) throws DataAccessException {
        try {
            checkConnection();
            
            transactions.computeIfAbsent(transaction.userId(), k -> new ArrayList<>())
                       .add(transaction);
            
            System.out.printf("[DATA] Saved transaction: %s $%.2f for user %s%n", 
                            transaction.type(), transaction.amount(), transaction.userId());
            
        } catch (RuntimeException e) {
            throw new DataAccessException("Failed to save transaction", e);
        }
    }
    
    public List<Transaction> getTransactionHistory(String userId) throws DataAccessException {
        try {
            checkConnection();
            
            List<Transaction> userTransactions = transactions.get(userId);
            if (userTransactions == null) {
                throw new EntityNotFoundException("Transaction history", userId);
            }
            
            System.out.printf("[DATA] Retrieved %d transactions for user %s%n", 
                            userTransactions.size(), userId);
            return new ArrayList<>(userTransactions);
            
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException("Failed to retrieve transaction history", e);
        }
    }
    
    private void checkConnection() throws DatabaseConnectionException {
        if (simulateConnectionFailure) {
            throw new DatabaseConnectionException("Connection timeout", 
                new RuntimeException("Network unreachable"));
        }
        
        // Simulate occasional connection issues
        if (Math.random() < 0.1) { // 10% chance
            throw new DatabaseConnectionException("Temporary connection failure", 
                new RuntimeException("Connection pool exhausted"));
        }
    }
    
    public void simulateConnectionFailure(boolean enable) {
        this.simulateConnectionFailure = enable;
    }
}

// =========================== BUSINESS LAYER ===========================

/**
 * Business Logic Layer - implements business rules and orchestrates data operations
 */
class AccountService {
    private final UserRepository userRepository;
    
    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User getAccountInfo(String userId) throws BusinessLogicException {
        try {
            System.out.printf("[BUSINESS] Getting account info for user: %s%n", userId);
            
            User user = userRepository.findById(userId);
            
            // Business rule: Check if account is active (simplified)
            if (user.balance() < 0) {
                throw new InvalidOperationException("getAccountInfo", 
                    "Account is suspended due to negative balance");
            }
            
            return user;
            
        } catch (DataAccessException e) {
            // Transform data layer exception to business layer exception
            throw new BusinessLogicException("Unable to retrieve account information", 
                                           "ACCOUNT_RETRIEVAL_FAILED", e);
        }
    }
    
    public void transferFunds(String fromUserId, String toUserId, double amount) 
            throws BusinessLogicException {
        try {
            System.out.printf("[BUSINESS] Processing transfer: $%.2f from %s to %s%n", 
                            amount, fromUserId, toUserId);
            
            // Business validation
            if (amount <= 0) {
                throw new InvalidOperationException("transferFunds", 
                    "Transfer amount must be positive");
            }
            
            if (fromUserId.equals(toUserId)) {
                throw new InvalidOperationException("transferFunds", 
                    "Cannot transfer to the same account");
            }
            
            // Get source account
            User fromUser = userRepository.findById(fromUserId);
            User toUser = userRepository.findById(toUserId);
            
            // Business rule: Check sufficient funds
            if (fromUser.balance() < amount) {
                throw new InsufficientFundsException(fromUser.balance(), amount);
            }
            
            // Business rule: Check transfer limits
            if (amount > 10000) {
                throw new InvalidOperationException("transferFunds", 
                    "Transfer amount exceeds daily limit of $10,000");
            }
            
            // Perform the transfer (simulate transaction)
            double newFromBalance = fromUser.balance() - amount;
            double newToBalance = toUser.balance() + amount;
            
            userRepository.updateUserBalance(fromUserId, newFromBalance);
            userRepository.updateUserBalance(toUserId, newToBalance);
            
            // Record transactions
            String transactionId = UUID.randomUUID().toString();
            userRepository.saveTransaction(new Transaction(
                transactionId + "-OUT", fromUserId, -amount, "TRANSFER_OUT", LocalDateTime.now()));
            userRepository.saveTransaction(new Transaction(
                transactionId + "-IN", toUserId, amount, "TRANSFER_IN", LocalDateTime.now()));
            
            System.out.printf("[BUSINESS] Transfer completed successfully%n");
            
        } catch (DataAccessException e) {
            throw new BusinessLogicException("Transfer failed due to data access issue", 
                                           "TRANSFER_DATA_ERROR", e);
        } catch (BusinessLogicException e) {
            // Re-throw business exceptions as-is
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Unexpected error during transfer", 
                                           "TRANSFER_UNEXPECTED_ERROR", e);
        }
    }
    
    public List<Transaction> getAccountHistory(String userId) throws BusinessLogicException {
        try {
            System.out.printf("[BUSINESS] Getting account history for user: %s%n", userId);
            
            // Verify user exists first
            userRepository.findById(userId);
            
            List<Transaction> history = userRepository.getTransactionHistory(userId);
            
            // Business rule: Sort by most recent first
            history.sort((t1, t2) -> t2.timestamp().compareTo(t1.timestamp()));
            
            return history;
            
        } catch (DataAccessException e) {
            throw new BusinessLogicException("Unable to retrieve account history", 
                                           "HISTORY_RETRIEVAL_FAILED", e);
        }
    }
}

// =========================== PRESENTATION LAYER ===========================

/**
 * Presentation Layer - handles user interface concerns and transforms business exceptions
 */
class AccountController {
    private final AccountService accountService;
    
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    public void displayAccountInfo(String userId) throws PresentationException {
        try {
            System.out.printf("[PRESENTATION] Displaying account info for: %s%n", userId);
            
            // Presentation layer validation
            if (userId == null || userId.trim().isEmpty()) {
                throw new ValidationException("User ID is required", 
                    new IllegalArgumentException("Empty user ID"));
            }
            
            User user = accountService.getAccountInfo(userId);
            
            // Format and display user information
            System.out.printf("=== Account Information ===%n");
            System.out.printf("Name: %s%n", user.name());
            System.out.printf("Email: %s%n", user.email());
            System.out.printf("Balance: $%.2f%n", user.balance());
            System.out.printf("Status: %s%n", user.balance() >= 0 ? "Active" : "Suspended");
            
        } catch (BusinessLogicException e) {
            // Transform business exception to presentation exception
            String userMessage = createUserFriendlyMessage(e);
            throw new PresentationException(userMessage, "DISPLAY_ACCOUNT_ERROR", e);
        }
    }
    
    public void processTransfer(String fromUserId, String toUserId, double amount, String authToken) 
            throws PresentationException {
        try {
            System.out.printf("[PRESENTATION] Processing transfer request%n");
            
            // Presentation layer security check
            if (!isValidAuthToken(authToken)) {
                throw new AuthorizationException("Invalid or expired authentication token");
            }
            
            // Input validation
            if (fromUserId == null || toUserId == null) {
                throw new ValidationException("Both source and destination user IDs are required", 
                    new IllegalArgumentException("Missing user IDs"));
            }
            
            if (amount <= 0) {
                throw new ValidationException("Transfer amount must be positive", 
                    new IllegalArgumentException("Invalid amount: " + amount));
            }
            
            accountService.transferFunds(fromUserId, toUserId, amount);
            
            System.out.printf("=== Transfer Successful ===%n");
            System.out.printf("Amount: $%.2f%n", amount);
            System.out.printf("From: %s%n", fromUserId);
            System.out.printf("To: %s%n", toUserId);
            System.out.printf("Time: %s%n", LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            
        } catch (BusinessLogicException e) {
            String userMessage = createUserFriendlyMessage(e);
            throw new PresentationException(userMessage, "TRANSFER_ERROR", e);
        }
    }
    
    public void displayTransactionHistory(String userId) throws PresentationException {
        try {
            System.out.printf("[PRESENTATION] Displaying transaction history%n");
            
            if (userId == null || userId.trim().isEmpty()) {
                throw new ValidationException("User ID is required for transaction history", 
                    new IllegalArgumentException("Empty user ID"));
            }
            
            List<Transaction> history = accountService.getAccountHistory(userId);
            
            System.out.printf("=== Transaction History ===%n");
            if (history.isEmpty()) {
                System.out.printf("No transactions found for user %s%n", userId);
            } else {
                System.out.printf("%-20s %-15s %-10s %-20s%n", "Date", "Type", "Amount", "ID");
                System.out.println("-".repeat(65));
                
                for (Transaction tx : history) {
                    System.out.printf("%-20s %-15s $%-9.2f %-20s%n",
                                    tx.timestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                                    tx.type(),
                                    tx.amount(),
                                    tx.id());
                }
            }
            
        } catch (BusinessLogicException e) {
            String userMessage = createUserFriendlyMessage(e);
            throw new PresentationException(userMessage, "HISTORY_DISPLAY_ERROR", e);
        }
    }
    
    private boolean isValidAuthToken(String token) {
        // Simplified auth token validation
        return token != null && token.startsWith("AUTH_") && token.length() > 10;
    }
    
    private String createUserFriendlyMessage(BusinessLogicException e) {
        return switch (e.getErrorCode()) {
            case "INSUFFICIENT_FUNDS" -> "Sorry, you don't have enough funds for this transaction.";
            case "INVALID_OPERATION" -> "This operation is not allowed. Please check your request.";
            case "ACCOUNT_RETRIEVAL_FAILED" -> "We're having trouble accessing your account. Please try again.";
            case "TRANSFER_DATA_ERROR" -> "Transfer could not be completed due to a system issue. Please try again.";
            case "HISTORY_RETRIEVAL_FAILED" -> "Unable to load transaction history at this time.";
            default -> "An error occurred while processing your request.";
        };
    }
}

// =========================== GLOBAL EXCEPTION HANDLER ===========================

/**
 * Global exception handler that demonstrates final exception processing
 */
class GlobalExceptionHandler {
    
    public void handleException(Exception e, String operation) {
        System.err.printf("%n🚨 EXCEPTION HANDLER - Operation: %s 🚨%n", operation);
        
        if (e instanceof ApplicationException appEx) {
            handleApplicationException(appEx);
        } else {
            handleSystemException(e);
        }
        
        // Log the complete exception chain
        logExceptionChain(e);
    }
    
    private void handleApplicationException(ApplicationException e) {
        System.err.printf("Application Exception: %s%n", e.getClass().getSimpleName());
        System.err.printf("Error Code: %s%n", e.getErrorCode());
        System.err.printf("Message: %s%n", e.getMessage());
        System.err.printf("Timestamp: %s%n", e.getFormattedTimestamp());
        
        if (!e.getContext().isEmpty()) {
            System.err.printf("Context:%n");
            e.getContext().forEach((key, value) -> 
                System.err.printf("  %s: %s%n", key, value));
        }
    }
    
    private void handleSystemException(Exception e) {
        System.err.printf("System Exception: %s%n", e.getClass().getSimpleName());
        System.err.printf("Message: %s%n", e.getMessage());
    }
    
    private void logExceptionChain(Throwable e) {
        System.err.printf("%nException Chain:%n");
        int level = 0;
        Throwable current = e;
        
        while (current != null) {
            String indent = "  ".repeat(level);
            System.err.printf("%s%d. %s: %s%n", 
                            indent, level + 1, current.getClass().getSimpleName(), current.getMessage());
            current = current.getCause();
            level++;
        }
        System.err.println();
    }
}

// =========================== MAIN DEMONSTRATION ===========================

public class Question10_HierarchicalExceptionHandling {
    
    private static final GlobalExceptionHandler globalHandler = new GlobalExceptionHandler();
    
    public static void main(String[] args) {
        System.out.println("=== Hierarchical Exception Handling Demonstration ===");
        
        // Setup the service layers
        UserRepository repository = new UserRepository();
        AccountService accountService = new AccountService(repository);
        AccountController controller = new AccountController(accountService);
        
        // Demonstrate various exception scenarios
        demonstrateSuccessfulOperations(controller);
        demonstrateValidationErrors(controller);
        demonstrateBusinessLogicErrors(controller);
        demonstrateDataLayerErrors(repository, controller);
        demonstrateCompleteExceptionBubbling(repository, controller);
        
        System.out.println("\n=== Benefits of Hierarchical Exception Handling ===");
        System.out.println("✓ Layer-specific exception handling and transformation");
        System.out.println("✓ Context enrichment as exceptions bubble up");
        System.out.println("✓ Separation of concerns in error handling");
        System.out.println("✓ User-friendly error messages at presentation layer");
        System.out.println("✓ Technical details preserved for debugging");
        System.out.println("✓ Centralized global exception handling");
    }
    
    private static void demonstrateSuccessfulOperations(AccountController controller) {
        System.out.println("\n=== Successful Operations ===");
        
        try {
            controller.displayAccountInfo("user1");
            controller.processTransfer("user1", "user2", 100.0, "AUTH_12345");
            controller.displayTransactionHistory("user1");
            
        } catch (Exception e) {
            globalHandler.handleException(e, "Successful Operations Demo");
        }
    }
    
    private static void demonstrateValidationErrors(AccountController controller) {
        System.out.println("\n=== Validation Errors (Presentation Layer) ===");
        
        try {
            controller.displayAccountInfo(""); // Empty user ID
        } catch (Exception e) {
            globalHandler.handleException(e, "Empty User ID Validation");
        }
        
        try {
            controller.processTransfer("user1", "user2", -50.0, "AUTH_12345"); // Negative amount
        } catch (Exception e) {
            globalHandler.handleException(e, "Negative Amount Validation");
        }
        
        try {
            controller.processTransfer("user1", "user2", 100.0, "INVALID_TOKEN"); // Bad auth
        } catch (Exception e) {
            globalHandler.handleException(e, "Invalid Auth Token");
        }
    }
    
    private static void demonstrateBusinessLogicErrors(AccountController controller) {
        System.out.println("\n=== Business Logic Errors ===");
        
        try {
            controller.processTransfer("user2", "user1", 1000.0, "AUTH_12345"); // Insufficient funds
        } catch (Exception e) {
            globalHandler.handleException(e, "Insufficient Funds");
        }
        
        try {
            controller.processTransfer("user1", "user1", 100.0, "AUTH_12345"); // Same account transfer
        } catch (Exception e) {
            globalHandler.handleException(e, "Same Account Transfer");
        }
        
        try {
            controller.processTransfer("user1", "user2", 15000.0, "AUTH_12345"); // Exceeds limit
        } catch (Exception e) {
            globalHandler.handleException(e, "Transfer Limit Exceeded");
        }
    }
    
    private static void demonstrateDataLayerErrors(UserRepository repository, AccountController controller) {
        System.out.println("\n=== Data Layer Errors ===");
        
        try {
            controller.displayAccountInfo("nonexistent"); // User not found
        } catch (Exception e) {
            globalHandler.handleException(e, "Non-existent User");
        }
        
        try {
            controller.displayTransactionHistory("user999"); // No transaction history
        } catch (Exception e) {
            globalHandler.handleException(e, "Missing Transaction History");
        }
    }
    
    private static void demonstrateCompleteExceptionBubbling(UserRepository repository, 
                                                           AccountController controller) {
        System.out.println("\n=== Complete Exception Bubbling (Database Failure) ===");
        
        // Simulate database connection failure
        repository.simulateConnectionFailure(true);
        
        try {
            controller.processTransfer("user1", "user2", 50.0, "AUTH_12345");
        } catch (Exception e) {
            globalHandler.handleException(e, "Database Connection Failure");
        } finally {
            repository.simulateConnectionFailure(false);
        }
        
        System.out.println("\n=== Exception Path Analysis ===");
        System.out.println("1. DatabaseConnectionException thrown in Data Layer");
        System.out.println("2. Caught and wrapped as DataAccessException in Repository");
        System.out.println("3. Caught and wrapped as BusinessLogicException in Service");
        System.out.println("4. Caught and wrapped as PresentationException in Controller");
        System.out.println("5. Finally handled by GlobalExceptionHandler");
        System.out.println("6. Each layer adds context and transforms the exception appropriately");
    }
}
