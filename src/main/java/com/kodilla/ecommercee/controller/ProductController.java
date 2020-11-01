package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.dto.ProductDto;
import com.kodilla.ecommercee.exception.product.ProductConflictException;
import com.kodilla.ecommercee.exception.product.ProductNotFoundException;
import com.kodilla.ecommercee.service.ProductDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class ProductController {

    @Autowired
    private ProductDbService productDbService;

    @GetMapping(value = "products")
    public List<ProductDto> getAllProducts() {
        return productDbService.getAllProducts();
    }

    @GetMapping(value = "product")
    public ProductDto getProduct(@RequestParam(value = "productId") Long productId) throws ProductNotFoundException {
        return productDbService.getProductById(productId);
    }

    @PostMapping(value = "product", consumes = APPLICATION_JSON_VALUE)
    public void createProduct(@RequestBody ProductDto productDto, @RequestParam Long userId) throws ProductConflictException {
        productDbService.saveProduct(userId, productDto);
    }

    @PutMapping(value = "product")
    public ProductDto updateProduct(@RequestBody ProductDto productDto, @RequestParam Long userId) throws ProductConflictException {
        return productDbService.saveProduct(userId, productDto);
    }

    @DeleteMapping(value = "product")
    public void deleteProduct(@RequestParam Long productId, @RequestParam Long userId) {
        productDbService.deleteProduct(userId, productId);
    }
}
