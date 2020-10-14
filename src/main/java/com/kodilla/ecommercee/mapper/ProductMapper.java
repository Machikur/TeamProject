package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public Product mapToProduct(final ProductDto productDto) {
        return new Product(
                productDto.getProductId(),
                productDto.getProductName(),
                productDto.getProductPrice(),
                productDto.getQuantity()
        );
    }

    public ProductDto mapToProductDto(final Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getProductName(),
                product.getProductPrice(),
                product.getQuantity()
        );
    }

    public List<ProductDto> mapToTaskDtoList(final List<Product> productList) {
        return productList.stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
    }
    public List<Product> mapToTaskListDto(final List<ProductDto> productList) {
        return productList.stream()
                .map(this::mapToProduct)
                .collect(Collectors.toList());
    }
}
