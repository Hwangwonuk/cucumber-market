package com.cucumber.market.dto.product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    private int productIdx;
}
