package com.cucumber.market.exception;

public class ProductNotIncludeCommentException extends RuntimeException {
    public ProductNotIncludeCommentException() {
        super();
    }

    public ProductNotIncludeCommentException(String message) {
        super(message);
    }
}
