package com.cucumber.market.exception;

public class PageNoPositiveException extends RuntimeException {
    public PageNoPositiveException() {
        super();
    }

    public PageNoPositiveException(String message) {
        super(message);
    }
}