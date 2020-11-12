package com.kodilla.ecommercee.aop.userwatcher;

import com.kodilla.ecommercee.dto.ShopComponent;
import com.kodilla.ecommercee.dto.UserOperationDto;
import com.kodilla.ecommercee.service.UserOperationService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class UserWatcher {


    private final UserOperationService userOperationService;

    @Autowired
    public UserWatcher(UserOperationService userOperationService) {
        this.userOperationService = userOperationService;
    }


    @Around("execution(* *(..)) && @annotation(com.kodilla.ecommercee.aop.userwatcher.UserOperation) && args(userId,..)")
    public ShopComponent operation(ProceedingJoinPoint proceedingJoinPoint, Long userId) throws Throwable {
        String action = getAnnotationValue(proceedingJoinPoint);
        ShopComponent result;
        result = (ShopComponent) proceedingJoinPoint.proceed();
        String className = result.getClass().getSimpleName().replace("Dto", "");
        UserOperationDto userOperationDto = new UserOperationDto(userId, action + ": " +
                className + " with id: " + result.getComponentId());
        userOperationService.save(userOperationDto);
        log.info("User by id: {} done action: {}, in class: {} id: {}", userId, action, className, result.getComponentId());
        return result;
    }

    private String getAnnotationValue(ProceedingJoinPoint proceedingJoinPoint) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = proceedingJoinPoint.getTarget().getClass()
                .getMethod(signature.getName(), signature.getMethod().getParameterTypes());
        return method.getAnnotation(UserOperation.class).operationtype().getDesc();
    }

}
