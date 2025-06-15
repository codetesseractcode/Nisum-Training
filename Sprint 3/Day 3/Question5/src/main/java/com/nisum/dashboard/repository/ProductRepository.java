package com.nisum.dashboard.repository;

import com.nisum.dashboard.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    public Product findById(Long id) {
        return productMap.get(id);
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(idGenerator.getAndIncrement());
        }
        productMap.put(product.getId(), product);
        return product;
    }

    public void deleteById(Long id) {
        productMap.remove(id);
    }

    public boolean existsById(Long id) {
        return productMap.containsKey(id);
    }

    public List<Product> findByCategory(String category) {
        return productMap.values().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Product> findByPriceRange(Double minPrice, Double maxPrice) {
        return productMap.values().stream()
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
}
