package com.cucumber.market.controller;

import com.cucumber.market.annotation.CheckSignIn;
import com.cucumber.market.annotation.CurrentMember;
import com.cucumber.market.dto.member.CurrentMemberInfo;
import com.cucumber.market.dto.product.ContentRequest;
import com.cucumber.market.dto.product.ProductUploadForm;
import com.cucumber.market.dto.product.ProductUploadResponse;
import com.cucumber.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
/*
 @ModelAttribute 는 HTTP 요청 파라미터(URL 쿼리 스트링, POST Form)를 다룰 때 사용한다.
 @RequestBody 는 HTTP Body의 데이터를 객체로 변환할 때 사용한다. 주로 API JSON 요청을 다룰 때 사용한다
* */
// TODO: 2021-12-08 상품찜, 댓글, 대댓글 리스폰스 객체 생성
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // TODO: 2021-12-07 Springfox OpenAPI 3.0 MultiPartFile with JSON 적용 실패 이슈
    // JSON 형식으로 객체와 MultipartFile 파일을 RequestBody에 담으려 했으나 스웨거 테스트 하는 과정에서 이슈발생(깃허브 이슈등록)
//    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    @RequestBody(content = @Content(encoding = @Encoding(name = "productUploadRequest", contentType = "application/json")))
//    @CheckSignIn
//    public ResponseEntity<ProductUploadResponse> uploadProductV2(
//            @RequestPart(value = "productUploadRequest") ProductUploadRequest productUploadRequest,
//            @RequestPart(value = "images") List<MultipartFile> multipartFiles,
//            @CurrentMember CurrentMemberInfo currentMemberInfo) throws IOException {
//        return new ResponseEntity<>(productService.uploadProductV2(productUploadRequest, multipartFiles, currentMemberInfo.getMember_id()), HttpStatus.OK);
//    }

    // 판매글 등록(다중 이미지 포함) TODO: 2021-12-07 이미지 파일업로드 보안정책 고려, 예외처리방식 리팩토링
    @PostMapping
    @CheckSignIn
    public ResponseEntity<ProductUploadResponse> uploadProduct(
            @RequestParam("bigCategoryName") String bigCategoryName,
            @RequestParam("smallCategoryName") String smallCategoryName,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("price") String price,
            @RequestParam("deliveryPrice") String deliveryPrice,
            @RequestPart(value = "images", required = false) List<MultipartFile> multipartFiles,
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

    // 상품조회기능 -> 제목 등의 product 컬럼들 join 문으로 파일테이블의 이미지 파일경로 + 상품찜 수 + 댓글수 뷰로 만들기


    // 상품찜(중복불가, 본인상품 찜 가능)
    @PostMapping("/{productIdx}/hope")
    @CheckSignIn
    public ResponseEntity<Void> registerHope(@PathVariable int productIdx,
                                  @CurrentMember CurrentMemberInfo currentMemberInfo) {
        productService.checkDuplicateHope(productIdx, currentMemberInfo.getMember_id());
        productService.registerHope(productIdx, currentMemberInfo.getMember_id());

        return ResponseEntity.ok().build();
    }

    // 상품찜 취소
    @DeleteMapping("/{productIdx}/hope")
    @CheckSignIn
    public ResponseEntity<Void> cancelHope(@PathVariable int productIdx,
                                        @CurrentMember CurrentMemberInfo currentMemberInfo) {
        productService.checkAlreadyHope(productIdx, currentMemberInfo.getMember_id());
        productService.cancelHope(productIdx, currentMemberInfo.getMember_id());

        return ResponseEntity.ok().build();
    }

    // 댓글등록
    @PostMapping("/{productIdx}/comment")
    @CheckSignIn
    public ResponseEntity<Void> registerComment(@PathVariable int productIdx,
                                             @Valid @RequestBody ContentRequest contentRequest,
                                             @CurrentMember CurrentMemberInfo currentMemberInfo) {
        productService.registerComment(productIdx, contentRequest.getContent(), currentMemberInfo.getMember_id());
        return ResponseEntity.ok().build();
    }

    // 댓글수정
    @PatchMapping("/comments/{commentIdx}/update")
    @CheckSignIn
    public ResponseEntity<Void> updateComment(@PathVariable int commentIdx,
                                           @Valid @RequestBody ContentRequest request,
                                           @CurrentMember CurrentMemberInfo currentMemberInfo) {
        productService.checkNotDeleteComment(commentIdx);
        productService.checkCommentWriter(commentIdx, currentMemberInfo.getMember_id());
        productService.updateComment(commentIdx, request.getContent());
        return ResponseEntity.ok().build();
    }

    // 댓글삭제
    @PatchMapping("/comments/{commentIdx}/delete")
    @CheckSignIn
    public ResponseEntity<Void> deleteComment(@PathVariable int commentIdx,
                                           @CurrentMember CurrentMemberInfo currentMemberInfo) {
        productService.checkNotDeleteComment(commentIdx);
        productService.checkCommentWriter(commentIdx, currentMemberInfo.getMember_id());
        productService.deleteComment(commentIdx, currentMemberInfo.getMember_id());
        return ResponseEntity.ok().build();
    }

    // 대댓글등록
    @PostMapping("/{productIdx}/comments/{commentIdx}/reply")
    @CheckSignIn
    public ResponseEntity<Void> registerReply(@PathVariable int productIdx,
                                           @PathVariable int commentIdx,
                                           @Valid @RequestBody ContentRequest request,
                                           @CurrentMember CurrentMemberInfo currentMemberInfo) {
        // productIdx의 작성자이거나, commentIdx의 작성자인가
        productService.checkProductOrCommentWriter(productIdx, commentIdx, currentMemberInfo.getMember_id());
        productService.registerReply(commentIdx, request.getContent(), currentMemberInfo.getMember_id());
        return ResponseEntity.ok().build();
    }

    // 대댓글수정
    @PatchMapping("/comments/replies/{replyIdx}/update")
    @CheckSignIn
    public ResponseEntity<Void> updateReply(@PathVariable int replyIdx,
                                         @Valid @RequestBody ContentRequest request,
                                         @CurrentMember CurrentMemberInfo currentMemberInfo) {
        productService.checkNotDeleteReply(replyIdx);
        productService.checkReplyWriter(replyIdx, currentMemberInfo.getMember_id());
        productService.updateReply(replyIdx, request.getContent());
        return ResponseEntity.ok().build();
    }

    // 대댓글삭제
    @PatchMapping("/comments/replies/{replyIdx}/delete")
    @CheckSignIn
    public ResponseEntity<Void> deleteReply(@PathVariable int replyIdx,
                                         @CurrentMember CurrentMemberInfo currentMemberInfo) {
        productService.checkNotDeleteReply(replyIdx);
        productService.checkReplyWriter(replyIdx, currentMemberInfo.getMember_id());
        productService.deleteReply(replyIdx, currentMemberInfo.getMember_id());
        return ResponseEntity.ok().build();
    }
}