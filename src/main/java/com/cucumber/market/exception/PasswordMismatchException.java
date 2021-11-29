package com.cucumber.market.exception;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super();
    }

    public PasswordMismatchException(String message) {
        super(message);
    }
}