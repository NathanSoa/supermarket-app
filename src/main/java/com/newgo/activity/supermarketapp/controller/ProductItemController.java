package com.newgo.activity.supermarketapp.controller;

import com.newgo.activity.supermarketapp.domain.ProductItem;
import com.newgo.activity.supermarketapp.entities.ProductItemDTO;
import com.newgo.activity.supermarketapp.entities.ProductItemInputDTO;
import com.newgo.activity.supermarketapp.service.ProductItemService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/list")
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

    @PostMapping
    @Transactional
    public ResponseEntity<?> addProduct(Principal principal, @Valid @RequestBody ProductItemInputDTO productInputDTO) {
        ProductItem productItem = productItemService.addProduct(principal.getName(), productInputDTO);
        return Objects.isNull(productItem) ? ResponseEntity.notFound().build() : ResponseEntity.ok(productItem.getProduct().getName() + " was added to your list");
    }

    @DeleteMapping
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteList(Principal principal) {
        productItemService.deleteList(principal.getName());
    }
}
