package com.cucumber.market.exception.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse {
    private final String title;

    private final String detail;

    public ExceptionResponse(String detail) {
        this.title = "";
        this.detail = detail;
    }
}
