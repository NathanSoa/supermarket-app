package com.newgo.activity.supermarketapp.service;

import com.newgo.activity.supermarketapp.domain.Product;
import com.newgo.activity.supermarketapp.repository.filter.ProductFilter;
import com.newgo.activity.supermarketapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

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
        try {
            boolean value = product.getActive();
        } catch (NullPointerException e) {
            product.setActive(false);
        }
        return productRepository.save(product);
    }

    public List<Product> findAllFiltered(ProductFilter productFilter) {
        return new ArrayList<>(productRepository.findAllFiltered(productFilter));
    }

}
