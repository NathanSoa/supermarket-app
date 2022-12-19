package com.newgo.activity.supermarketapp.service;

import com.newgo.activity.supermarketapp.domain.Product;
import com.newgo.activity.supermarketapp.domain.ProductItem;
import com.newgo.activity.supermarketapp.domain.User;
import com.newgo.activity.supermarketapp.entities.ProductItemDTO;
import com.newgo.activity.supermarketapp.entities.ProductItemRequest;
import com.newgo.activity.supermarketapp.repository.ProductItemRepository;
import com.newgo.activity.supermarketapp.repository.ProductRepository;
import com.newgo.activity.supermarketapp.repository.UserRepository;

import com.newgo.activity.supermarketapp.utils.BeanCopyNonNullProperty;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductItemService {

    private final ProductItemRepository productItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductItemService(ProductItemRepository productItemRepository, UserRepository userRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.productItemRepository = productItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProductItemDTO> findAll(String name) {
        User user = userRepository.findByUsername(name).get();

        List<ProductItem> productItems = productItemRepository.findAllByUser(user);

        return productItems
                .stream()
                .map(each -> modelMapper.map(each, ProductItemDTO.class))
                .collect(Collectors.toList());
    }

    public ProductItem addProduct(String name, ProductItemRequest productItemRequest) {
        User user = userRepository.findByUsername(name).get();
        Optional<Product> optionalProduct = productRepository.findByName(productItemRequest.getName());

        if(!optionalProduct.isPresent())
            return null;

        if(itemAlreadyInList(user, optionalProduct.get())) {
            return null;
        }

        ProductItem productItem = new ProductItem();
        productItem.setProduct(optionalProduct.get());
        productItem.setUser(user);
        productItem.setQuantity(productItemRequest.getQuantity());

        return productItemRepository.save(productItem);
    }

    public ProductItem changeQuantity(String name, ProductItemRequest productItemRequest) {
        User user = userRepository.findByUsername(name).get();
        Optional<Product> optionalProduct = productRepository.findByName(productItemRequest.getName());

        if(!optionalProduct.isPresent())
            return null;

        Optional<ProductItem> optionalProductItem = productItemRepository.findByProductAndUser(optionalProduct.get(), user);

        if(!optionalProductItem.isPresent())
            return null;

        ProductItem databaseProductItem = optionalProductItem.get();

        ProductItem productItem = new ProductItem();
        productItem.setQuantity(productItemRequest.getQuantity());

        BeanCopyNonNullProperty.execute(productItem, databaseProductItem);

        return productItemRepository.save(databaseProductItem);
    }

    public void deleteList(String name) {
        User user = userRepository.findByUsername(name).get();

        productItemRepository.deleteAllByUser(user);
    }

    public void deleteProduct(String name, Long id) {
        User user = userRepository.findByUsername(name).get();
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(!optionalProduct.isPresent())
            throw new RuntimeException("Cannot found any product with id " + id);

        productItemRepository.deleteByProductAndUser(optionalProduct.get(), user);
    }

    public ProductItemDTO findByProductIdAndUser(String name, Long id) {
        User user = userRepository.findByUsername(name).get();
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(!optionalProduct.isPresent())
            return null;

        Optional<ProductItem> optionalProductItem = productItemRepository.findByProductAndUser(optionalProduct.get(), user);

        if(!optionalProductItem.isPresent())
            return null;

        ProductItem databaseProductItem = optionalProductItem.get();
        return modelMapper.map(databaseProductItem, ProductItemDTO.class);
    }

    private boolean itemAlreadyInList(User user, Product product) {
        return productItemRepository.findByProductAndUser(product, user).isPresent();
    }
}
