package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.dto.CartDto;
import com.kodilla.ecommercee.dto.OrderDto;
import com.kodilla.ecommercee.exception.order.CartNotFoundException;
import com.kodilla.ecommercee.exception.product.ProductNotFoundException;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.service.CartDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class CartController {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartDbService cartDbService;

    @Autowired
    private OrderMapper orderMapper;


    @PostMapping("/createCart")
    public Cart createCart(@RequestBody CartDto cartDto) {
        return cartDbService.createCart(cartMapper.mapToCart(cartDto));
    }

    @GetMapping("/getCart/{cartId}")
    public CartDto getCart(@PathVariable Long cartId) throws CartNotFoundException {
        return cartMapper.mapToCartDto(cartDbService.getCartById(cartId));
    }

    @PostMapping("/addProduct/{cartId}/{productId}")
    public Cart addProduct(@PathVariable Long cartId, @PathVariable Long productId) throws CartNotFoundException, ProductNotFoundException {
        return cartDbService.addProductToCart(cartId, productId);
    }

    @DeleteMapping("/deleteProduct/{cartId}/{productId}")
    public Cart deleteProduct(@PathVariable Long cartId, @PathVariable Long productId) throws CartNotFoundException, ProductNotFoundException {
        return cartDbService.deleteProductFromCart(cartId, productId);
    }
  
    @PostMapping("/createOrder/{cartId}/{userId}")
    public OrderDto createOrder(@PathVariable Long cartId, @PathVariable Long userId) throws CartNotFoundException, UserNotFoundException {
        return orderMapper.mapToOrderDto(cartDbService.createOrder(cartId, userId));
    }
}
