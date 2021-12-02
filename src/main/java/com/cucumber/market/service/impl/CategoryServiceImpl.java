package com.cucumber.market.service.impl;

import com.cucumber.market.dto.BigCategoryRequest;
import com.cucumber.market.dto.BigCategoryResponse;
import com.cucumber.market.dto.SmallCategoryRequest;
import com.cucumber.market.dto.SmallCategoryResponse;
import com.cucumber.market.mapper.CategoryMapper;
import com.cucumber.market.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Value("${cucumber.category.url}")
    private String categoryUrl;

    /**
     * 대분류 등록 메소드
     * @param bigCategoryRequest 대분류 등록 시 저장할 이름
     */
    @Override
    public BigCategoryResponse registerBigCategory(BigCategoryRequest bigCategoryRequest) {
        categoryMapper.registerBigCategory(bigCategoryRequest);

        return BigCategoryResponse.builder()
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
     * 소분류 등록 메소드
     * @param smallCategoryRequest 소분류 등록 시 저장할 이름
     */
    @Override
    public SmallCategoryResponse registerSmallCategory(SmallCategoryRequest smallCategoryRequest) {
        categoryMapper.registerSmallCategory(smallCategoryRequest);

        return SmallCategoryResponse.builder()
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
            throw new DataIntegrityViolationException("입력한 대분류명은 존재하지 않습니다.");
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

}
