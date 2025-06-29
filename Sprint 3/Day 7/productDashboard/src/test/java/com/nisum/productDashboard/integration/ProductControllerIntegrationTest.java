package com.nisum.productDashboard.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.productDashboard.entity.Product;
import com.nisum.productDashboard.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Product testProduct1;
    private Product testProduct2;
    private Product testProduct3;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();

        testProduct1 = new Product("Laptop", "High-performance laptop",
                                 new BigDecimal("999.99"), 50, "Electronics");
        testProduct2 = new Product("Smartphone", "Latest smartphone model",
                                 new BigDecimal("599.99"), 25, "Electronics");
        testProduct3 = new Product("Coffee Mug", "Ceramic coffee mug",
                                 new BigDecimal("12.99"), 5, "Home & Kitchen");
    }

    @Test
    void testGetAllProducts_WhenNoProducts_ReturnsEmptyList() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testGetAllProducts_WhenProductsExist_ReturnsAllProducts() throws Exception {
        // Given
        productRepository.saveAll(List.of(testProduct1, testProduct2, testProduct3));

        // When & Then
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Laptop")))
                .andExpect(jsonPath("$[0].price", is(999.99)))
                .andExpect(jsonPath("$[0].stockQuantity", is(50)))
                .andExpect(jsonPath("$[0].category", is("Electronics")))
                .andExpect(jsonPath("$[1].name", is("Smartphone")))
                .andExpect(jsonPath("$[2].name", is("Coffee Mug")));
    }

    @Test
    void testGetProductById_WhenProductExists_ReturnsProduct() throws Exception {
        // Given
        Product savedProduct = productRepository.save(testProduct1);

        // When & Then
        mockMvc.perform(get("/products/{id}", savedProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedProduct.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Laptop")))
                .andExpect(jsonPath("$.description", is("High-performance laptop")))
                .andExpect(jsonPath("$.price", is(999.99)))
                .andExpect(jsonPath("$.stockQuantity", is(50)))
                .andExpect(jsonPath("$.category", is("Electronics")));
    }

    @Test
    void testGetProductById_WhenProductNotExists_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/products/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProduct_WithValidData_ReturnsCreatedProduct() throws Exception {
        // Given
        Product newProduct = new Product("Gaming Mouse", "RGB gaming mouse",
                                       new BigDecimal("79.99"), 30, "Electronics");

        // When & Then
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Gaming Mouse")))
                .andExpect(jsonPath("$.description", is("RGB gaming mouse")))
                .andExpect(jsonPath("$.price", is(79.99)))
                .andExpect(jsonPath("$.stockQuantity", is(30)))
                .andExpect(jsonPath("$.category", is("Electronics")));
    }

    @Test
    void testCreateProduct_WithInvalidData_ReturnsBadRequest() throws Exception {
        // Given - Product with null name (violates @NotBlank)
        Product invalidProduct = new Product();
        invalidProduct.setName(null);
        invalidProduct.setDescription("Test description");
        invalidProduct.setPrice(new BigDecimal("10.00"));
        invalidProduct.setStockQuantity(5);
        invalidProduct.setCategory("Test");

        // When & Then
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateProduct_WithNegativePrice_ReturnsBadRequest() throws Exception {
        // Given
        Product invalidProduct = new Product("Test Product", "Test description",
                                           new BigDecimal("-10.00"), 5, "Test");

        // When & Then
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateProduct_WhenProductExists_ReturnsUpdatedProduct() throws Exception {
        // Given
        Product savedProduct = productRepository.save(testProduct1);
        Product updatedData = new Product("Updated Laptop", "Updated description",
                                        new BigDecimal("1199.99"), 40, "Electronics");

        // When & Then
        mockMvc.perform(put("/products/{id}", savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedData)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedProduct.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Updated Laptop")))
                .andExpect(jsonPath("$.description", is("Updated description")))
                .andExpect(jsonPath("$.price", is(1199.99)))
                .andExpect(jsonPath("$.stockQuantity", is(40)))
                .andExpect(jsonPath("$.category", is("Electronics")));
    }

    @Test
    void testUpdateProduct_WhenProductNotExists_ReturnsNotFound() throws Exception {
        // Given
        Product updatedData = new Product("Non-existent Product", "Description",
                                        new BigDecimal("100.00"), 10, "Category");

        // When & Then
        mockMvc.perform(put("/products/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedData)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProduct_WhenProductExists_ReturnsNoContent() throws Exception {
        // Given
        Product savedProduct = productRepository.save(testProduct1);

        // When & Then
        mockMvc.perform(delete("/products/{id}", savedProduct.getId()))
                .andExpect(status().isNoContent());

        // Verify product is deleted
        mockMvc.perform(get("/products/{id}", savedProduct.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProduct_WhenProductNotExists_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/products/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetProductsByCategory_ReturnsFilteredProducts() throws Exception {
        // Given
        productRepository.saveAll(List.of(testProduct1, testProduct2, testProduct3));

        // When & Then
        mockMvc.perform(get("/products/category/{category}", "Electronics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].category", is("Electronics")))
                .andExpect(jsonPath("$[1].category", is("Electronics")));
    }

    @Test
    void testGetLowStockProducts_WithDefaultThreshold_ReturnsLowStockProducts() throws Exception {
        // Given
        productRepository.saveAll(List.of(testProduct1, testProduct2, testProduct3));

        // When & Then (default threshold is 10)
        mockMvc.perform(get("/products/low-stock"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Coffee Mug")))
                .andExpect(jsonPath("$[0].stockQuantity", is(5)));
    }

    @Test
    void testGetLowStockProducts_WithCustomThreshold_ReturnsFilteredProducts() throws Exception {
        // Given
        productRepository.saveAll(List.of(testProduct1, testProduct2, testProduct3));

        // When & Then (threshold = 30)
        mockMvc.perform(get("/products/low-stock")
                .param("threshold", "30"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].stockQuantity", lessThan(30)))
                .andExpect(jsonPath("$[1].stockQuantity", lessThan(30)));
    }

    @Test
    void testCreateMultipleProducts_AndVerifyPersistence() throws Exception {
        // Create first product
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testProduct1)))
                .andExpect(status().isCreated());

        // Create second product
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testProduct2)))
                .andExpect(status().isCreated());

        // Verify both products exist
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testCompleteProductLifecycle() throws Exception {
        // Create product
        String createResponse = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testProduct1)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Product createdProduct = objectMapper.readValue(createResponse, Product.class);
        Long productId = createdProduct.getId();

        // Read product
        mockMvc.perform(get("/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Laptop")));

        // Update product
        Product updatedData = new Product("Gaming Laptop", "High-end gaming laptop",
                                        new BigDecimal("1299.99"), 30, "Electronics");

        mockMvc.perform(put("/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Gaming Laptop")))
                .andExpect(jsonPath("$.price", is(1299.99)));

        // Delete product
        mockMvc.perform(delete("/products/{id}", productId))
                .andExpect(status().isNoContent());

        // Verify deletion
        mockMvc.perform(get("/products/{id}", productId))
                .andExpect(status().isNotFound());
    }
}
