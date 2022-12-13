package com.newgo.activity.supermarketapp.controller;

import com.newgo.activity.supermarketapp.domain.Product;
import com.newgo.activity.supermarketapp.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return product == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<Product> findAll(){
        return productService.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestParam("photo") @Valid Product product){
        Product savedProduct = productService.save(product);
        return ResponseEntity.ok(savedProduct);
    }
}
