package com.cucumber.market.service;

import com.cucumber.market.dto.*;

import java.util.List;


public interface CategoryService {

    BigCategoryRegisterResponse registerBigCategory(BigCategoryRegisterRequest bigCategoryRegisterRequest);

    SmallCategoryRegisterResponse registerSmallCategory(SmallCategoryRegisterRequest smallCategoryRegisterRequest);

    void isDuplicateBigCategoryName(String bigCategoryName);

    void findByBigCategoryName(String bigCategoryName);

    void findBySmallCategoryName(String smallCategoryName);

    void isDuplicateSmallCategoryName(String smallCategoryName);

    BigCategoryUpdateResponse updateBigCategory(BigCategoryUpdateRequest bigCategoryUpdateRequest);

    SmallCategoryUpdateResponse updateSmallCategory(SmallCategoryUpdateRequest smallCategoryUpdateRequest);

    List<CategoryDTO> findCategoryNames(CategoryNamesRequest categoryNamesRequest);

/*    int registerSmallCategory(SmallCategoryDTO smallCategoryDTO);

    void updateBigCategory(BigCategoryDTO bigCategoryDTO);

    void updateSmallCategory(SmallCategoryDTO smallCategoryDTO);

    void deleteBigCategory(int bigCategoryId);

    void deleteSmallCategory(int smallCategoryId);*/
}
