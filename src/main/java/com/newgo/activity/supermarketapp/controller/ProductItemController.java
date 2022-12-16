package com.newgo.activity.supermarketapp.controller;

import com.newgo.activity.supermarketapp.domain.ProductItem;
import com.newgo.activity.supermarketapp.entities.ProductItemDTO;
import com.newgo.activity.supermarketapp.entities.ProductItemRequest;
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
        return ResponseEntity.ok(productItemService.findAll(principal.getName()));
    }


    @Transactional
    @PostMapping
    public ResponseEntity<?> addProduct(Principal principal, @Valid @RequestBody ProductItemRequest productItemRequest) {
        ProductItem productItem = productItemService.addProduct(principal.getName(), productItemRequest);
        return Objects.isNull(productItem) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(productItem.getProduct().getName() + " was added to your list");
    }


    @Transactional
    @PutMapping("/product")
    public ResponseEntity<?> changeQuantity(Principal principal, @Valid @RequestBody ProductItemRequest productItemRequest) {
        ProductItem productItem = productItemService.changeQuantity(principal.getName(), productItemRequest);
        return Objects.isNull(productItem) ? ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }


    @Transactional
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteList(Principal principal) {
        productItemService.deleteList(principal.getName());
    }


    @Transactional
    @DeleteMapping("/{id}/product")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(Principal principal, @PathVariable Long id) {
        productItemService.deleteProduct(principal.getName(), id);
    }
}
