package com.newgo.activity.supermarketapp.domain.service;

import com.newgo.activity.supermarketapp.data.entities.Product;
import com.newgo.activity.supermarketapp.data.entities.ProductItem;
import com.newgo.activity.supermarketapp.data.entities.User;
import com.newgo.activity.supermarketapp.presentation.dtos.ProductItemDTO;
import com.newgo.activity.supermarketapp.presentation.dtos.ProductItemRequest;
import com.newgo.activity.supermarketapp.data.repository.ProductItemRepository;
import com.newgo.activity.supermarketapp.data.repository.ProductRepository;
import com.newgo.activity.supermarketapp.data.repository.UserRepository;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductItemService {

    private final ProductItemRepository productItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductItemDTO> findAll(String name) {
        User user = userRepository.findByUsername(name).get();

        List<ProductItem> productItems = productItemRepository.findAllByUser(user);

        return productItems
                .stream()
                .map(each -> modelMapper.map(each, ProductItemDTO.class))
                .collect(Collectors.toList());
    }


    @Transactional
    public String addProduct(String name, ProductItemRequest productItemRequest) {
        User user = userRepository.findByUsername(name).get();
        Optional<Product> optionalProduct = productRepository.findByName(productItemRequest.getName());

        if(!optionalProduct.isPresent())
            throw new EmptyResultDataAccessException("There's no available product with name " + productItemRequest.getName(), 1);

        if(itemAlreadyInList(user, optionalProduct.get())) {
            ProductItem databaseProduct = productItemRepository.findByProductAndUser(optionalProduct.get(), user).get();
            updateQuantity(databaseProduct, databaseProduct.getQuantity() + 1);
            return "Added one more unit of " + databaseProduct.getProduct().getName() + " to your list";
        }

        ProductItem productItem = new ProductItem();
        productItem.setProduct(optionalProduct.get());
        productItem.setUser(user);
        productItem.setQuantity(productItemRequest.getQuantity());

        return "Product " + productItem.getProduct().getName() + " was added to your list with " + productItem.getQuantity() + " units";
    }

    @Transactional
    public ProductItemDTO changeQuantity(String name, ProductItemRequest productItemRequest) {
        User user = userRepository.findByUsername(name).get();
        Optional<Product> optionalProduct = productRepository.findByName(productItemRequest.getName());

        if(!optionalProduct.isPresent())
            throw new EmptyResultDataAccessException("There's no available product with name " + productItemRequest.getName(), 1);

        Optional<ProductItem> optionalProductItem = productItemRepository.findByProductAndUser(optionalProduct.get(), user);

        if(!optionalProductItem.isPresent())
            throw new EmptyResultDataAccessException("There's no product with name " + productItemRequest.getName() + " in your list, you should add it first.", 1);

        ProductItem databaseProductItem = optionalProductItem.get();
        updateQuantity(databaseProductItem, productItemRequest.getQuantity());
        return modelMapper.map(databaseProductItem, ProductItemDTO.class);
    }

    @Transactional
    public void deleteList(String name) {
        User user = userRepository.findByUsername(name).get();

        productItemRepository.deleteAllByUser(user);
    }

    @Transactional
    public String deleteProduct(String name, Long id) {
        User user = userRepository.findByUsername(name).get();
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(!optionalProduct.isPresent())
            throw new EmptyResultDataAccessException("There's no product with name " + name + " in your list to be deleted", 1);


        productItemRepository.deleteByProductAndUser(optionalProduct.get(), user);
        return "Product " + optionalProduct.get().getName() + " was deleted from " + name + "'s list";
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

    private void updateQuantity(ProductItem databaseProductItem, Integer newQuantity) {
        ProductItem updatedProductItem = modelMapper.map(databaseProductItem, ProductItem.class);
        updatedProductItem.setQuantity(newQuantity);
        productItemRepository.save(updatedProductItem);
    }
}
