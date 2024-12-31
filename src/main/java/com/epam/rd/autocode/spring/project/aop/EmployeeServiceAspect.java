package com.epam.rd.autocode.spring.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class EmployeeServiceAspect {

    @Around(
            value = "execution(public com.epam.rd.autocode.spring.project.dto.response.GetEmployeeDetailsResponse" +
                    " updateEmployeeByEmail(String, com.epam.rd.autocode.spring.project.dto.request.UpdateEmployeeRequest))")
    public Object updateEmployeeByEmailAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return CustomLogger.log(log, "UPDATE_EMPLOYEE_BY_EMAIL_REQUEST",
                "UPDATE_EMPLOYEE_BY_EMAIL_RESPONSE", joinPoint);
    }

    @Around(
            value = "execution(public com.epam.rd.autocode.spring.project.dto.response.GetEmployeeDetailsResponse " +
                    "getEmployeeByEmail(String))")
    public Object getEmployeeByEmailAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return CustomLogger.log(log, "GET_EMPLOYEE_BY_EMAIL_REQUEST",
                "GET_EMPLOYEE_BY_EMAIL_RESPONSE", joinPoint);
    }

    @Around(
            value = "execution(public void deleteEmployeeByEmail(String))")
    public void deleteEmployeeByEmailAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        CustomLogger.log(log, "DELETE_EMPLOYEE_BY_EMAIL_REQUEST",
                "DELETE_EMPLOYEE_BY_EMAIL_RESPONSE", joinPoint);
    }

    @Around(
            value = "execution(public com.epam.rd.autocode.spring.project.dto.response.GetEmployeeDetailsResponse " +
                    "addEmployee(com.epam.rd.autocode.spring.project.dto.request.CreateEmployeeRequest))")
    public Object addEmployeeAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return CustomLogger.log(log, "CREATE_EMPLOYEE_REQUEST", "CREATE_EMPLOYEE_RESPONSE",
                joinPoint);
    }

}
