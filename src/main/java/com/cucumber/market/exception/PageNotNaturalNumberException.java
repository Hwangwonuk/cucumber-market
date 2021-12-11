package com.cucumber.market.exception;

public class PageNotNaturalNumberException extends RuntimeException {
    public PageNotNaturalNumberException() {
        super();
    }

    public PageNotNaturalNumberException(String message) {
        super(message);
    }
}