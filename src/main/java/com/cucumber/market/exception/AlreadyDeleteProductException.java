package com.cucumber.market.exception;

public class AlreadyDeleteProductException extends RuntimeException {
    public AlreadyDeleteProductException() {
        super();
    }

    public AlreadyDeleteProductException(String message) {
        super(message);
    }
}
