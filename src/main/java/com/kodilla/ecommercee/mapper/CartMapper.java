package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.CartDto;
import com.kodilla.ecommercee.service.ProductDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDbService productDbService;

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(
                cart.getCartId(),
                productMapper.mapToProductDtoList(
                        productDbService.findAllProductsByIdList(
                                productMapper.getListOfProductsId(cart.getProducts()))));
    }

    public Cart mapToCart(final CartDto cartDto) {
        return new Cart(
                cartDto.getCartId(),
                productMapper.mapToProductList(cartDto.getProducts()));

    }
}
