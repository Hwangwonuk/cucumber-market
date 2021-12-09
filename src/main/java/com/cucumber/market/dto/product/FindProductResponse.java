package com.cucumber.market.dto.product;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class FindProductResponse {

    private final String productIdx;

    private final String bigCategoryName;

    private final String smallCategoryName;

    private final String title;

    private final String price;

    private final String member_id;

    private final String status;

    private final String updateTime;

    private final String storeFilename;

    private final String hope;

}
