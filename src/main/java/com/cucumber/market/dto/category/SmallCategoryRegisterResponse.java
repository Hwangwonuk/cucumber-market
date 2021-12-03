package com.cucumber.market.dto.category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SmallCategoryRegisterResponse {
    private String redirectUrl;
}
