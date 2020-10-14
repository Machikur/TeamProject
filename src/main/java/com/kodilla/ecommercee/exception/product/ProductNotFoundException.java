package com.kodilla.ecommercee.exception.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends Exception {

    private static final String MESSAGE = "Produkt nie istnieje";

    public ProductNotFoundException() {
        super(MESSAGE);
    }
}
