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
public class BookServiceAspect {

    @Around(
            value = "execution(public com.epam.rd.autocode.spring.project.dto.response.GetBookDetailsResponse updateBookById(Long, com.epam.rd.autocode.spring.project.dto.request.ModifyBookRequest))")
    public Object updateBookByIdAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return CustomLogger.log(log, "UPDATE_BOOK_BY_ID_REQUEST", "UPDATE_BOOK_BY_ID_RESPONSE", joinPoint);
    }

    @Around(
            value = "execution(public com.epam.rd.autocode.spring.project.dto.response.GetBookDetailsResponse updateBookByName(String, com.epam.rd.autocode.spring.project.dto.request.ModifyBookRequest))")
    public Object updateBookByNameAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return CustomLogger.log(log, "UPDATE_BOOK_BY_NAME_REQUEST", "UPDATE_BOOK_BY_NAME_RESPONSE", joinPoint);
    }

    @Around(
            value = "execution(public void deleteBookByName(String))")
    public void deleteBookByNameAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        CustomLogger.log(log, "DELETE_BOOK_BY_NAME_REQUEST", "DELETE_BOOK_BY_NAME_RESPONSE", joinPoint);
    }

    @Around(
            value = "execution(public void deleteBookById(Long))")
    public Object deleteBookByIdAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return CustomLogger.log(log, "DELETE_BOOK_BY_ID_REQUEST", "DELETE_BOOK_BY_ID_RESPONSE", joinPoint);
    }

    @Around(
            value = "execution(public com.epam.rd.autocode.spring.project.dto.response.GetBookDetailsResponse updateBookById(com.epam.rd.autocode.spring.project.dto.request.ModifyBookRequest))")
    public Object addBookAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return CustomLogger.log(log, "ADD_BOOK_BY_REQUEST", "ADD_BOOK_BY_RESPONSE", joinPoint);
    }
}
