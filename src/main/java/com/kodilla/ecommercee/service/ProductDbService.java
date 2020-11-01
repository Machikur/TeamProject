package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.dto.ProductDto;
import com.kodilla.ecommercee.exception.product.ProductConflictException;
import com.kodilla.ecommercee.exception.product.ProductNotFoundException;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.repository.ProductDao;
import com.kodilla.ecommercee.validation.AuthorizationRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductDbService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductMapper productMapper;

    public List<ProductDto> getAllProducts() {
        return productMapper.mapToProductDtoList(productDao.findAll());
    }

    public ProductDto getProductById(Long productId) throws ProductNotFoundException {
        return productMapper.mapToProductDto(productDao.findById(
                productId).orElseThrow(ProductNotFoundException::new));
    }

    @AuthorizationRequired
    public ProductDto saveProduct(Long userId, ProductDto product) throws ProductConflictException {
        if (!productDao.existsByProductName(product.getProductName())) {
            return productMapper.mapToProductDto(productDao.save(productMapper.mapToProduct(product)));
        } else {
            throw new ProductConflictException();
        }
    }

    @AuthorizationRequired
    public void deleteProduct(Long userId, Long productId) {
        productDao.deleteById(productId);
    }
}
