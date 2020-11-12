package com.kodilla.ecommercee.aop.validate;

import com.kodilla.ecommercee.service.UserDbService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserValidator {

    private final UserDbService userDbService;

    @Autowired
    public UserValidator(UserDbService userDbService) {
        this.userDbService = userDbService;
    }


    @Before("execution(* *(..)) && @annotation(com.kodilla.ecommercee.aop.validate.AuthorisationRequired) && args(userId,..)")
    public void operation(Long userId) throws Throwable {
        userDbService.checkValidityById(userId);
    }
}
