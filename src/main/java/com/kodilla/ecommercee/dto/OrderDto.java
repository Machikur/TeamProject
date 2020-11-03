package com.kodilla.ecommercee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderDto implements ShopComponent {
    private Long orderId;
    private Long userId;
    private List<ProductDto> products;

    @Override
    public Long getComponentId() {
        return orderId;
    }
}
