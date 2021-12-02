package com.cucumber.market.controller;

import com.cucumber.market.dto.*;
import com.cucumber.market.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // 대분류 등록
    @PostMapping("/big")
    public ResponseEntity<BigCategoryResponse> signUpMember(@Valid @RequestBody BigCategoryRequest request) {
        categoryService.isDuplicateBigCategoryName(request.getBigCategoryName());
        return new ResponseEntity<>(categoryService.registerBigCategory(request), HttpStatus.FOUND);
    }

    // 소분류 등록
    @PostMapping("/small")
    public ResponseEntity<SmallCategoryResponse> signUpMember(@Valid @RequestBody SmallCategoryRequest request) {
        categoryService.findByBigCategoryName(request.getBigCategoryName());
        categoryService.isDuplicateSmallCategoryName(request.getSmallCategoryName());
        return new ResponseEntity<>(categoryService.registerSmallCategory(request), HttpStatus.FOUND);
    }

}
