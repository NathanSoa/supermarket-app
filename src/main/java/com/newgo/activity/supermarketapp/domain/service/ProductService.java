package com.newgo.activity.supermarketapp.domain.service;

import com.newgo.activity.supermarketapp.utils.BeanCopyNonNullProperty;
import com.newgo.activity.supermarketapp.data.entities.Product;
import com.newgo.activity.supermarketapp.presentation.dtos.ProductDTO;
import com.newgo.activity.supermarketapp.data.repository.filter.ProductFilter;
import com.newgo.activity.supermarketapp.data.repository.ProductRepository;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                    .stream()
                    .map(each -> modelMapper.map(each, ProductDTO.class))
                    .collect(Collectors.toList());
    }

    public List<ProductDTO> findAllFiltered(ProductFilter productFilter) {
        return productRepository.findAllFiltered(productFilter)
                .stream()
                .map(each -> modelMapper.map(each, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        Product databaseProduct = getProductOrThrowException(id);
        return modelMapper.map(databaseProduct, ProductDTO.class);
    }

    @Transactional
    public ProductDTO save(Product entity) {
        if(Objects.isNull(entity.getActive()))
            entity.setActive(false);

        entity.setId(null);
        return modelMapper.map(productRepository.save(entity), ProductDTO.class);
    }

    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void delete(Product entity) {
        productRepository.delete(entity);
    }

    @Transactional
    public ProductDTO fullyUpdate(Product entity, Long id) {
        Product databaseProduct = getProductOrThrowException(id);
        entity.setId(id);
        BeanUtils.copyProperties(entity, databaseProduct);
        return modelMapper.map(productRepository.save(databaseProduct), ProductDTO.class);
    }

    @Transactional
    public ProductDTO partialUpdate(Product entity, Long id) {
        Product databaseProduct = getProductOrThrowException(id);
        BeanCopyNonNullProperty.execute(entity, databaseProduct);
        return modelMapper.map(productRepository.save(databaseProduct), ProductDTO.class);
    }

    private Product getProductOrThrowException(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElseThrow(() ->  new EmptyResultDataAccessException("Cannot find any product with id "  + id, 1));
    }
}
