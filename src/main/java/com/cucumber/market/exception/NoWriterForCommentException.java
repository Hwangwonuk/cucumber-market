package com.cucumber.market.exception;

public class NoWriterForCommentException extends RuntimeException {
    public NoWriterForCommentException() {
        super();
    }

    public NoWriterForCommentException(String message) {
        super(message);
    }
}