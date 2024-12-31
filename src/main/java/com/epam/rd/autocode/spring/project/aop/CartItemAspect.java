package com.epam.rd.autocode.spring.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class CartItemAspect {

    @Around(
            value = "execution(public void addItemToCart(com.epam.rd.autocode.spring.project.dto.request." +
                    "AddCartItemRequest))")
    public void addItemToCartAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        CustomLogger.log(log, "ADD_ITEM_TO_CART_REQUEST", "ADD_ITEM_TO_CART_RESPONSE",
                joinPoint);
    }

    @Around(
            value = "execution(public void deleteItemFromCart(Long))")
    public void deleteItemFromCartAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        CustomLogger.log(log, "DELETE_ITEM_FROM_CART_REQUEST", "DELETE_ITEM_FROM_CART_RESPONSE",
                joinPoint);
    }

    @Around(
            value = "execution(public void deleteCartByClientEmail(String))")
    public void deleteCartByClientEmailAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        CustomLogger.log(log, "CLEAN_CART_REQUEST", "CLEAN_CART_RESPONSE", joinPoint);
    }

}
