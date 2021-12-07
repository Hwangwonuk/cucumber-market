package com.cucumber.market.exception;

public class AlreadyDeletedCommentException extends RuntimeException {
    public AlreadyDeletedCommentException() {
        super();
    }

    public AlreadyDeletedCommentException(String message) {
        super(message);
    }
}