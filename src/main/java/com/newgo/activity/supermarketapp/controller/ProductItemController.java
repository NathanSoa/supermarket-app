package com.newgo.activity.supermarketapp.controller;

import com.newgo.activity.supermarketapp.entities.dto.ProductItemDTO;
import com.newgo.activity.supermarketapp.service.ProductItemService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/List")
public class ProductItemController {

    private final ProductItemService productItemService;

    public ProductItemController(ProductItemService productItemService) {
        this.productItemService = productItemService;
    }

    @GetMapping
    public ResponseEntity<List<ProductItemDTO>> findAll(Principal principal) {
        System.out.println(principal.getName());
        return ResponseEntity.ok(productItemService.findAll(principal.getName()));
    }
}
