package com.cucumber.market.service.impl;

import com.cucumber.market.dto.file.FileUploadForm;
import com.cucumber.market.dto.product.*;
import com.cucumber.market.exception.*;
import com.cucumber.market.file.FileStore;
import com.cucumber.market.mapper.CommentMapper;
import com.cucumber.market.mapper.FileMapper;
import com.cucumber.market.mapper.ProductMapper;
import com.cucumber.market.mapper.ReplyMapper;
import com.cucumber.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    private final CommentMapper commentMapper;

    private final ReplyMapper replyMapper;

    private final FileMapper fileMapper;

    private final FileStore fileStore;

    @Value("${cucumber.product.url}")
    private String productUrl;

//    @Override
//    public ProductUploadResponse uploadProductV2(ProductUploadRequest productUploadRequest,
//                                                 List<MultipartFile> multipartFiles,
//                                                 String member_id) throws IOException {
//        ProductUploadForm productUploadForm = ProductUploadForm.builder()
//                .bigCategoryName(productUploadRequest.getBigCategoryName())
//                .smallCategoryName(productUploadRequest.getSmallCategoryName())
//                .title(productUploadRequest.getTitle())
//                .content(productUploadRequest.getContent())
//                .price(productUploadRequest.getPrice())
//                .deliveryPrice(productUploadRequest.getDeliveryPrice())
//                .member_id(member_id)
//                .build();
//
//        productMapper.uploadProduct(productUploadForm); // 글등록(파일제외)
//
//        int productIdx = productMapper.getMyLatestProduct(member_id); // 방금 등록된 글 idx
//
//        List<FileUploadForm> storeImageFiles = fileStore.storeFiles(productIdx, multipartFiles); // 파일명 변환
//        fileMapper.save(storeImageFiles); // 파일 테이블에 글 idx, uploadfilename, storefilename 저장
//
//        int thumbnailIdx = fileMapper.getMyThumbnailIdx(productIdx); // 해당 글 idx로 저장된 파일중 file_idx가 가장작은(먼저등록된) 이미지의 file_idx
//        productMapper.updateThumbnailIdx(productIdx, thumbnailIdx); // 썸네일 idx로 업데이트
//
//        return ProductUploadResponse.builder()
//                .redirectUrl(productUrl)
//                .build();
//    }

    /**
     * 판매글 등록 메소드
     * @param productUploadRequest 글 등록시 필요한 데이터
     */
    // uploadProduct에서는 DB 관련 메서드를 하나만 사용하기 때문에 (productMapper.uploadProduct)
    // 어차피 @Transactional 없이도 DB에 commit 되거나 안되거나 둘 중 하나만 됨
    // 따라서 @Transactional 사용 안함
    @Override
    public void uploadProduct(ProductUploadRequest productUploadRequest) {
        productMapper.uploadProduct(productUploadRequest); // 글등록(파일제외)
    }

    /**
     * 판매글 등록 시 함께 호출될 썸네일 이미지, 상세페이지 이미지 등록 메소드
     * @param images 등록한 이미지들
     * @param member_id 로그인한 회원의 아이디
     */
    // @Transactional은 Spring에서 제공하는 DB Transaction 애노테이션
    // 기본적으로 Unchecked Exception, Error 만을 rollback함
    @Override
    @Transactional
    public ProductResponse uploadProductImages(List<MultipartFile> images, String member_id) throws IOException {
        int productIdx = productMapper.getMyLatestProduct(member_id); // 방금 등록된 글 idx

        List<FileUploadForm> storeImageFiles = fileStore.storeFiles(productIdx, images); // 파일명 변환
        fileMapper.save(storeImageFiles); // 파일 테이블에 글 idx, uploadfilename, storefilename 저장

        int thumbnailIdx = fileMapper.getMyThumbnailIdx(productIdx); // 해당 글 idx로 저장된 파일중 file_idx가 가장작은(먼저등록된) 이미지의 file_idx
        productMapper.updateThumbnailIdx(productIdx, thumbnailIdx); // 썸네일 idx로 업데이트

        return ProductResponse.builder()
                .productIdx(productIdx)
                .build();
    }

    /**
     * 판매글 조회 메소드(페이징, 검색)
     * @param pageNum 페이지 번호
     * @param contentNum 보여줄 개수
     * @param smallCategoryName 소분류명
     * @param title 제목
     * @return 글번호, 대분류, 소분류, 제목, 가격, 작성자, status(a,b), 수정시간, 썸네일 파일경로, 상품찜 수 MySQL 뷰 활용
     */
    @Override
    public List<FindProductResponse> findProductByPagination(int pageNum, int contentNum, String smallCategoryName, String title) {
        if (pageNum <= 0)
            throw new PageNoPositiveException("페이지는 1 이상 이어야 합니다.");

        int offset = (pageNum - 1) * contentNum;

        String findTitle;
        if(title == null)
            findTitle = "%%";
        else
            findTitle = "%" + title + "%";

        return productMapper.findProductByPagination(contentNum, offset, smallCategoryName, findTitle);
    }

    /**
     * 판매글 존재여부 검사 메소드
     * @param productIdx 판매글 번호
     */
    @Override
    public void checkExistProduct(int productIdx) {
        if (productMapper.checkExistProduct(productIdx) == 0) {
            throw new NotExistProductException("글번호에 해당하는 판매글이 존재하지 않습니다.");
        }
    }

    /**
     * 판매글 상세조회 메소드
     * @param productIdx 판매글 번호
     * @return 판매글 상세정보 (글번호, 작성자, 제목, 내용, 가격, 배송비,  글상태, 수정시간, 해당글의 모든 이미지 경로, 글번호에 속한 댓글번호, 대댓글번호)
     */
    @Override
    public FindDetailProductResponse findDetailProduct(int productIdx) {
        return productMapper.findDetailProduct(productIdx);
    }

    /**
     * 판매글 작성자 여부 확인 메소드
     * @param productIdx 글번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void checkProductWriter(int productIdx, String member_id) {
        if (productMapper.checkProductWriter(productIdx, member_id) == 0) {
            throw new NoWriterForProductException("해당 판매글의 작성자가 아닙니다.");
        }
    }

    /**
     * 판매글 수정 메소드
     * @param productUpdateRequest 업데이트할 정보
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public ProductResponse updateProduct(int productIdx, ProductUpdateRequest productUpdateRequest, String member_id) {
        ProductUpdateRequest updateRequest = ProductUpdateRequest.builder()
                .title(productUpdateRequest.getTitle())
                .content(productUpdateRequest.getContent())
                .price(productUpdateRequest.getPrice())
                .deliveryPrice(productUpdateRequest.getDeliveryPrice())
                .productIdx(productIdx)
                .member_id(member_id).build();

        productMapper.updateProduct(updateRequest);
        return ProductResponse.builder()
                .productIdx(productIdx)
                .build();
    }

    /**
     * 판매글 판매완료 여부 확인 메소드
     * @param productIdx 판매글 번호
     */
    @Override
    public void checkNotSoldOutProduct(int productIdx) {
        if (productMapper.checkNotSoldOutProduct(productIdx) == 1) {
            throw new AlreadySoldOutProductException("이미 삭제된 판매글입니다.");
        }
    }

    /**
     * 판매글 판매완료 상태변경 메소드
     * @param productIdx 판매글 번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void soldOutProduct(int productIdx, String member_id) {
        productMapper.soldOutProduct(productIdx, member_id);
    }

    /**
     * 판매글 삭제여부 확인 메소드
     * @param productIdx 판매글 번호
     */
    @Override
    public void checkNotDeleteProduct(int productIdx) {
        if (productMapper.checkNotDeleteProduct(productIdx) == 1) {
            throw new AlreadyDeleteProductException("이미 삭제된 판매글입니다.");
        }
    }

    /**
     * 판매글 삭제 메소드
     * @param productIdx 판매글 번호
     * @param member_id 현재 접속한 회원의 아이디
     */
    @Override
    public void deleteProduct(int productIdx, String member_id) {
        productMapper.deleteProduct(productIdx, member_id);
    }

    /**
     * 상품글 작성자 or 댓글 작성자 검사 메소드
     * @param productIdx 글번호
     * @param commentIdx 댓글번호
     * @param member_id 대댓글 작성 or 조회를 시도하는 회원의 아이디
     */
    @Override
    public void checkProductOrCommentWriter(int productIdx, int commentIdx, String member_id) {
        if (productMapper.checkProductWriter(productIdx, member_id) == 0 && commentMapper.checkCommentWriter(commentIdx, member_id) == 0) {
            throw new NoWriterForProductOrCommentException("해당 글이나 댓글의 작성자가 아닙니다.");
        }
    }

    /**
     * 상품글 작성자 or 대댓글 작성자 검사 메소드
     * @param productIdx 글번호
     * @param replyIdx 대댓글번호
     * @param member_id 대댓글 조회를 시도하는 회원의 아이디
     */
    @Override
    public void checkProductOrReplyWriter(int productIdx, int replyIdx, String member_id) {
        if (productMapper.checkProductWriter(productIdx, member_id) == 0 && replyMapper.checkReplyWriter(replyIdx, member_id) == 0) {
            throw new NoWriterForProductOrCommentException("해당 글이나 댓글의 작성자가 아닙니다.");
        }
    }
}
