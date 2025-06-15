package com.nisum.dashboard.service;

import com.nisum.dashboard.exception.ProductNotFoundException;
import com.nisum.dashboard.model.Product;
import com.nisum.dashboard.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(Integer page, Integer size, String sortBy, String sortDir,
                                        String category, Double minPrice, Double maxPrice) {
        List<Product> products = productRepository.findAll();

        // Apply category filter if provided
        if (category != null && !category.isEmpty()) {
            products = products.stream()
                    .filter(p -> p.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }

        // Apply price range filter if provided
        if (minPrice != null && maxPrice != null) {
            products = products.stream()
                    .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }

        // Apply sorting if requested
        if (sortBy != null && !sortBy.isEmpty()) {
            Comparator<Product> comparator = getComparatorForField(sortBy);
            if ("desc".equalsIgnoreCase(sortDir)) {
                comparator = comparator.reversed();
            }
            products = products.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());
        }

        // Apply pagination if requested
        if (page != null && size != null) {
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, products.size());

            if (startIndex < products.size()) {
                products = products.subList(startIndex, endIndex);
            } else {
                products = List.of();
            }
        }

        return products;
    }

    private Comparator<Product> getComparatorForField(String field) {
        Comparator<Product> comparator;
        switch (field.toLowerCase()) {
            case "name":
                comparator = Comparator.comparing(Product::getName);
                break;
            case "price":
                comparator = Comparator.comparing(Product::getPrice);
                break;
            case "category":
                comparator = Comparator.comparing(Product::getCategory);
                break;
            case "stockquantity":
                comparator = Comparator.comparing(Product::getStockQuantity);
                break;
            default:
                comparator = Comparator.comparing(Product::getId);
                break;
        }
        return comparator;
    }

    public Product getProductById(Long id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        return product;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productDetails.setId(id);
        return productRepository.save(productDetails);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
