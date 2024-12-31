package com.epam.rd.autocode.spring.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ClientServiceAspect {

    @Around(
            value = "execution(public com.epam.rd.autocode.spring.project.dto.response.GetClientDetailsResponse " +
                    "updateClientByEmail(String, com.epam.rd.autocode.spring.project.dto.request.UpdateClientRequest))")
    public Object updateClientByEmailAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return CustomLogger.log(log, "UPDATE_CLIENT_BY_EMAIL_REQUEST",
                "UPDATE_CLIENT_BY_EMAIL_RESPONSE", joinPoint);
    }

    @Around(
            value = "execution(public com.epam.rd.autocode.spring.project.dto.response.GetClientDetailsResponse" +
                    " getClientByEmail(String))")
    public Object getClientByEmailAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return CustomLogger.log(log, "GET_CLIENT_BY_EMAIL_REQUEST",
                "GET_CLIENT_BY_EMAIL_RESPONSE", joinPoint);
    }

    @Around(
            value = "execution(public void deleteClientByEmail(String))")
    public void deleteClientByEmailAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        CustomLogger.log(log, "DELETE_CLIENT_BY_EMAIL_REQUEST",
                "DELETE_CLIENT_BY_EMAIL_RESPONSE", joinPoint);
    }


    @Around(
            value = "execution(public com.epam.rd.autocode.spring.project.dto.response.GetClientDetailsResponse " +
                    "addClient(com.epam.rd.autocode.spring.project.dto.request.CreateClientRequest))")
    public Object addClientAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return CustomLogger.log(log, "CREATE_CLIENT_REQUEST",
                "CREATE_CLIENT_RESPONSE", joinPoint);
    }

    @Around(
            value = "execution(public void blockUnblockClient(String))")
    public Object blockUnblockClientAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return CustomLogger.log(log, "BLOCK_CLIENT_BY_EMAIL_REQUEST",
                "BLOCK_CLIENT_BY_EMAIL_RESPONSE", joinPoint);
    }

}
