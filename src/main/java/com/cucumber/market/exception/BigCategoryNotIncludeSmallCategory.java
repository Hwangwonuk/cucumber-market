package com.cucumber.market.exception;

public class BigCategoryNotIncludeSmallCategory extends RuntimeException {
    public BigCategoryNotIncludeSmallCategory() {
        super();
    }

    public BigCategoryNotIncludeSmallCategory(String message) {
        super(message);
    }
}
