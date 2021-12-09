package com.cucumber.market.exception;

public class NotExistCommentException extends RuntimeException {
    public NotExistCommentException() {
        super();
    }

    public NotExistCommentException(String message) {
        super(message);
    }
}
