package com.cucumber.market.exception;

public class AlreadySignInException extends RuntimeException {
    public AlreadySignInException() {
        super();
    }

    public AlreadySignInException(String message) {
        super(message);
    }
}

