package com.cucumber.market.exception;

public class NotExistProductException extends RuntimeException {
    public NotExistProductException() {
        super();
    }

    public NotExistProductException(String message) {
        super(message);
    }
}
