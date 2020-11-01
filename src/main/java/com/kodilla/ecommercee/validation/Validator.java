package com.kodilla.ecommercee.validation;

import com.kodilla.ecommercee.exception.user.KeyException;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.service.UserDbService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class Validator {

    @Autowired
    private UserDbService userDbService;

    @Before("execution(* *(..)) && @annotation(com.kodilla.ecommercee.validation.AuthorizationRequired) && args(userId,..)")
    public void userIdValidation(Long userId) throws KeyException, UserNotFoundException {
        try {
            userDbService.checkValidityById(userId);
        } catch (KeyException s) {
            log.warn("Próba nieautoryzowanej operacji przez użytkownika o numerze id: " + userId);
            throw new KeyException(s.getMessage());
        }
    }
}
