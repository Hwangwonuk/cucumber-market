package com.cucumber.market.dto.category;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class BigCategoryUpdateRequest {
    @NotBlank(message = "대분류 명을 입력해주세요.")
    private final String oldBigCategoryName;

    @NotBlank(message = "변경할 대분류 명을 입력해주세요.")
    private final String newBigCategoryName;
}
