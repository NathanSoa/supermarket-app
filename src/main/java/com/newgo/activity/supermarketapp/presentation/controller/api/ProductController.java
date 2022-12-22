package com.newgo.activity.supermarketapp.presentation.controller.api;

import com.newgo.activity.supermarketapp.data.entities.Product;
import com.newgo.activity.supermarketapp.presentation.dtos.ProductDTO;
import com.newgo.activity.supermarketapp.data.repository.filter.ProductFilter;
import com.newgo.activity.supermarketapp.domain.service.ProductService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductDTO>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductDTO>> findAllFiltered(@RequestBody ProductFilter productFilter) {
        return ResponseEntity.ok(productService.findAllFiltered(productFilter));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> save(@Valid @RequestBody Product product){
        ProductDTO savedProduct = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> fullyUpdate(@Valid @RequestBody Product product, @PathVariable Long id) {
        ProductDTO updatedProduct = productService.fullyUpdate(product, id);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> partialUpdate(@RequestBody Product product, @PathVariable Long id) {
        ProductDTO updatedProduct = productService.partialUpdate(product, id);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok("Product with id " + id + " was deleted!");
    }
}
