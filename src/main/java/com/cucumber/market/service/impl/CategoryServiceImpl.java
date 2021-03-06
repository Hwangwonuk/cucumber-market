package com.cucumber.market.service.impl;

import com.cucumber.market.dto.category.*;
import com.cucumber.market.exception.BigCategoryNotIncludeSmallCategoryException;
import com.cucumber.market.exception.NotExistCategoryNameException;
import com.cucumber.market.mapper.CategoryMapper;
import com.cucumber.market.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Value("${cucumber.category.url}")
    private String categoryUrl;

    /**
     * 대분류 등록 메소드
     *
     * @param bigCategoryNameRequest 대분류 등록 시 저장할 이름
     */
    @Override
    public CategoryResponse registerBigCategory(BigCategoryNameRequest bigCategoryNameRequest) {
        categoryMapper.registerBigCategory(bigCategoryNameRequest.getBigCategoryName());

        return CategoryResponse.builder()
                .redirectUrl(categoryUrl)
                .build();
    }

    /**
     * 대분류명 중복검사 메소드
     *
     * @param bigCategoryName 중복 검사할 회원 아이디
     */
    @Override
    public void checkDuplicateBigCategoryName(String bigCategoryName) {
        if (categoryMapper.checkDuplicateBigCategoryName(bigCategoryName) == 1) {
            throw new DataIntegrityViolationException("중복된 대분류명 입니다.");
        }
    }

    /**
     * 모든 대분류명 조회 메소드
     *
     * @return 대분류 전체 목록
     */
    @Override
    @Transactional(readOnly = true)
    public List<BigCategoryNamesResponse> getBigCategoryNames() {
        return categoryMapper.getBigCategoryNames();
    }

    /**
     * 대분류명에 해당하는 소분류명 찾기 메소드
     *
     * @param bigCategoryNameRequest 소분류를 불러올 대분류 이름
     * @return 대분류에 해당하는 소분류 목록
     */
    @Override
    @Transactional(readOnly = true)
    public List<SmallCategoryNamesResponse> getSmallCategoryNames(BigCategoryNameRequest bigCategoryNameRequest) {
        return categoryMapper.getSmallCategoryNames(bigCategoryNameRequest.getBigCategoryName());
    }

    /**
     * 대분류명 존재여부 검사 메소드
     *
     * @param bigCategoryName 중복 검사할 회원 아이디
     */
    @Override
    public void checkExistBigCategoryName(String bigCategoryName) {
        if (categoryMapper.checkDuplicateBigCategoryName(bigCategoryName) == 0) {
            throw new NotExistCategoryNameException("입력한 대분류명은 존재하지 않습니다.");
        }
    }

    /**
     * 대분류 이름 수정 메소드
     *
     * @param bigCategoryUpdateRequest 원래 대분류명, 변경할 대분류명
     * @return 카테고리 페이지 URL
     */
    @Override
    public CategoryResponse updateBigCategory(BigCategoryUpdateRequest bigCategoryUpdateRequest) {
        categoryMapper.updateBigCategory(bigCategoryUpdateRequest);
        return CategoryResponse.builder()
                .redirectUrl(categoryUrl)
                .build();
    }

    /**
     * 소분류 등록 메소드
     *
     * @param smallCategoryRegisterRequest 소분류 등록 시 저장할 이름
     * @return 카테고리 페이지 URL
     */
    @Override
    public CategoryResponse registerSmallCategory(SmallCategoryRegisterRequest smallCategoryRegisterRequest) {
        categoryMapper.registerSmallCategory(smallCategoryRegisterRequest);

        return CategoryResponse.builder()
                .redirectUrl(categoryUrl)
                .build();
    }

    /**
     * 소분류명 중복검사 메소드
     *
     * @param smallCategoryName 중복 검사할 회원 아이디
     */
    @Override
    public void checkDuplicateSmallCategoryName(String smallCategoryName) {
        if (categoryMapper.checkDuplicateSmallCategoryName(smallCategoryName) == 1) {
            throw new DataIntegrityViolationException("중복된 소분류명 입니다.");
        }
    }

    /**
     * 소분류 이름 수정 메소드
     *
     * @param smallCategoryUpdateRequest 대분류명, 원래 소분류명, 변경할 소분류명
     * @return 카테고리 페이지 URL
     */
    @Override
    public CategoryResponse updateSmallCategory(SmallCategoryUpdateRequest smallCategoryUpdateRequest) {
        categoryMapper.updateSmallCategory(smallCategoryUpdateRequest);
        return CategoryResponse.builder()
                .redirectUrl(categoryUrl)
                .build();
    }

    /**
     * 소분류명 존재여부 검사 메소드
     *
     * @param smallCategoryName 존재하는지 확인하기 위한 소분류명
     */
    @Override
    public void checkExistSmallCategoryName(String smallCategoryName) {
        if (categoryMapper.checkDuplicateSmallCategoryName(smallCategoryName) == 0) {
            throw new NotExistCategoryNameException("입력한 소분류명은 존재하지 않습니다.");
        }
    }

    /**
     * 대분류에 속하는 소분류인지 검사 메소드
     *
     * @param bigCategoryName 대분류명
     * @param smallCategoryName 소분류명
     */
    @Override
    public void checkBigCategoryIncludeSmallCategory(String bigCategoryName, String smallCategoryName) {
        if (categoryMapper.checkBigCategoryIncludeSmallCategory(bigCategoryName, smallCategoryName) == 0) {
            throw new BigCategoryNotIncludeSmallCategoryException("대분류에 속하지않는 소분류명 입니다.");
        }
    }

}
