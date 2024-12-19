package com.epam.rd.autocode.spring.project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ModelAndView handleAuthenticationException(AuthenticationException authenticationException) {
        log.error("", authenticationException);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/auth/login");
        return modelAndView;
    }

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleUnexpectedError(Throwable runtimeException) {
        log.error("", runtimeException);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/auth/login");
        return modelAndView;
    }
}
