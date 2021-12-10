package com.cucumber.market.dto.product;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Builder
public class ProductUploadRequest {
    @NotBlank(message = "대분류를 입력해주세요.")
    private final String bigCategoryName;

    @NotBlank(message = "소분류를 입력해주세요.")
    private final String smallCategoryName;

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

    @NotBlank
    private final List<MultipartFile> images;

    private final String member_id;


}
