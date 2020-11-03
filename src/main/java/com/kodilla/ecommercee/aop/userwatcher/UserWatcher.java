package com.kodilla.ecommercee.aop.userwatcher;

import com.kodilla.ecommercee.dto.ShopComponent;
import com.kodilla.ecommercee.dto.UserOperationDto;
import com.kodilla.ecommercee.exception.user.KeyException;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.service.UserDbService;
import com.kodilla.ecommercee.service.UserOperationService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class UserWatcher {


    @Autowired
    private UserOperationService userOperationService;

    @Autowired
    private UserDbService userDbService;

    @Around("execution(* *(..)) && @annotation(com.kodilla.ecommercee.aop.userwatcher.Save) && args(userId,..)")
    public ShopComponent saveOperation(ProceedingJoinPoint proceedingJoinPoint, Long userId) throws Throwable {
        return operation(proceedingJoinPoint, userId, "save");
    }

    @Around("execution(* *(..)) && @annotation(com.kodilla.ecommercee.aop.userwatcher.Update) && args(userId,..)")
    public ShopComponent updateOperation(ProceedingJoinPoint proceedingJoinPoint, Long userId) throws Throwable {
        return operation(proceedingJoinPoint, userId, "update");
    }

    @Around("execution(* *(..)) && @annotation(com.kodilla.ecommercee.aop.userwatcher.Delete) && args(userId,..)")
    public ShopComponent deleteOperation(ProceedingJoinPoint proceedingJoinPoint, Long userId) throws Throwable {
        return operation(proceedingJoinPoint, userId, "Delete");
    }

    public ShopComponent operation(ProceedingJoinPoint proceedingJoinPoint, Long userId, String action) throws Throwable {
        checkValidityById(userId);
        ShopComponent result;
        result = (ShopComponent) proceedingJoinPoint.proceed();
        String className = result.getClass().getSimpleName().replace("Dto", "");
        UserOperationDto userOperationDto = new UserOperationDto(userId, action + ": " +
                className + " with id: " + result.getComponentId());
        userOperationService.save(userOperationDto);
        log.info("user " + userId + " done action: " + action + " in " + className + " by id:" + result.getComponentId());
        return result;
    }

    private void checkValidityById(Long userId) throws KeyException {
        try {
            userDbService.checkValidityById(userId);
        } catch (
                KeyException | UserNotFoundException s) {
            log.warn("Próba nieautoryzowanej operacji przez użytkownika o numerze id: " + userId);
            throw new KeyException(s.getMessage());
        }
    }
}
