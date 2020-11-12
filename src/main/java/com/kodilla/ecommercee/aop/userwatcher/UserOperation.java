package com.kodilla.ecommercee.aop.userwatcher;

import com.kodilla.ecommercee.aop.validate.AuthorisationRequired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@AuthorisationRequired
public @interface UserOperation {
    OperationType operationtype();
}
