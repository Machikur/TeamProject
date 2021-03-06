package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.dto.CartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    private final ProductMapper productMapper;

    @Autowired
    public CartMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(
                cart.getCartId(),
                productMapper.mapToProductDtoList(cart.getProducts()),
                cart.getUser().getUserId()
        );
    }

    public Cart mapToCart(final CartDto cartDto) {
        return new Cart(
                productMapper.mapToProductList(cartDto.getProducts()));
    }
}
