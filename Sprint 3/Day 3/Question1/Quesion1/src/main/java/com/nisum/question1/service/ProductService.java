package com.nisum.question1.service;

import com.nisum.question1.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    public ProductService() {
        // Add some initial product data
        products.add(new Product(idCounter.incrementAndGet(), "Laptop", 999.99));
        products.add(new Product(idCounter.incrementAndGet(), "Smartphone", 599.99));
        products.add(new Product(idCounter.incrementAndGet(), "Headphones", 149.99));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Optional<Product> getProductById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    public Product createProduct(Product product) {
        product.setId(idCounter.incrementAndGet());
        products.add(product);
        return product;
    }

    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        Optional<Product> productOptional = getProductById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            return Optional.of(product);
        }

        return Optional.empty();
    }

    public boolean deleteProduct(Long id) {
        return products.removeIf(product -> product.getId().equals(id));
    }
}
