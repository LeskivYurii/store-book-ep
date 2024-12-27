package com.epam.rd.autocode.spring.project.exception;

public class CreateOrderException extends RuntimeException{

    public CreateOrderException() {
        super();
    }

    public CreateOrderException(String message) {
        super(message);
    }
}
