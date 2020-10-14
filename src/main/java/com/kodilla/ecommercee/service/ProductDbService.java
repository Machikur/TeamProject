package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.exception.product.ProductConflictException;
import com.kodilla.ecommercee.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDbService {

    @Autowired
    private ProductDao productDao;

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public Optional<Product> getProductById(final Long productId) {
        return productDao.findById(productId);
    }

    public Product saveProduct(final Product product) throws ProductConflictException {
        if (!productDao.existsByProductName(product.getProductName())) {
            return productDao.save(product);
        } else {
            throw new ProductConflictException();
        }
    }

    public void deleteProduct(final Long productId) {
        productDao.deleteById(productId);
    }
}
