package com.cucumber.market.service;

import com.cucumber.market.dto.category.*;

import java.util.List;

public interface CategoryService {
    CategoryResponse registerBigCategory(BigCategoryNameRequest bigCategoryNameRequest);

    void checkDuplicateBigCategoryName(String bigCategoryName);

    List<SmallCategoryNamesResponse> getSmallCategoryNames(BigCategoryNameRequest bigCategoryNameRequest);

    void checkExistBigCategoryName(String bigCategoryName);

    CategoryResponse updateBigCategory(BigCategoryUpdateRequest bigCategoryUpdateRequest);

    CategoryResponse registerSmallCategory(SmallCategoryRegisterRequest smallCategoryRegisterRequest);

    void checkDuplicateSmallCategoryName(String smallCategoryName);

    CategoryResponse updateSmallCategory(SmallCategoryUpdateRequest smallCategoryUpdateRequest);

    void checkExistSmallCategoryName(String smallCategoryName);

}
