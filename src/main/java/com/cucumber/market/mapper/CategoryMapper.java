package com.cucumber.market.mapper;

import com.cucumber.market.dto.category.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    void registerBigCategory(String bigCategoryName);

    int checkDuplicateBigCategoryName(String bigCategoryName);

    List<BigCategoryNamesResponse> getBigCategoryNames();

    List<SmallCategoryNamesResponse> getSmallCategoryNames(String bigCategoryName);

    void updateBigCategory(BigCategoryUpdateRequest bigCategoryUpdateRequest);

    void registerSmallCategory(SmallCategoryRegisterRequest smallCategoryRegisterRequest);

    int checkDuplicateSmallCategoryName(String smallCategoryName);

    void updateSmallCategory(SmallCategoryUpdateRequest smallCategoryUpdateRequest);

    int checkBigCategoryIncludeSmallCategory(@Param("bigCategoryName") String bigCategoryName, @Param("smallCategoryName") String smallCategoryName);

}
