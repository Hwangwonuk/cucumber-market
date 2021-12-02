package com.cucumber.market.service;

import com.cucumber.market.dto.*;

import java.util.List;


public interface CategoryService {

    CategoryResponse registerBigCategory(BigCategoryRegisterRequest bigCategoryRegisterRequest);

    CategoryResponse registerSmallCategory(SmallCategoryRegisterRequest smallCategoryRegisterRequest);

    void isDuplicateBigCategoryName(String bigCategoryName);

    void findByBigCategoryName(String bigCategoryName);

    void findBySmallCategoryName(String smallCategoryName);

    void isDuplicateSmallCategoryName(String smallCategoryName);

    CategoryResponse updateBigCategory(BigCategoryUpdateRequest bigCategoryUpdateRequest);

    CategoryResponse updateSmallCategory(SmallCategoryUpdateRequest smallCategoryUpdateRequest);

    List<CategoryDTO> findCategoryNames(CategoryNamesRequest categoryNamesRequest);
}
