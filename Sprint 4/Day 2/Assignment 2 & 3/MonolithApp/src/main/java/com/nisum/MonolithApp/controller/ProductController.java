package com.nisum.MonolithApp.controller;

import com.nisum.MonolithApp.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        // Hardcoded list of products
        List<Product> products = Arrays.asList(
            new Product(1L, "Laptop", "High-performance laptop for work and gaming", 999.99, "Electronics"),
            new Product(2L, "Smartphone", "Latest model smartphone with advanced features", 699.99, "Electronics"),
            new Product(3L, "Coffee Mug", "Ceramic coffee mug with ergonomic design", 15.99, "Home & Kitchen"),
            new Product(4L, "Running Shoes", "Comfortable running shoes for daily exercise", 129.99, "Sports"),
            new Product(5L, "Wireless Headphones", "Noise-cancelling wireless headphones", 199.99, "Electronics")
        );

        return ResponseEntity.ok(products);
    }
}
