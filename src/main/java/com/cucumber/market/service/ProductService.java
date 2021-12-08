package com.cucumber.market.service;

import com.cucumber.market.dto.product.ProductUploadForm;
import com.cucumber.market.dto.product.ProductUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    /*ProductUploadResponse uploadProductV2(ProductUploadRequest productUploadRequest,
                                          List<MultipartFile> multipartFiles,
                                          String member_id) throws IOException;*/

    ProductUploadResponse uploadProduct(ProductUploadForm productUploadForm,
                                        List<MultipartFile> multipartFiles,
                                        String member_id) throws IOException;

    void registerHope(int productIdx, String member_id);

    void checkDuplicateHope(int productIdx, String member_id);

    void checkAlreadyHope(int productIdx, String member_id);

    void cancelHope(int productIdx, String member_id);

    void registerComment(int productIdx, String content, String member_id);

    void checkNotDeleteComment(int commentIdx);

    void checkCommentWriter(int commentIdx, String member_id);

    void updateComment(int commentIdx, String content);

    void deleteComment(int commentIdx, String member_id);

    void registerReply(int commentIdx, String content, String member_id);

    void checkProductOrCommentWriter(int productIdx, int commentIdx, String member_id);

    void checkNotDeleteReply(int replyIdx);

    void checkReplyWriter(int replyIdx, String member_id);

    void updateReply(int replyIdx, String content);

    void deleteReply(int replyIdx, String member_id);
}
