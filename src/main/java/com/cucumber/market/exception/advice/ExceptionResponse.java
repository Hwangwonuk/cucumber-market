package com.cucumber.market.exception.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse {
    private final String title;

    private final String detail;

    public ExceptionResponse(String detail) {
        this.title = ""; // TODO: 2021-12-04 title 필드 활용 정책 수립 후 리팩토링
        this.detail = detail;
    }
}
