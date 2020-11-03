package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.aop.userwatcher.Delete;
import com.kodilla.ecommercee.aop.userwatcher.Save;
import com.kodilla.ecommercee.aop.userwatcher.Update;
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

    @Autowired
    private ProductDbService productDbService;

    @GetMapping(value = "products")
    public List<ProductDto> getAllProducts() {
        return productDbService.getAllProducts();
    }

    @GetMapping(value = "product")
    public ProductDto getProduct(@RequestParam Long productId) throws ProductNotFoundException {
        return productDbService.getProductById(productId);
    }

    @Save
    @PostMapping(value = "product")
    public ProductDto createProduct(@RequestParam Long userId, @RequestBody ProductDto productDto) throws ProductConflictException {
        return productDbService.saveProduct(productDto);
    }

    @Update
    @PutMapping(value = "product")
    public ProductDto updateProduct(@RequestParam Long userId, @RequestBody ProductDto productDto) throws ProductNotFoundException {
        return productDbService.updateProduct(productDto);
    }

    @Delete
    @DeleteMapping(value = "product")
    public void deleteProduct(@RequestParam Long userId, @RequestParam Long productId) {
        productDbService.deleteProduct(productId);
    }
}
