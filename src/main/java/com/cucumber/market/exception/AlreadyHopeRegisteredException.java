package com.cucumber.market.exception;

public class AlreadyHopeRegisteredException extends RuntimeException {
    public AlreadyHopeRegisteredException() {
        super();
    }

    public AlreadyHopeRegisteredException(String message) {
        super(message);
    }
}