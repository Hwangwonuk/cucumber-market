package com.cucumber.market.controller;

import com.cucumber.market.annotation.CheckSignIn;
import com.cucumber.market.annotation.CurrentMember;
import com.cucumber.market.dto.member.CurrentMemberInfo;
import com.cucumber.market.dto.product.ProductUploadForm;
import com.cucumber.market.dto.product.ProductUploadResponse;
import com.cucumber.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // TODO: 2021-12-07 Springfox OpenAPI 3.0 MultiPartFile with JSON 적용 실패 이슈
//    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    @RequestBody(content = @Content(encoding = @Encoding(name = "productUploadRequest", contentType = "application/json")))
//    @CheckSignIn
//    public ResponseEntity<ProductUploadResponse> uploadProductV2(
//            @RequestPart(value = "productUploadRequest") ProductUploadRequest productUploadRequest,
//            @RequestPart(value = "images") List<MultipartFile> multipartFiles,
//            @CurrentMember CurrentMemberInfo currentMemberInfo) throws IOException {
//        return new ResponseEntity<>(productService.uploadProductV2(productUploadRequest, multipartFiles, currentMemberInfo.getMember_id()), HttpStatus.OK);
//    }

    @PostMapping
    @CheckSignIn
    public ResponseEntity<ProductUploadResponse> uploadProduct(
            @RequestParam("bigCategoryName") String bigCategoryName,
            @RequestParam("smallCategoryName") String smallCategoryName,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("price") String price,
            @RequestParam("deliveryPrice") String deliveryPrice,
            @RequestPart(value = "images") List<MultipartFile> multipartFiles,
            @CurrentMember CurrentMemberInfo currentMemberInfo) throws IOException {
        ProductUploadForm productUploadForm = ProductUploadForm.builder()
                .bigCategoryName(bigCategoryName)
                .smallCategoryName(smallCategoryName)
                .title(title)
                .content(content)
                .price(price)
                .deliveryPrice(deliveryPrice)
                .member_id(currentMemberInfo.getMember_id())
                .build();

        return new ResponseEntity<>(productService.uploadProduct(productUploadForm, multipartFiles, currentMemberInfo.getMember_id()), HttpStatus.OK);
    }
}