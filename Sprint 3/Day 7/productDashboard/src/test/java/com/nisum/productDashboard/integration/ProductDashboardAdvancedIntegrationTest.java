package com.nisum.productDashboard.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.productDashboard.entity.Product;
import com.nisum.productDashboard.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("Product Dashboard Advanced Integration Tests")
class ProductDashboardAdvancedIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Test bulk product operations - Create, Read, Update, Delete multiple products")
    void testBulkProductOperations() throws Exception {
        // Create multiple products
        List<Product> products = List.of(
            new Product("MacBook Pro", "Apple laptop", new BigDecimal("2499.99"), 15, "Electronics"),
            new Product("iPhone 15", "Latest iPhone", new BigDecimal("999.99"), 30, "Electronics"),
            new Product("AirPods Pro", "Wireless earbuds", new BigDecimal("249.99"), 50, "Electronics"),
            new Product("Coffee Table", "Wooden coffee table", new BigDecimal("299.99"), 8, "Furniture"),
            new Product("Office Chair", "Ergonomic office chair", new BigDecimal("199.99"), 12, "Furniture")
        );

        // Create all products
        for (Product product : products) {
            mockMvc.perform(post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(product)))
                    .andExpect(status().isCreated());
        }

        // Verify all products were created
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));

        // Test category filtering
        mockMvc.perform(get("/products/category/Electronics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        mockMvc.perform(get("/products/category/Furniture"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Test edge cases for stock quantity management")
    void testStockQuantityEdgeCases() throws Exception {
        // Create products with various stock levels
        Product zeroStock = new Product("Out of Stock Item", "Currently unavailable",
                                      new BigDecimal("19.99"), 0, "Test");
        Product lowStock = new Product("Low Stock Item", "Almost sold out",
                                     new BigDecimal("29.99"), 3, "Test");
        Product highStock = new Product("High Stock Item", "Plenty available",
                                      new BigDecimal("39.99"), 1000, "Test");

        // Create all products
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(zeroStock)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lowStock)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(highStock)))
                .andExpect(status().isCreated());

        // Test low stock filtering with different thresholds
        mockMvc.perform(get("/products/low-stock").param("threshold", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // zero stock and low stock
                .andExpect(jsonPath("$[*].stockQuantity", everyItem(lessThan(5))));

        mockMvc.perform(get("/products/low-stock").param("threshold", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // only zero stock
                .andExpect(jsonPath("$[0].stockQuantity", is(0)));

        mockMvc.perform(get("/products/low-stock").param("threshold", "2000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))); // all products
    }

    @Test
    @DisplayName("Test comprehensive validation scenarios")
    void testComprehensiveValidation() throws Exception {
        // Test empty name
        Product emptyName = new Product("", "Valid description",
                                      new BigDecimal("10.00"), 5, "Valid");
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emptyName)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.name", notNullValue()));

        // Test null price
        Product nullPrice = new Product("Valid Name", "Valid description",
                                      null, 5, "Valid");
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nullPrice)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.price", notNullValue()));

        // Test negative stock quantity
        Product negativeStock = new Product("Valid Name", "Valid description",
                                          new BigDecimal("10.00"), -1, "Valid");
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(negativeStock)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.stockQuantity", notNullValue()));

        // Test name too long (exceeds 100 characters)
        String longName = "A".repeat(101);
        Product longNameProduct = new Product(longName, "Valid description",
                                            new BigDecimal("10.00"), 5, "Valid");
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(longNameProduct)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.name", notNullValue()));

        // Test description too long (exceeds 500 characters)
        String longDescription = "A".repeat(501);
        Product longDescProduct = new Product("Valid Name", longDescription,
                                            new BigDecimal("10.00"), 5, "Valid");
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(longDescProduct)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.description", notNullValue()));

        // Test multiple validation errors
        Product multipleErrors = new Product("", "Valid description",
                                           new BigDecimal("-5.00"), -10, "");
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(multipleErrors)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors", aMapWithSize(greaterThan(1))));
    }

    @Test
    @DisplayName("Test concurrent operations simulation")
    void testConcurrentOperationsSimulation() throws Exception {
        // Create a base product
        Product baseProduct = new Product("Concurrent Test Product", "Test concurrent operations",
                                        new BigDecimal("100.00"), 50, "Test");

        String createResponse = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(baseProduct)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Product createdProduct = objectMapper.readValue(createResponse, Product.class);
        Long productId = createdProduct.getId();

        // Simulate multiple read operations
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(get("/products/{id}", productId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(productId.intValue())));
        }

        // Simulate multiple update operations
        for (int i = 1; i <= 3; i++) {
            Product updateData = new Product("Updated Product " + i,
                                           "Updated description " + i,
                                           new BigDecimal("100.00").add(BigDecimal.valueOf(i * 10)),
                                           50 - i, "Test");

            mockMvc.perform(put("/products/{id}", productId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateData)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is("Updated Product " + i)))
                    .andExpect(jsonPath("$.stockQuantity", is(50 - i)));
        }

        // Verify final state
        mockMvc.perform(get("/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Product 3")))
                .andExpect(jsonPath("$.stockQuantity", is(47)));
    }

    @Test
    @DisplayName("Test performance with large dataset")
    void testPerformanceWithLargeDataset() throws Exception {
        // Create 50 products to test performance
        List<Product> products = IntStream.range(1, 51)
                .mapToObj(i -> new Product(
                    "Product " + i,
                    "Description for product " + i,
                    new BigDecimal("10.00").multiply(BigDecimal.valueOf(i)),
                    i % 100, // Stock quantity between 0-99
                    i % 2 == 0 ? "Electronics" : "Furniture"
                ))
                .toList();

        // Bulk create products
        for (Product product : products) {
            mockMvc.perform(post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(product)))
                    .andExpect(status().isCreated());
        }

        // Test retrieval performance
        long startTime = System.currentTimeMillis();

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(50)));

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Assert reasonable response time (less than 5 seconds)
        assert duration < 5000 : "Response time too slow: " + duration + "ms";

        // Test category filtering performance
        mockMvc.perform(get("/products/category/Electronics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(25)));

        mockMvc.perform(get("/products/category/Furniture"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(25)));
    }

    @Test
    @DisplayName("Test boundary values for decimal precision")
    void testDecimalPrecisionBoundaries() throws Exception {
        // Test maximum decimal precision
        Product highPrecisionProduct = new Product("High Precision Product",
                                                 "Test decimal precision",
                                                 new BigDecimal("99999999.99"),
                                                 1, "Test");

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(highPrecisionProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.price", is(99999999.99)));

        // Test minimum valid price
        Product minPriceProduct = new Product("Min Price Product",
                                            "Minimum price test",
                                            new BigDecimal("0.01"),
                                            1, "Test");

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(minPriceProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.price", is(0.01)));

        // Test very small decimal
        Product smallDecimalProduct = new Product("Small Decimal Product",
                                                "Small decimal test",
                                                new BigDecimal("0.99"),
                                                1, "Test");

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(smallDecimalProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.price", is(0.99)));
    }

    @Test
    @DisplayName("Test error handling consistency")
    void testErrorHandlingConsistency() throws Exception {
        // Test 404 scenarios consistency
        mockMvc.perform(get("/products/99999"))
                .andExpect(status().isNotFound());

        mockMvc.perform(put("/products/99999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new Product("Test", "Test", new BigDecimal("10.00"), 1, "Test"))))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/products/99999"))
                .andExpect(status().isNotFound());

        // Test malformed JSON
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{invalid json}"))
                .andExpect(status().isBadRequest());

        // Test wrong content type
        mockMvc.perform(post("/products")
                .contentType(MediaType.TEXT_PLAIN)
                .content("plain text"))
                .andExpect(status().isUnsupportedMediaType());
    }
}
