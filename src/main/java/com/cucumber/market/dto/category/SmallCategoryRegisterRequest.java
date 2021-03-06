package com.cucumber.market.dto.category;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class SmallCategoryRegisterRequest {
    @NotBlank(message = "대분류 명을 입력해주세요.")
    private final String bigCategoryName;

    @NotBlank(message = "소분류 명을 입력해주세요.")
    private final String smallCategoryName;
}
