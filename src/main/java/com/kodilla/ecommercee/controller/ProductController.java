package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.dto.ProductDto;
import com.kodilla.ecommercee.exception.product.ProductConflictException;
import com.kodilla.ecommercee.exception.product.ProductNotFoundException;
import com.kodilla.ecommercee.mapper.ProductMapper;
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

    @Autowired
    private ProductMapper productMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getAllProducts")
    public List<ProductDto> getAllProducts() {
        return productMapper.mapToTaskDtoList(productDbService.getAllProducts());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getProduct")
    public ProductDto getProduct(@RequestParam(value = "productId") Long productId) throws ProductNotFoundException {
        return productMapper.mapToProductDto(productDbService.getProductById(productId).orElseThrow(ProductNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createProduct", consumes = APPLICATION_JSON_VALUE)
    public void createProduct(@RequestBody ProductDto productDto) throws ProductConflictException {
        productDbService.saveProduct(productMapper.mapToProduct(productDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateProduct")
    public ProductDto updateProduct(@RequestBody ProductDto productDto) throws ProductConflictException {
        return productMapper.mapToProductDto(productDbService.saveProduct(productMapper.mapToProduct(productDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteProduct")
    public void deleteProduct(@RequestParam(value = "productId") Long productId) {
        productDbService.deleteProduct(productId);
    }
}
