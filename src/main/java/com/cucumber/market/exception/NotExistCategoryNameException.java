package com.cucumber.market.exception;

public class NotExistCategoryNameException extends RuntimeException {
    public NotExistCategoryNameException() {
        super();
    }

    public NotExistCategoryNameException(String message) {
        super(message);
    }
}
