package com.nisum.ProductService.service;

import com.nisum.ProductService.model.Product;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public List<Product> getAllProducts() {
        return Arrays.asList(
            new Product(1L, "Laptop", 1200.0),
            new Product(2L, "Phone", 800.0)
        );
    }
}
