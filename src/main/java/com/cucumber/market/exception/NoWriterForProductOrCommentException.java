package com.cucumber.market.exception;

public class NoWriterForProductOrCommentException extends RuntimeException {
    public NoWriterForProductOrCommentException() {
        super();
    }

    public NoWriterForProductOrCommentException(String message) {
        super(message);
    }
}