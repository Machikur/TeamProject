package com.kodilla.ecommercee.exception.order;

public class OrderNotFoundException extends Exception {
    private static final String MESSAGE = "Order was not found.";

    public OrderNotFoundException() {
        super(MESSAGE);
    }
}
