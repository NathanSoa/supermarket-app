package com.newgo.activity.supermarketapp.controller.api;

import com.newgo.activity.supermarketapp.domain.Product;
import com.newgo.activity.supermarketapp.dtos.ProductDTO;
import com.newgo.activity.supermarketapp.repository.filter.ProductFilter;
import com.newgo.activity.supermarketapp.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO product = productService.findById(id);
        return Objects.isNull(product) ? ResponseEntity.notFound().build() : ResponseEntity.ok(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<ProductDTO> findAll(){
        return productService.findAll();
    }

    @GetMapping("/filter")
    public ResponseEntity<Set<ProductDTO>> findAllFiltered(@RequestBody ProductFilter productFilter) {
        List<ProductDTO> list = productService.findAllFiltered(productFilter);
        return list.size() < 1 ? ResponseEntity.notFound().build() : ResponseEntity.ok(new HashSet<>(list));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> save(@Valid @RequestBody Product product){
        ProductDTO savedProduct = productService.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@RequestBody Product product, @PathVariable Long id) {
        ProductDTO updatedProduct = productService.update(product, id);
        return Objects.isNull(updatedProduct) ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
