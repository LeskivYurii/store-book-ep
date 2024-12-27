package com.epam.rd.autocode.spring.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class CustomLogger {

    private CustomLogger() {
    }

    public static Object log(Logger log, String actionRequest, String actionResponse, ProceedingJoinPoint joinPoint)
            throws Throwable {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Action: {} | Parameters: {} | User who commited action: {}", actionRequest, joinPoint.getArgs(),
                username);
        try {
            Object result = joinPoint.proceed();
            log.info("Action: {} | Parameters: {} | User who commited action: {}", actionResponse, result, username);
            return result;
        } catch (Exception e) {
            log.error("Exception was thrown in {} | User who commited action: {}", joinPoint.getSignature().getName(),
                    username);
            throw e;
        }

    }

}
