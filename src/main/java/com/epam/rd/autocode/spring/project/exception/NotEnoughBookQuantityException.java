package com.epam.rd.autocode.spring.project.exception;

public class NotEnoughBookQuantityException  extends RuntimeException{

    public NotEnoughBookQuantityException() {
        super();
    }

    public NotEnoughBookQuantityException(String message) {
        super(message);
    }
}
