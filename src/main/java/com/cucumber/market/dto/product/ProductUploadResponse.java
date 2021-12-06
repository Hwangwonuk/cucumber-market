package com.cucumber.market.dto.product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductUploadResponse {
    private String redirectUrl;
}
