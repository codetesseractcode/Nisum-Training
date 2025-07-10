# Order App - Context Isolation Integration Test

## Assignment 4: Context Isolation Integration Test Implementation

This project demonstrates the implementation of a Context Isolation Integration Test using Spring Boot's testing capabilities.

## Test Implementation Overview

### 1. Context Isolation Integration Test (`ContextIsolationIntegrationTest`)

**Features:**
- ✅ Marked with `@SpringBootTest` - brings up full Spring application context
- ✅ Uses H2 in-memory database and web layer (embedded server)
- ✅ Single `@MockBean` for third-party `PaymentGatewayClient`
- ✅ Executes real HTTP calls using `TestRestTemplate` to `/orders` endpoint
- ✅ Verifies mock interaction exactly once
- ✅ Ensures other beans (`OrderService`, `OrderRepository`) are real and properly wired

**Key Test Assertions:**
1. **Gateway Mock Verification**: Confirms `PaymentGatewayClient.processPayment()` is called exactly once
2. **Real Bean Validation**: Ensures `OrderService` and `OrderRepository` are actual instances (not mocks)
3. **HTTP Integration**: Tests complete HTTP request/response cycle
4. **Database Persistence**: Validates data is actually persisted in H2 database

### 2. Data Layer Slice Test (`OrderRepositorySliceTest`)

**Purpose:** Comparison test using `@DataJpaTest` for performance analysis

**Features:**
- Uses `@DataJpaTest` - loads only JPA repositories and entities
- No web layer, service layer, or additional Spring beans
- Faster startup time due to limited context

## Performance Comparison

### Startup Time Analysis

| Test Type | Context Loaded | Typical Startup Time | Use Case |
|-----------|----------------|---------------------|----------|
| `@SpringBootTest` (Full Context) | Complete application context, web server, all beans | ~3-5 seconds | End-to-end integration testing |
| `@DataJpaTest` (Slice) | Only JPA repositories, entities, database | ~1-2 seconds | Repository layer testing |

### When to Use Each Approach

**Full Context Integration Test (`@SpringBootTest`):**
- ✅ Testing complete application flows
- ✅ Validating HTTP endpoints
- ✅ Integration with external services (mocked)
- ✅ End-to-end behavior verification
- ❌ Slower startup time
- ❌ More resource intensive

**Slice Testing (`@DataJpaTest`):**
- ✅ Fast execution
- ✅ Focused testing of data layer
- ✅ Lightweight
- ❌ Limited scope
- ❌ Cannot test full integration flows

## Architecture

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   OrderController   │─────▶│   OrderService   │─────▶│ PaymentGatewayClient │
│   (Real Bean)    │    │   (Real Bean)    │    │    (Mocked)     │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                                │
                                ▼
                        ┌──────────────────┐
                        │ OrderRepository  │
                        │   (Real Bean)    │
                        └──────────────────┘
                                │
                                ▼
                        ┌──────────────────┐
                        │   H2 Database    │
                        │     (Real)       │
                        └──────────────────┘
```

## Running the Tests

```bash
# Run all tests
mvn test

# Run only integration tests
mvn test -Dtest="*Integration*"

# Run only slice tests
mvn test -Dtest="*Slice*"
```

## Key Benefits of Context Isolation

1. **Realistic Testing**: Uses real HTTP calls and database operations
2. **Selective Mocking**: Only external dependencies are mocked
3. **Full Integration**: Tests the complete request-response cycle
4. **Real Bean Interactions**: Validates actual Spring bean wiring and behavior
5. **Performance Insights**: Demonstrates trade-offs between test speed and coverage

## Test Coverage

- ✅ HTTP endpoint functionality
- ✅ Service layer business logic
- ✅ Database persistence
- ✅ Mock integration verification
- ✅ Error handling and validation
- ✅ Bean wiring and dependency injection
