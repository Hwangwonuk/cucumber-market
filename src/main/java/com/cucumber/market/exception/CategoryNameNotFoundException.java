package com.cucumber.market.exception;

public class CategoryNameNotFoundException extends RuntimeException {
    public CategoryNameNotFoundException() {
        super();
    }

    public CategoryNameNotFoundException(String message) {
        super(message);
    }
}
