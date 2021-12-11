package com.cucumber.market.exception;

public class AlreadyDeletedProductException extends RuntimeException {
    public AlreadyDeletedProductException() {
        super();
    }

    public AlreadyDeletedProductException(String message) {
        super(message);
    }
}
