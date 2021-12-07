package com.cucumber.market.service.impl;

import com.cucumber.market.dto.product.FileUploadForm;
import com.cucumber.market.dto.product.ProductUploadForm;
import com.cucumber.market.dto.product.ProductUploadResponse;
import com.cucumber.market.exception.*;
import com.cucumber.market.file.FileStore;
import com.cucumber.market.mapper.FileMapper;
import com.cucumber.market.mapper.ProductMapper;
import com.cucumber.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

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
     * 글 등록 메소드(이미지 파일 포함)
     * @param productUploadForm 글 등록시 필요한 데이터
     * @param multipartFiles 업로드된 파일들
     * @param member_id 현재 접속중인 회원의 아이디
     */
    @Override
    public ProductUploadResponse uploadProduct(ProductUploadForm productUploadForm,
                                               List<MultipartFile> multipartFiles,
                                               String member_id) throws IOException {
        productMapper.uploadProduct(productUploadForm); // 글등록(파일제외)

        int productIdx = productMapper.getMyLatestProduct(member_id); // 방금 등록된 글 idx

        List<FileUploadForm> storeImageFiles = fileStore.storeFiles(productIdx, multipartFiles); // 파일명 변환
        fileMapper.save(storeImageFiles); // 파일 테이블에 글 idx, uploadfilename, storefilename 저장

        int thumbnailIdx = fileMapper.getMyThumbnailIdx(productIdx); // 해당 글 idx로 저장된 파일중 file_idx가 가장작은(먼저등록된) 이미지의 file_idx
        productMapper.updateThumbnailIdx(productIdx, thumbnailIdx); // 썸네일 idx로 업데이트

        return ProductUploadResponse.builder()
                .redirectUrl(productUrl)
                .build();
    }

    /**
     * 상품찜 중복검사 메소드
     * @param productIdx 상품번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void checkDuplicateHope(int productIdx, String member_id) {
        if (productMapper.checkDuplicateHope(productIdx, member_id) == 1) {
            throw new AlreadyHopeRegisteredException("이미 해당 상품을 찜하셨습니다.");
        }
    }

    /**
     * 상품찜 메소드
     * @param productIdx 상품번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void registerHope(int productIdx, String member_id) {
        productMapper.registerHope(productIdx, member_id);
    }

    /**
     * 상품찜 취소전 본인확인 메소드
     * @param productIdx 상품번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void checkAlreadyHope(int productIdx, String member_id) {
        if (productMapper.checkDuplicateHope(productIdx, member_id) == 0) {
            throw new NotYetHopeRegisteredException("해당 상품을 찜한적이 없습니다.");
        }
    }

    /**
     * 상품찜 취소 메소드
     * @param productIdx 상품번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void cancelHope(int productIdx, String member_id) {
        productMapper.cancelHope(productIdx, member_id);
    }

    /**
     * 댓글등록 메소드
     * @param productIdx 상품번호
     * @param content 댓글내용
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void registerComment(int productIdx, String content, String member_id) {
        productMapper.registerComment(productIdx, content, member_id);
    }

    /**
     * 댓글삭제 여부검사 메소드
     * @param commentIdx
     */
    @Override
    public void checkNotDeleteComment(int commentIdx) {
        if (productMapper.checkNotDeleteComment(commentIdx) == 1) {
            throw new AlreadyDeletedCommentException("해당 댓글은 이미 삭제되었습니다.");
        }
    }

    /**
     * 댓글 작성자 확인 메소드
     * @param commentIdx 댓글번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void checkCommentWriter(int commentIdx, String member_id) {
        if (productMapper.checkCommentWriter(commentIdx, member_id) == 0) {
            throw new NoWriterForCommentException("해당 댓글의 작성자가 아닙니다.");
        }
    }

    /**
     * 댓글수정 메소드
     * @param commentIdx 댓글번호
     * @param content 수정할 내용
     */
    @Override
    public void updateComment(int commentIdx, String content) {
        productMapper.updateComment(commentIdx, content);
    }

    /**
     * 댓글삭제 메소드
     * @param commentIdx 댓글번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void deleteComment(int commentIdx, String member_id) {
        productMapper.deleteComment(commentIdx, member_id);
    }

    /**
     * 대댓글 등록 메소드
     * @param commentIdx 댓글번호
     * @param content 등록할 내용
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void registerReply(int commentIdx, String content, String member_id) {
        productMapper.registerReply(commentIdx, content, member_id);
    }

    /**
     * 상품글 작성자 or 댓글 작성자 검사 메소드
     * @param productIdx
     * @param commentIdx
     * @param member_id
     */
    @Override
    public void checkProductOrCommentWriter(int productIdx, int commentIdx, String member_id) {
        if (productMapper.checkProductWriter(productIdx, member_id) == 0 || productMapper.checkCommentWriter(commentIdx, member_id) == 0) {
            throw new NoWriterForProductOrCommentException("해당 글이나 댓글의 작성자가 아닙니다.");
        }
    }

    /**
     * 삭제된 대댓글인지 확인하는 메소드
     * @param replyIdx 대댓글 번호
     */
    @Override
    public void checkNotDeleteReply(int replyIdx) {
        if (productMapper.checkNotDeleteReply(replyIdx) == 1) {
            throw new AlreadyDeletedReplyException("해당 대댓글은 이미 삭제되었습니다.");
        }
    }

    /**
     * 대댓글 작성자인지 확인하는 메소드
     * @param replyIdx 대댓글 번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void checkReplyWriter(int replyIdx, String member_id) {
        if (productMapper.checkReplyWriter(replyIdx, member_id) == 0) {
            throw new NoWriterForReplyException("해당 대댓글의 작성자가 아닙니다.");
        }
    }

    /**
     * 대댓글 수정 메소드
     * @param replyIdx 대댓글 번호
     * @param content 수정할 내용
     */
    @Override
    public void updateReply(int replyIdx, String content) {
        productMapper.updateReply(replyIdx, content);
    }

    /**
     * 대댓글 삭제 메소드
     * @param replyIdx 대댓글 번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void deleteReply(int replyIdx, String member_id) {
        productMapper.deleteReply(replyIdx, member_id);
    }
}
