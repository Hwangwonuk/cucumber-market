package com.cucumber.market.exception;

public class BigCategoryNotIncludeSmallCategoryException extends RuntimeException {
    public BigCategoryNotIncludeSmallCategoryException() {
        super();
    }

    public BigCategoryNotIncludeSmallCategoryException(String message) {
        super(message);
    }
}
