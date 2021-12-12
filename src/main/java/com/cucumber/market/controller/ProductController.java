package com.cucumber.market.controller;

import com.cucumber.market.annotation.CheckSignIn;
import com.cucumber.market.annotation.CurrentMember;
import com.cucumber.market.dto.comment.CommentResponse;
import com.cucumber.market.dto.comment.ContentRequest;
import com.cucumber.market.dto.comment.ContentResponse;
import com.cucumber.market.dto.member.CurrentMemberInfo;
import com.cucumber.market.dto.product.*;
import com.cucumber.market.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    private final CategoryService categoryService;

    private final HopeService hopeService;

    private final CommentService commentService;

    private final ReplyService replyService;

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

    // 판매글 관련

    // TODO: 2021-12-07 이미지 파일업로드 보안정책 고려
    // 판매글 등록(썸네일, 상세이미지 포함)
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @CheckSignIn
    // @Transactional은 Spring에서 제공하는 DB Transaction 애노테이션
    // 기본적으로 Unchecked Exception, Error 만을 rollback함
    // 모든 예외에 대해서 rollback을 진행하고 싶을 경우 (rollbackFor = Exception.class) 를 붙여야함
    // Checked Exception (예: IOException) 같은 경우도 Exception 클래스를 상속하기 때문
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ProductResponse> uploadProduct(@ModelAttribute ProductUploadRequest request,
                                                         @CurrentMember CurrentMemberInfo currentMemberInfo) throws IOException {
        categoryService.checkExistBigCategoryName(request.getBigCategoryName());
        categoryService.checkExistSmallCategoryName(request.getSmallCategoryName());
        categoryService.checkBigCategoryIncludeSmallCategory(request.getBigCategoryName(), request.getSmallCategoryName());

        ProductUploadRequest productUploadRequest = ProductUploadRequest.builder()
                .bigCategoryName(request.getBigCategoryName())
                .smallCategoryName(request.getSmallCategoryName())
                .title(request.getTitle())
                .content(request.getContent())
                .price(request.getPrice())
                .deliveryPrice(request.getDeliveryPrice())
                .member_id(currentMemberInfo.getMember_id())
                .build();
        productService.uploadProduct(productUploadRequest);

        return new ResponseEntity<>(productService.uploadProductImages(request.getImages(), currentMemberInfo.getMember_id()), HttpStatus.OK);
    }

    // 판매글 검색 및 조회(최신순 페이징, 분류, 제목 검색기능 포함)
    @GetMapping
    public ResponseEntity<List<FindProductResponse>> findProductByPagination(@RequestParam(defaultValue = "1") int pageNum,
                                                                             @RequestParam(defaultValue = "10") int contentNum,
                                                                             @Valid FindProductRequest request) {
        categoryService.checkExistSmallCategoryName(request.getSmallCategoryName());

        return new ResponseEntity<>(productService.findProductByPagination(pageNum, contentNum, request.getSmallCategoryName(), request.getTitle()), HttpStatus.OK);
    }

    // 판매글 상세조회
    @GetMapping("{productIdx}")
    public ResponseEntity<FindDetailProductResponse> findDetailProduct(@PathVariable("productIdx") int productIdx) {
        productService.checkExistProduct(productIdx);
        productService.checkNotDeleteProduct(productIdx);

        return new ResponseEntity<>(productService.findDetailProduct(productIdx), HttpStatus.OK);
    }

    // 판매글 수정
    @PatchMapping("/{productIdx}/update")
    @CheckSignIn
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("productIdx") int productIdx,
                                                         @Valid @RequestBody ProductUpdateRequest request,
                                                         @CurrentMember CurrentMemberInfo currentMemberInfo) {
        productService.checkExistProduct(productIdx);
        productService.checkNotDeleteProduct(productIdx);
        productService.checkProductWriter(productIdx, currentMemberInfo.getMember_id());

        return new ResponseEntity<>(productService.updateProduct(productIdx, request, currentMemberInfo.getMember_id()), HttpStatus.OK);
    }

    // 판매글 판매완료 상태변경
    @PatchMapping("/{productIdx}/soldOut")
    @CheckSignIn
    public ResponseEntity<Void> soldOutProduct(@PathVariable("productIdx") int productIdx,
                                               @CurrentMember CurrentMemberInfo currentMemberInfo) {
        productService.checkExistProduct(productIdx);
        productService.checkNotSoldOutProduct(productIdx);
        productService.checkNotDeleteProduct(productIdx);
        productService.checkProductWriter(productIdx, currentMemberInfo.getMember_id());
        productService.soldOutProduct(productIdx, currentMemberInfo.getMember_id());

        return ResponseEntity.ok().build();
    }

    // 판매글 삭제(비활성화)
    @PatchMapping("/{productIdx}/delete")
    @CheckSignIn
    public ResponseEntity<Void> deleteProduct(@PathVariable("productIdx") int productIdx,
                                              @CurrentMember CurrentMemberInfo currentMemberInfo) {
        productService.checkExistProduct(productIdx);
        productService.checkNotDeleteProduct(productIdx);
        productService.checkProductWriter(productIdx, currentMemberInfo.getMember_id());
        productService.deleteProduct(productIdx, currentMemberInfo.getMember_id());

        return ResponseEntity.ok().build();
    }

    // 상품찜 관련

    // 상품찜(중복불가, 본인상품 찜 가능)
    @PostMapping("/{productIdx}/hope")
    @CheckSignIn
    public ResponseEntity<Void> registerHope(@PathVariable int productIdx,
                                             @CurrentMember CurrentMemberInfo currentMemberInfo) {
        productService.checkExistProduct(productIdx);
        productService.checkNotDeleteProduct(productIdx);
        hopeService.checkDuplicateHope(productIdx, currentMemberInfo.getMember_id());
        hopeService.registerHope(productIdx, currentMemberInfo.getMember_id());

        return ResponseEntity.ok().build();
    }

    // 상품찜 취소
    @DeleteMapping("/{productIdx}/hope")
    @CheckSignIn
    public ResponseEntity<Void> cancelHope(@PathVariable int productIdx,
                                           @CurrentMember CurrentMemberInfo currentMemberInfo) {
        productService.checkExistProduct(productIdx);
        productService.checkNotDeleteProduct(productIdx);
        hopeService.checkAlreadyHope(productIdx, currentMemberInfo.getMember_id());
        hopeService.cancelHope(productIdx, currentMemberInfo.getMember_id());

        return ResponseEntity.ok().build();
    }

    // 댓글관련

    // 댓글등록
    @PostMapping("/{productIdx}/comment")
    @CheckSignIn
    public ResponseEntity<ProductResponse> registerComment(@PathVariable int productIdx,
                                                           @Valid @RequestBody ContentRequest contentRequest,
                                                           @CurrentMember CurrentMemberInfo currentMemberInfo) {
        productService.checkExistProduct(productIdx);
        productService.checkNotDeleteProduct(productIdx);

        return new ResponseEntity<>(commentService.registerComment(productIdx, contentRequest.getContent(), currentMemberInfo.getMember_id()), HttpStatus.OK);
    }

    // 댓글조회(글 작성자 or 댓글 작성자만 조회가능)
    @GetMapping("{productIdx}/comments/{commentIdx}")
    @CheckSignIn
    public ResponseEntity<ContentResponse> getComment(@PathVariable int productIdx,
                                                      @PathVariable int commentIdx,
                                                      @CurrentMember CurrentMemberInfo currentMemberInfo) {
        commentService.checkExistComment(commentIdx);
        commentService.checkNotDeleteComment(commentIdx);
        productService.checkProductOrCommentWriter(productIdx, commentIdx, currentMemberInfo.getMember_id());
        commentService.checkProductIncludeComment(productIdx, commentIdx);

        return new ResponseEntity(commentService.getComment(commentIdx), HttpStatus.OK);
    }

    // 댓글수정
    @PatchMapping("/comments/{commentIdx}/update")
    @CheckSignIn
    public ResponseEntity<Void> updateComment(@PathVariable int commentIdx,
                                              @Valid @RequestBody ContentRequest request,
                                              @CurrentMember CurrentMemberInfo currentMemberInfo) {
        commentService.checkExistComment(commentIdx);
        commentService.checkNotDeleteComment(commentIdx);
        commentService.checkCommentWriter(commentIdx, currentMemberInfo.getMember_id());
        commentService.updateComment(commentIdx, request.getContent());

        return ResponseEntity.ok().build();
    }

    // 댓글삭제(비활성화)
    @PatchMapping("/comments/{commentIdx}/delete")
    @CheckSignIn
    public ResponseEntity<Void> deleteComment(@PathVariable int commentIdx,
                                              @CurrentMember CurrentMemberInfo currentMemberInfo) {
        commentService.checkExistComment(commentIdx);
        commentService.checkNotDeleteComment(commentIdx);
        commentService.checkCommentWriter(commentIdx, currentMemberInfo.getMember_id());
        commentService.deleteComment(commentIdx, currentMemberInfo.getMember_id());

        return ResponseEntity.ok().build();
    }

    // 대댓글 관련

    // 대댓글등록(글 작성자 or 댓글 작성자만 등록가능)
    @PostMapping("/{productIdx}/comments/{commentIdx}/reply")
    @CheckSignIn
    public ResponseEntity<CommentResponse> registerReply(@PathVariable int productIdx,
                                                         @PathVariable int commentIdx,
                                                         @Valid @RequestBody ContentRequest request,
                                                         @CurrentMember CurrentMemberInfo currentMemberInfo) {
        productService.checkExistProduct(productIdx);
        productService.checkNotDeleteProduct(productIdx);
        commentService.checkExistComment(commentIdx);
        commentService.checkNotDeleteComment(commentIdx);
        productService.checkProductOrCommentWriter(productIdx, commentIdx, currentMemberInfo.getMember_id());

        return new ResponseEntity<>(replyService.registerReply(commentIdx, request.getContent(), currentMemberInfo.getMember_id()), HttpStatus.OK);
    }

    // 대댓글조회(글 작성자 or 대댓글 작성자만 조회가능)
    @GetMapping("{productIdx}/comments/{commentIdx}/replies/{replyIdx}")
    @CheckSignIn
    public ResponseEntity<ContentResponse> getReply(@PathVariable int productIdx,
                                                    @PathVariable int commentIdx,
                                                    @PathVariable int replyIdx,
                                                    @CurrentMember CurrentMemberInfo currentMemberInfo) {
        replyService.checkExistReply(replyIdx);
        replyService.checkCommentIncludeReply(commentIdx, replyIdx);
        replyService.checkNotDeleteReply(replyIdx);

        // 현재 로그인한 사람이 댓글 작성자라면 판매자가 작성한 대댓글을 볼 수 있어야된다
        if (commentService.isCommentWriter(commentIdx, currentMemberInfo.getMember_id())) {
            return new ResponseEntity<>(replyService.getReply(replyIdx), HttpStatus.OK);
        } else { // 판매글 작성자거나 대댓글 작성자면
            productService.checkProductOrReplyWriter(productIdx, replyIdx, currentMemberInfo.getMember_id());
        }

        return new ResponseEntity<>(replyService.getReply(replyIdx), HttpStatus.OK);
    }

    // 대댓글수정
    @PatchMapping("/comments/replies/{replyIdx}/update")
    @CheckSignIn
    public ResponseEntity<Void> updateReply(@PathVariable int replyIdx,
                                            @Valid @RequestBody ContentRequest request,
                                            @CurrentMember CurrentMemberInfo currentMemberInfo) {
        replyService.checkExistReply(replyIdx);
        replyService.checkNotDeleteReply(replyIdx);
        replyService.checkReplyWriter(replyIdx, currentMemberInfo.getMember_id());
        replyService.updateReply(replyIdx, request.getContent());

        return ResponseEntity.ok().build();
    }

    // 대댓글삭제(비활성화)
    @PatchMapping("/comments/replies/{replyIdx}/delete")
    @CheckSignIn
    public ResponseEntity<Void> deleteReply(@PathVariable int replyIdx,
                                            @CurrentMember CurrentMemberInfo currentMemberInfo) {
        replyService.checkExistReply(replyIdx);
        replyService.checkNotDeleteReply(replyIdx);
        replyService.checkReplyWriter(replyIdx, currentMemberInfo.getMember_id());
        replyService.deleteReply(replyIdx, currentMemberInfo.getMember_id());

        return ResponseEntity.ok().build();
    }
}