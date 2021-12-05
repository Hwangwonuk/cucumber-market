package com.cucumber.market.exception;

public class NotYetSignInException extends RuntimeException {
    public NotYetSignInException() {
        super();
    }

    public NotYetSignInException(String message) {
        super(message);
    }
}