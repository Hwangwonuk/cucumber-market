package com.cucumber.market.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BigCategoryRegisterResponse {
    private String redirectUrl;
}
