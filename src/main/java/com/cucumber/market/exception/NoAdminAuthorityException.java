package com.cucumber.market.exception;

public class NoAdminAuthorityException extends RuntimeException {
    public NoAdminAuthorityException() {
        super();
    }

    public NoAdminAuthorityException(String message) {
        super(message);
    }
}