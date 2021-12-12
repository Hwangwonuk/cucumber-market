package com.cucumber.market.exception;

public class NotExistMemberException extends RuntimeException {
    public NotExistMemberException() {
        super();
    }

    public NotExistMemberException(String message) {
        super(message);
    }
}
