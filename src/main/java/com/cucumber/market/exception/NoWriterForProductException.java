package com.cucumber.market.exception;

public class NoWriterForProductException extends RuntimeException {
    public NoWriterForProductException() {
        super();
    }

    public NoWriterForProductException(String message) {
        super(message);
    }
}
