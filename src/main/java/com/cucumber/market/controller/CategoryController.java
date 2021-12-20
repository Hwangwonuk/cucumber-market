package com.cucumber.market.controller;

import com.cucumber.market.annotation.CheckAdmin;
import com.cucumber.market.annotation.CheckSignIn;
import com.cucumber.market.dto.category.*;
import com.cucumber.market.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // 대분류(카테고리) 등록
    @PostMapping("/big")
    @CheckSignIn
    @CheckAdmin
    public ResponseEntity<CategoryResponse> registerBigCategory(@Valid @RequestBody BigCategoryNameRequest request) {
        categoryService.checkDuplicateBigCategoryName(request.getBigCategoryName());
        return new ResponseEntity<>(categoryService.registerBigCategory(request), HttpStatus.FOUND);
    }

    // 대분류(카테고리) 전체 조회
    @GetMapping("/big")
    public ResponseEntity<List<BigCategoryNamesResponse>> getBigCategoryNames() {
        return new ResponseEntity<>(categoryService.getBigCategoryNames(), HttpStatus.OK);
    }

    // 대분류(카테고리) 이름 수정
    @PatchMapping("/big")
    @CheckSignIn
    @CheckAdmin
    public ResponseEntity<CategoryResponse> updateBigCategory(@Valid @RequestBody BigCategoryUpdateRequest request) {
        categoryService.checkExistBigCategoryName(request.getOldBigCategoryName());
        return new ResponseEntity<>(categoryService.updateBigCategory(request), HttpStatus.FOUND);
    }

    // 소분류(카테고리) 등록
    @PostMapping("/small")
    @CheckSignIn
    @CheckAdmin
    public ResponseEntity<CategoryResponse> registerSmallCategory(@Valid @RequestBody SmallCategoryRegisterRequest request) {
        categoryService.checkExistBigCategoryName(request.getBigCategoryName());
        categoryService.checkDuplicateSmallCategoryName(request.getSmallCategoryName());
        return new ResponseEntity<>(categoryService.registerSmallCategory(request), HttpStatus.FOUND);
    }

    // 조회할 대분류에 속하는 모든 소분류(카테고리) 조회
    @GetMapping("/small")
    public ResponseEntity<List<SmallCategoryNamesResponse>> getSmallCategoryNames(@Valid BigCategoryNameRequest request) {
        categoryService.checkExistBigCategoryName(request.getBigCategoryName());
        return new ResponseEntity<>(categoryService.getSmallCategoryNames(request), HttpStatus.OK);
    }

    // 소분류(카테고리) 이름 수정
    @PatchMapping("/small")
    @CheckSignIn
    @CheckAdmin
    public ResponseEntity<CategoryResponse> updateBigCategory(@Valid @RequestBody SmallCategoryUpdateRequest request) {
        categoryService.checkExistBigCategoryName(request.getBigCategoryName());
        categoryService.checkExistSmallCategoryName(request.getOldSmallCategoryName());
        categoryService.checkDuplicateSmallCategoryName(request.getNewSmallCategoryName());
        return new ResponseEntity<>(categoryService.updateSmallCategory(request), HttpStatus.FOUND);
    }
}
