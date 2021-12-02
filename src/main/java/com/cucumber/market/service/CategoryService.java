package com.cucumber.market.service;

import com.cucumber.market.dto.BigCategoryRequest;
import com.cucumber.market.dto.BigCategoryResponse;
import com.cucumber.market.dto.SmallCategoryRequest;
import com.cucumber.market.dto.SmallCategoryResponse;

public interface CategoryService {

    BigCategoryResponse registerBigCategory(BigCategoryRequest bigCategoryRequest);

    SmallCategoryResponse registerSmallCategory(SmallCategoryRequest smallCategoryRequest);

    void isDuplicateBigCategoryName(String bigCategoryName);

    void findByBigCategoryName(String bigCategoryName);

    void isDuplicateSmallCategoryName(String smallCategoryName);

/*    int registerSmallCategory(SmallCategoryDTO smallCategoryDTO);

    void updateBigCategory(BigCategoryDTO bigCategoryDTO);

    void updateSmallCategory(SmallCategoryDTO smallCategoryDTO);

    void deleteBigCategory(int bigCategoryId);

    void deleteSmallCategory(int smallCategoryId);*/
}
