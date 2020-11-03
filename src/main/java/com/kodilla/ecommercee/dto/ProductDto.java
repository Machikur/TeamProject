package com.kodilla.ecommercee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductDto implements ShopComponent {

    private Long productId;
    private String productName;
    private double productPrice;
    private int quantity;

    @Override
    public Long getComponentId() {
        return productId;
    }
}
