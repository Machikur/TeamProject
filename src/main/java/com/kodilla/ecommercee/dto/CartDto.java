package com.kodilla.ecommercee.dto;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
public class CartDto {

    private long cartId;
    private UserDto userDto;
    private List<ProductDto> productDtoList;

    public CartDto(long cartId, UserDto userDto, List<ProductDto> productDtoList) {
        this.cartId = cartId;
        this.userDto = userDto;
        this.productDtoList = productDtoList;
    }
}
