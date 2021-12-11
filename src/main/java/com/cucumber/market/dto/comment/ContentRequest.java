package com.cucumber.market.dto.comment;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class ContentRequest {
    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;
}
