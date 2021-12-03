package com.cucumber.market.exception;

public class AlreadyInActiveMemberException extends RuntimeException {
    public AlreadyInActiveMemberException() {
        super();
    }

    public AlreadyInActiveMemberException(String message) {
        super(message);
    }
}
