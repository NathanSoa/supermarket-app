package com.newgo.activity.supermarketapp.service;

import com.newgo.activity.supermarketapp.utils.BeanCopyNonNullProperty;
import com.newgo.activity.supermarketapp.domain.Product;
import com.newgo.activity.supermarketapp.domain.dto.ProductDTO;
import com.newgo.activity.supermarketapp.repository.filter.ProductFilter;
import com.newgo.activity.supermarketapp.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ProductDTO findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.isPresent() ? modelMapper.map(productOptional.get(), ProductDTO.class) : null;
    }

    public Set<ProductDTO> findAll() {
        return productRepository.findAll()
                    .stream()
                    .map(each -> modelMapper.map(each, ProductDTO.class))
                    .collect(Collectors.toSet());
    }

    public List<ProductDTO> findAllFiltered(ProductFilter productFilter) {
        return productRepository.findAllFiltered(productFilter)
                .stream()
                .map(each -> modelMapper.map(each, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO save(Product product) {
        try {
            boolean value = product.getActive();
        } catch (NullPointerException e) {
            product.setActive(false);
        }
        return modelMapper.map(productRepository.save(product), ProductDTO.class);
    }

    @Transactional
    public ProductDTO update(Product product, Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(!optionalProduct.isPresent())
            return null;

        Product databaseProduct = optionalProduct.get();
        BeanCopyNonNullProperty.execute(product, databaseProduct);
        return modelMapper.map(productRepository.save(databaseProduct), ProductDTO.class);
    }
}
