package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.dto.CartDto;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.service.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserDbService userDbService;


    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(
                cart.getCartId(),
                productMapper.mapToProductDtoList(cart.getProducts()),
                cart.getUser().getUserId()
        );
    }

    public Cart mapToCart(final CartDto cartDto) throws UserNotFoundException {
        return new Cart(
                cartDto.getCartId(),
                productMapper.mapToProductList(cartDto.getProducts()),
                userDbService.findById(cartDto.getUserId()));

    }
}
