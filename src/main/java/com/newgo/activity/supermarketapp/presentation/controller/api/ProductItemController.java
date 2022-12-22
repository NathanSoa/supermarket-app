package com.newgo.activity.supermarketapp.presentation.controller.api;

import com.newgo.activity.supermarketapp.data.entities.ProductItem;
import com.newgo.activity.supermarketapp.presentation.dtos.ProductItemDTO;
import com.newgo.activity.supermarketapp.presentation.dtos.ProductItemRequest;
import com.newgo.activity.supermarketapp.domain.service.ProductItemService;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ProductItemController {

    private final ProductItemService productItemService;

    @GetMapping
    public ResponseEntity<List<ProductItemDTO>> findAll(Principal principal) {
        return ResponseEntity.ok(productItemService.findAll(principal.getName()));
    }


    @PostMapping
    public ResponseEntity<String> addProduct(Principal principal, @Valid @RequestBody ProductItemRequest productItemRequest) {
        String message = productItemService.addProduct(principal.getName(), productItemRequest);
        return ResponseEntity.ok(message);
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
