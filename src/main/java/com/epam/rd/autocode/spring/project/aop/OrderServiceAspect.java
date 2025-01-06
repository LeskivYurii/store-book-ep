package com.epam.rd.autocode.spring.project.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class OrderServiceAspect {

    @Around(
            value = "execution(public com.epam.rd.autocode.spring.project.dto.response.GetOrderDetailsResponse" +
                    " findOrderById(Long))")
    public Object findOrderByIdAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return CustomLogger.log(log, "FIND_ORDER_BY_ID_REQUEST", "FIND_ORDER_BY_ID_RESPONSE",
                joinPoint);
    }

    @Around(
            value = "execution(public com.epam.rd.autocode.spring.project.dto.response.GetOrderDetailsResponse createOrder())")
    public Object createOrderAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return CustomLogger.log(log, "CREATE_ORDER_REQUEST", "CREATE_ORDER_RESPONSE", joinPoint);
    }

    @Around(
            value = "execution(public com.epam.rd.autocode.spring.project.dto.response.GetOrderDetailsResponse " +
                    "updateStatus(Long, com.epam.rd.autocode.spring.project.model.enums.OrderStatus))")
    public Object updateStatusAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return CustomLogger.log(log, "UPDATE_ORDER_REQUEST_REQUEST", "UPDATE_ORDER_REQUEST_RESPONSE",
                joinPoint);
    }
    
}
