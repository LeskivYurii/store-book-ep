package com.epam.rd.autocode.spring.project.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final DateTimeFormatter DATETIMEFORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private static final String MESSAGE = "message";
    private final MessageSource messageSource;

    @ExceptionHandler(BadCredentialsException.class)
    public ModelAndView handleBadCredentialsException(AuthenticationException authenticationException) {
        log.error("", authenticationException);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/auth/login-page?error=true");
        return modelAndView;
    }

    @ExceptionHandler(DisabledException.class)
    public ModelAndView handleDisabledException(DisabledException e) {
        log.error("", e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setStatus(HttpStatus.LOCKED);
        modelAndView.setViewName("redirect:/auth/login-page?blocked=true");
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

    @ExceptionHandler(GenerateFileLinkException.class)
    public ModelAndView handleGenerateFileLinkException(GenerateFileLinkException e, HttpServletRequest request) {
        return setDetails(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughBookQuantityException.class)
    public ModelAndView handleNotEnoughBookQuantityException(NotEnoughBookQuantityException e, HttpServletRequest request) {
        return setDetails(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UploadImageException.class)
    public ModelAndView handleNotUploadImageException(UploadImageException e, HttpServletRequest request) {
        return setDetails(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleUnexpectedError(RuntimeException e, HttpServletRequest request) {
        ModelAndView mav = setDetails(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject(MESSAGE, messageSource.getMessage("error.unexpected", null,
                LocaleContextHolder.getLocale()));
        return mav;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        ModelAndView mav = setDetails(e, request, HttpStatus.FORBIDDEN);
        mav.addObject(MESSAGE, messageSource.getMessage("error.access.denied", null,
                LocaleContextHolder.getLocale()));
        return mav;
    }

    private ModelAndView setDetails(RuntimeException e, HttpServletRequest request, HttpStatus status) {
        log.error("", e);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/error/error-page");
        mav.setStatus(status);
        mav.addObject(MESSAGE, e.getMessage());
        mav.addObject("timestamp", DATETIMEFORMATTER.format(LocalDateTime.now()));
        mav.addObject("code",  status.value());
        mav.addObject("error",  status.getReasonPhrase());
        mav.addObject("path", request.getRequestURI());
        return mav;
    }

}
