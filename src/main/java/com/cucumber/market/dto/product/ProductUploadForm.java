package com.cucumber.market.dto.product;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class ProductUploadForm {
    @NotBlank(message = "대분류를 입력해주세요.")
    private final String bigCategoryName;

    @NotBlank(message = "소분류를 입력해주세요.")
    private final String smallCategoryName;

    @NotBlank(message = "제목을 입력해주세요.")
    private final String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    //@NotBlank(message = "가격을 입력해주세요.")
    private final String price;

    //@NotBlank(message = "배송비를 입력해주세요.")
    private final String deliveryPrice;

    private final String member_id;


}
