package com.cucumber.market.exception;

public class NotExistReplyException extends RuntimeException {
    public NotExistReplyException() {
        super();
    }

    public NotExistReplyException(String message) {
        super(message);
    }
}
