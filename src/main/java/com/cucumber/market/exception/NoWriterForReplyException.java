package com.cucumber.market.exception;

public class NoWriterForReplyException extends RuntimeException {
    public NoWriterForReplyException() {
        super();
    }

    public NoWriterForReplyException(String message) {
        super(message);
    }
}