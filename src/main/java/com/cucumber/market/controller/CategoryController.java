package com.cucumber.market.controller;

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
    public ResponseEntity<CategoryResponse> registerBigCategory(@Valid @RequestBody BigCategoryRegisterRequest request) {
        categoryService.isDuplicateBigCategoryName(request.getBigCategoryName());
        return new ResponseEntity<>(categoryService.registerBigCategory(request), HttpStatus.FOUND);
    }

    // 대분류(카테고리) 조회 - 조회할 대분류에 속하는 소분류 조회
    @GetMapping("/big")
    public ResponseEntity<List<CategoryDTO>> findCategoryNames(@Valid CategoryNamesRequest request) {
        return new ResponseEntity<>(categoryService.findCategoryNames(request), HttpStatus.OK);
    }

    // 대분류(카테고리) 이름 수정
    @PatchMapping("/big")
    public ResponseEntity<CategoryResponse> updateBigCategory(@Valid @RequestBody BigCategoryUpdateRequest request) {
        categoryService.findByBigCategoryName(request.getOldBigCategoryName());
        return new ResponseEntity<>(categoryService.updateBigCategory(request), HttpStatus.FOUND);
    }

    // 소분류(카테고리) 등록
    @PostMapping("/small")
    public ResponseEntity<CategoryResponse> signUpMember(@Valid @RequestBody SmallCategoryRegisterRequest request) {
        categoryService.findByBigCategoryName(request.getBigCategoryName());
        categoryService.isDuplicateSmallCategoryName(request.getSmallCategoryName());
        return new ResponseEntity<>(categoryService.registerSmallCategory(request), HttpStatus.FOUND);
    }

    // 소분류(카테고리) 이름 수정
    @PatchMapping("/small")
    public ResponseEntity<CategoryResponse> updateBigCategory(@Valid @RequestBody SmallCategoryUpdateRequest request) {
        categoryService.findByBigCategoryName(request.getBigCategoryName());
        categoryService.findBySmallCategoryName(request.getOldSmallCategoryName());
        categoryService.isDuplicateSmallCategoryName(request.getNewSmallCategoryName());
        return new ResponseEntity<>(categoryService.updateSmallCategory(request), HttpStatus.FOUND);
    }
}
