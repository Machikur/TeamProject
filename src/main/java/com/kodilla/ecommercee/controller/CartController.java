package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.aop.userwatcher.OperationType;
import com.kodilla.ecommercee.aop.userwatcher.UserOperation;
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

    private final CartDbService cartDbService;

    @Autowired
    public CartController(CartDbService cartDbService) {
        this.cartDbService = cartDbService;
    }

    @UserOperation(operationtype = OperationType.CREATE)
    @PostMapping("/cart")
    public CartDto createCart(@RequestParam Long userId, @RequestBody CartDto cartDto) throws UserNotFoundException {
        return cartDbService.createCart(cartDto);
    }

    @GetMapping("/cart")
    public CartDto getCart(@RequestParam Long cartId) throws CartNotFoundException {
        return cartDbService.getCartById(cartId);
    }

    @UserOperation(operationtype = OperationType.UPDATE)
    @PutMapping("/productToCart")
    public CartDto addProducts(@RequestParam Long userId, @RequestParam Long cartId, @RequestParam Long productId)
            throws CartNotFoundException, ProductNotFoundException {

        return cartDbService.addProductToCart(cartId, productId);
    }

    @UserOperation(operationtype = OperationType.DELETE)
    @DeleteMapping("/productFromCart")
    public CartDto deleteProduct(@RequestParam Long userId, @RequestParam Long cartId, @RequestParam Long productId)
            throws CartNotFoundException, ProductNotFoundException {

        return cartDbService.deleteProductFromCart(cartId, productId);
    }

    @UserOperation(operationtype = OperationType.CREATE)
    @PostMapping("/orderFromCart")
    public OrderDto createOrder(@RequestParam Long userId, @RequestParam Long cartId)
            throws CartNotFoundException, UserNotFoundException {

        return cartDbService.createOrder(cartId);
    }
}
