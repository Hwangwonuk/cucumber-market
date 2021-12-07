package com.cucumber.market.exception;

public class NotYetHopeRegisteredException extends RuntimeException {
    public NotYetHopeRegisteredException() {
        super();
    }

    public NotYetHopeRegisteredException(String message) {
        super(message);
    }
}