package com.kodilla.ecommercee.exception.order;

public class CartNotFoundException extends Exception {

    public CartNotFoundException(Long id) {
        super("Cart not found: "+id);
    }
}
