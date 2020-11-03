package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.aop.userwatcher.Delete;
import com.kodilla.ecommercee.aop.userwatcher.Save;
import com.kodilla.ecommercee.aop.userwatcher.Update;
import com.kodilla.ecommercee.dto.CartDto;
import com.kodilla.ecommercee.dto.OrderDto;
import com.kodilla.ecommercee.exception.order.CartNotFoundException;
import com.kodilla.ecommercee.exception.product.ProductNotFoundException;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.service.CartDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/")
public class CartController {

    @Autowired
    private CartDbService cartDbService;

    @Save
    @PostMapping("cart")
    public CartDto createCart(@RequestParam Long userId, @RequestBody CartDto cartDto) throws UserNotFoundException {
        return cartDbService.createCart(cartDto);
    }

    @GetMapping("cart")
    public CartDto getCart(@RequestParam Long cartId) throws CartNotFoundException {
        return cartDbService.getCartById(cartId);
    }

    @Update
    @PutMapping("addProductToCart")
    public CartDto addProducts(@RequestParam Long userId, @RequestParam Long cartId, @RequestParam Long productId)
            throws CartNotFoundException, ProductNotFoundException {

        return cartDbService.addProductToCart(cartId, productId);
    }

    @Delete
    @DeleteMapping("deleteProductFromCart")
    public CartDto deleteProduct(@RequestParam Long userId, @RequestParam Long cartId, @RequestParam Long productId)
            throws CartNotFoundException, ProductNotFoundException {

        return cartDbService.deleteProductFromCart(cartId, productId);
    }

    @Save
    @PostMapping("createOrderFromCart")
    public OrderDto createOrder(@RequestParam Long userId, @RequestParam Long cartId)
            throws CartNotFoundException, UserNotFoundException {

        return cartDbService.createOrder(cartId);
    }
}
