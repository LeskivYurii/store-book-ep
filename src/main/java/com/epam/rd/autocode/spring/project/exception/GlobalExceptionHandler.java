package com.epam.rd.autocode.spring.project.exception;

import com.epam.rd.autocode.spring.project.dto.request.LoginRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final DateTimeFormatter DATETIMEFORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    @ExceptionHandler(AuthenticationException.class)
    public ModelAndView handleAuthenticationException(AuthenticationException authenticationException) {
        log.error("", authenticationException);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/auth/login-page?error=true");
        return modelAndView;
    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        return setDetails(e, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ModelAndView handleAlreadyExistException(AlreadyExistException e, HttpServletRequest request) {
        return setDetails(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreateOrderException.class)
    public ModelAndView handleCreateOrderException(CreateOrderException e, HttpServletRequest request) {
        return setDetails(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleUnexpectedError(RuntimeException e, HttpServletRequest request) {
        ModelAndView mav = setDetails(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject("message", "Unexpected exception happened we're working on it to fix it!");
        return mav;
    }

    private ModelAndView setDetails(RuntimeException e, HttpServletRequest request, HttpStatus status) {
        log.error("", e);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/error/error-page");
        mav.setStatus(status);
        mav.addObject("message", e.getMessage());
        mav.addObject("timestamp", "Date: " + DATETIMEFORMATTER.format(LocalDateTime.now()));
        mav.addObject("code",  status.value());
        mav.addObject("error",  status.getReasonPhrase());
        mav.addObject("path", "Path: " + request.getRequestURI());
        return mav;
    }
}
