package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.ProductDto;
import com.kodilla.ecommercee.exception.product.ProductConflictException;
import com.kodilla.ecommercee.exception.product.ProductNotFoundException;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductDbService {

    private ProductDao productDao;
    private ProductMapper productMapper;

    @Autowired
    public ProductDbService(ProductDao productDao, ProductMapper productMapper) {
        this.productDao = productDao;
        this.productMapper = productMapper;
    }

    public List<ProductDto> getAllProducts() {
        return productMapper.mapToProductDtoList(productDao.findAll());
    }

    public ProductDto getProductById(Long productId) throws ProductNotFoundException {
        return productMapper.mapToProductDto(productDao.findById(
                productId).orElseThrow(ProductNotFoundException::new));
    }

    public ProductDto saveProduct(ProductDto product) throws ProductConflictException {
        if (!productDao.existsByProductName(product.getProductName())) {
            return productMapper.mapToProductDto(productDao.save(productMapper.mapToProduct(product)));
        } else {
            throw new ProductConflictException();
        }
    }

    public ProductDto updateProduct(ProductDto productDto) throws ProductNotFoundException {
        Product product = productDao.findById(productDto.getProductId()).orElseThrow(ProductNotFoundException::new);
        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());
        product.setQuantity(productDto.getQuantity());
        return productMapper.mapToProductDto(productDao.save(product));
    }


    public void deleteProduct(Long productId) {
        productDao.deleteById(productId);
    }
}
