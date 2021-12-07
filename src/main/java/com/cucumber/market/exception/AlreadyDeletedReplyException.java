package com.cucumber.market.exception;

public class AlreadyDeletedReplyException extends RuntimeException {
    public AlreadyDeletedReplyException() {
        super();
    }

    public AlreadyDeletedReplyException(String message) {
        super(message);
    }
}