package com.cucumber.market.dto.product;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class ContentResponse {

    private final String member_id;

    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    private final String updateTime;
}
