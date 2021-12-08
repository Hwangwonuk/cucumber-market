package com.cucumber.market.exception;

public class AlreadySoldOutProductException extends RuntimeException {
    public AlreadySoldOutProductException() {
        super();
    }

    public AlreadySoldOutProductException(String message) {
        super(message);
    }
}
