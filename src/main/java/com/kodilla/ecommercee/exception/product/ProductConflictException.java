package com.kodilla.ecommercee.exception.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductConflictException extends Exception {

    private static final String MESSAGE = "Produkt o podanej nazwie już istnieje";

    public ProductConflictException() {
        super(MESSAGE);
    }
}
