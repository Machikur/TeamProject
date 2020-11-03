package com.kodilla.ecommercee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CartDto implements ShopComponent {

    private Long cartId;
    private List<ProductDto> products;
    private Long userId;

    @Override
    public Long getComponentId() {
        return cartId;
    }
}
