package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.CartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(
                cart.getCartId(),
                userMapper.mapUserToUserDto(cart.getUser()),
                productMapper.mapToTaskDtoList(cart.getProducts())
        );
    }

    public Cart mapToCart(final CartDto cartDto) {
        return new Cart(
                cartDto.getCartId(),
                productMapper.mapToTaskListDto(cartDto.getProductDtoList()),
                userMapper.mapUserDtoToUser(cartDto.getUserDto())
        );

    }
}
