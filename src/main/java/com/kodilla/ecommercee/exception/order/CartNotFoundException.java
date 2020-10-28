package com.kodilla.ecommercee.exception.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartNotFoundException extends Exception {

    public CartNotFoundException(Long id) {
        super("Cart not found: " + id);
    }
}
