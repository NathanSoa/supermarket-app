package com.newgo.activity.supermarketapp.service;

import com.newgo.activity.supermarketapp.domain.ProductItem;
import com.newgo.activity.supermarketapp.domain.User;
import com.newgo.activity.supermarketapp.entities.dto.ProductItemDTO;
import com.newgo.activity.supermarketapp.repository.ProductItemRepository;
import com.newgo.activity.supermarketapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductItemService {

    private final ProductItemRepository productItemRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ProductItemService(ProductItemRepository productItemRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.productItemRepository = productItemRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProductItemDTO> findAll(String name) {
        Optional<User> optionalUser = userRepository.findByUsername(name);

        if(!optionalUser.isPresent())
            return null;

        List<ProductItem> productItems = productItemRepository.findAllByUser(optionalUser.get());

        return productItems
                .stream()
                .map(each -> modelMapper.map(each, ProductItemDTO.class))
                .collect(Collectors.toList());
    }
}
