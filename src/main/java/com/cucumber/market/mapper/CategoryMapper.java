package com.cucumber.market.mapper;

import com.cucumber.market.dto.BigCategoryRequest;
import com.cucumber.market.dto.SmallCategoryRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    void registerBigCategory(BigCategoryRequest bigCategoryRequest);

    void registerSmallCategory(SmallCategoryRequest smallCategoryRequest);

    int findBigCategoryNameCount(String bigCategoryName);

    int findSmallCategoryNameCount(String smallCategoryName);
/*    int registerSmallCategory(SmallCategoryDTO smallCategoryDTO);

    void updateBigCategory(BigCategoryDTO bigCategoryDTO);

    void updateSmallCategory(SmallCategoryDTO smallCategoryDTO);

    void deleteBigCategory(int bigCategoryId);

    void deleteSmallCategory(int smallCategoryId);*/
}
