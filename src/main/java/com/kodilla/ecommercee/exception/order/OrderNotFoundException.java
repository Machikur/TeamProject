package com.kodilla.ecommercee.exception.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends Exception {
    private static final String MESSAGE = "Order was not found.";

    public OrderNotFoundException() {
        super(MESSAGE);
    }
}
