package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.aop.userwatcher.OperationType;
import com.kodilla.ecommercee.aop.userwatcher.UserOperation;
import com.kodilla.ecommercee.dto.ProductDto;
import com.kodilla.ecommercee.exception.product.ProductConflictException;
import com.kodilla.ecommercee.exception.product.ProductNotFoundException;
import com.kodilla.ecommercee.service.ProductDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class ProductController {

    private final ProductDbService productDbService;

    @Autowired
    public ProductController(ProductDbService productDbService) {
        this.productDbService = productDbService;
    }

    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        return productDbService.getAllProducts();
    }

    @GetMapping("/product")
    public ProductDto getProduct(@RequestParam Long productId) throws ProductNotFoundException {
        return productDbService.getProductById(productId);
    }

    @UserOperation(operationtype = OperationType.CREATE)
    @PostMapping("/product")
    public ProductDto createProduct(@RequestParam Long userId, @RequestBody ProductDto productDto) throws ProductConflictException {
        return productDbService.saveProduct(productDto);
    }

    @UserOperation(operationtype = OperationType.UPDATE)
    @PutMapping("/product")
    public ProductDto updateProduct(@RequestParam Long userId, @RequestBody ProductDto productDto) throws ProductNotFoundException {
        return productDbService.updateProduct(productDto);
    }

    @UserOperation(operationtype = OperationType.DELETE)
    @DeleteMapping("/product")
    public void deleteProduct(@RequestParam Long userId, @RequestParam Long productId) {
        productDbService.deleteProduct(productId);
    }
}
