package com.nisum.service;

import com.nisum.exception.ProductNotFoundException;
import com.nisum.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private final Map<Long, Product> productMap = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Product> getAllProducts() {
        return new ArrayList<>(productMap.values());
    }

    public Product getProductById(Long id) {
        Product product = productMap.get(id);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        return product;
    }

    public Product createProduct(Product product) {
        Long id = idGenerator.getAndIncrement();
        product.setId(id);
        productMap.put(id, product);
        return product;
    }

    public Product updateProduct(Long id, Product product) {
        if (!productMap.containsKey(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        product.setId(id);
        productMap.put(id, product);
        return product;
    }

    public void deleteProduct(Long id) {
        if (!productMap.containsKey(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productMap.remove(id);
    }
}
