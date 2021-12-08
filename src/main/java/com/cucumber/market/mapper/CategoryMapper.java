package com.cucumber.market.mapper;

import com.cucumber.market.dto.category.BigCategoryUpdateRequest;
import com.cucumber.market.dto.category.SmallCategoryNamesResponse;
import com.cucumber.market.dto.category.SmallCategoryRegisterRequest;
import com.cucumber.market.dto.category.SmallCategoryUpdateRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    void registerBigCategory(String bigCategoryName);

    int checkDuplicateBigCategoryName(String bigCategoryName);

    List<SmallCategoryNamesResponse> getSmallCategoryNames(String bigCategoryName);

    void updateBigCategory(BigCategoryUpdateRequest bigCategoryUpdateRequest);

    void registerSmallCategory(SmallCategoryRegisterRequest smallCategoryRegisterRequest);

    int checkDuplicateSmallCategoryName(String smallCategoryName);

    void updateSmallCategory(SmallCategoryUpdateRequest smallCategoryUpdateRequest);

}
