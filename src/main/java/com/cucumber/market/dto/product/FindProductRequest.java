package com.cucumber.market.dto.product;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class FindProductRequest {

    @NotBlank(message = "소분류 명을 입력해주세요.")
    private final String smallCategoryName;

    private final String title;
}
