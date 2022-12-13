package com.newgo.activity.supermarketapp.service;

import com.newgo.activity.supermarketapp.domain.Product;
import com.newgo.activity.supermarketapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.isPresent() ? productOptional.get() : null;
    }

    public Set<Product> findAll() {
        return new HashSet<>(productRepository.findAll());
    }

    public Product save(Product product) {
        if (product.getActive() == null) {
            product.setActive(false);
        } else {
            product.setActive(product.getActive());
        }
        return productRepository.save(product);
    }
}
