package com.cucumber.market.service.impl;

import com.cucumber.market.dto.*;
import com.cucumber.market.exception.CategoryNameNotFoundException;
import com.cucumber.market.mapper.CategoryMapper;
import com.cucumber.market.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Value("${cucumber.category.url}")
    private String categoryUrl;

    /**
     * 대분류 등록 메소드
     * @param bigCategoryRegisterRequest 대분류 등록 시 저장할 이름
     */
    @Override
    public CategoryResponse registerBigCategory(BigCategoryRegisterRequest bigCategoryRegisterRequest) {
        categoryMapper.registerBigCategory(bigCategoryRegisterRequest);

        return CategoryResponse.builder()
                .redirectUrl(categoryUrl)
                .build();
    }

    /**
     * 대분류명 중복검사 메소드
     * @param bigCategoryName 중복 검사할 회원 아이디
     */
    @Override
    public void isDuplicateBigCategoryName(String bigCategoryName) {
        if (categoryMapper.findBigCategoryNameCount(bigCategoryName) == 1) {
            throw new DataIntegrityViolationException("중복된 대분류명 입니다.");
        }
    }

    /**
     * 대분류 이름 수정 메소드
     * @param bigCategoryUpdateRequest 원래 대분류명, 변경할 대분류명
     */
    @Override
    public CategoryResponse updateBigCategory(BigCategoryUpdateRequest bigCategoryUpdateRequest) {
        categoryMapper.updateBigCategory(bigCategoryUpdateRequest);
        return CategoryResponse.builder()
                .redirectUrl(categoryUrl)
                .build();
    }

    /**
     * 소분류 이름 수정 메소드
     * @param smallCategoryUpdateRequest 대분류명, 원래 소분류명, 변경할 소분류명
     */
    @Override
    public CategoryResponse updateSmallCategory(SmallCategoryUpdateRequest smallCategoryUpdateRequest) {
        categoryMapper.updateSmallCategory(smallCategoryUpdateRequest);
        return CategoryResponse.builder()
                .redirectUrl(categoryUrl)
                .build();
    }

    /**
     * 소분류 등록 메소드
     * @param smallCategoryRegisterRequest 소분류 등록 시 저장할 이름
     */
    @Override
    public CategoryResponse registerSmallCategory(SmallCategoryRegisterRequest smallCategoryRegisterRequest) {
        categoryMapper.registerSmallCategory(smallCategoryRegisterRequest);

        return CategoryResponse.builder()
                .redirectUrl(categoryUrl)
                .build();
    }

    /**
     * 대분류명 존재여부 검사 메소드
     * @param bigCategoryName 중복 검사할 회원 아이디
     */
    @Override
    public void findByBigCategoryName(String bigCategoryName) {
        if (categoryMapper.findBigCategoryNameCount(bigCategoryName) == 0) {
            throw new CategoryNameNotFoundException("입력한 대분류명은 존재하지 않습니다.");
        }
    }

    /**
     * 소분류명 중복검사 메소드
     * @param smallCategoryName 중복 검사할 회원 아이디
     */
    @Override
    public void isDuplicateSmallCategoryName(String smallCategoryName) {
        if (categoryMapper.findSmallCategoryNameCount(smallCategoryName) == 1) {
            throw new DataIntegrityViolationException("중복된 소분류명 입니다.");
        }
    }

    /**
     * 소분류명 존재여부 검사 메소드
     * @param smallCategoryName 존재하는지 확인하기 위한 소분류명
     */
    @Override
    public void findBySmallCategoryName(String smallCategoryName) {
        if (categoryMapper.findSmallCategoryNameCount(smallCategoryName) == 0) {
            throw new CategoryNameNotFoundException("입력한 소분류명은 존재하지 않습니다.");
        }
    }

    /**
     * 대분류명에 해당하는 소분류명 찾기 메소드
     * @param categoryNamesRequest 소분류를 불러올 대분류 이름
     * @return
     */
    @Override
    public List<CategoryDTO> findCategoryNames(CategoryNamesRequest categoryNamesRequest) {
        return categoryMapper.findCategoryNames(categoryNamesRequest.getBigCategoryName());
    }

}
