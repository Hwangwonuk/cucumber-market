package com.cucumber.market.exception;

public class AlreadyAdminException extends RuntimeException {
    public AlreadyAdminException() {
        super();
    }
    public AlreadyAdminException(String message) {
        super(message);
    }
}
