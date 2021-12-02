package com.cucumber.market.mapper;

import com.cucumber.market.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    void registerBigCategory(BigCategoryRegisterRequest bigCategoryRegisterRequest);

    void registerSmallCategory(SmallCategoryRegisterRequest smallCategoryRegisterRequest);

    int findBigCategoryNameCount(String bigCategoryName);

    int findSmallCategoryNameCount(String smallCategoryName);

    void updateBigCategory(BigCategoryUpdateRequest bigCategoryUpdateRequest);

    void updateSmallCategory(SmallCategoryUpdateRequest smallCategoryUpdateRequest);

    List<CategoryDTO> findCategoryNames(String bigCategoryName);
}
