package com.cucumber.market.dto.category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryNamesRequest {
    private final String bigCategoryName;
}
