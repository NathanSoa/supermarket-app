package com.newgo.activity.supermarketapp.presentation.controller.api;

import com.newgo.activity.supermarketapp.presentation.dtos.ProductItemDTO;
import com.newgo.activity.supermarketapp.presentation.dtos.ProductItemRequest;
import com.newgo.activity.supermarketapp.domain.service.ProductItemService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;

import java.util.List;

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

    @PatchMapping("/product")
    public ResponseEntity<ProductItemDTO> changeQuantity(Principal principal, @Valid @RequestBody ProductItemRequest productItemRequest) {
        ProductItemDTO productItemDTO = productItemService.changeQuantity(principal.getName(), productItemRequest);
        return ResponseEntity.ok(productItemDTO);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteList(Principal principal) {
        productItemService.deleteList(principal.getName());
        return ResponseEntity.ok(principal.getName() + ", your list was deleted!");
    }

    @DeleteMapping("/{id}/product")
    public ResponseEntity<String> deleteProduct(Principal principal, @PathVariable Long id) {
        String message = productItemService.deleteProduct(principal.getName(), id);
        return ResponseEntity.ok(message);
    }
}
