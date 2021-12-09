package com.cucumber.market.dto.product;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class ProductUpdateRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    private final String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    @NotBlank(message = "가격을 입력해주세요.")
    @Pattern(regexp = "[0-9]", message = "가격은 숫자만 입력이 가능합니다.")
    private final String price;

    @NotBlank(message = "배송비를 입력해주세요.")
    @Pattern(regexp = "[0-9]", message = "배송비는 숫자만 입력이 가능합니다.")
    private final String deliveryPrice;

    private final int productIdx;

    private final String member_id;
}
