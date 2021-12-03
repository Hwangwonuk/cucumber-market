package com.cucumber.market.exception;

public class AlreadySignOutException extends RuntimeException {
    public AlreadySignOutException() {
        super();
    }

    public AlreadySignOutException(String message) {
        super(message);
    }
}