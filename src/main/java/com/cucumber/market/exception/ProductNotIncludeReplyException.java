package com.cucumber.market.exception;

public class ProductNotIncludeReplyException extends RuntimeException {
    public ProductNotIncludeReplyException() {
        super();
    }

    public ProductNotIncludeReplyException(String message) {
        super(message);
    }
}
