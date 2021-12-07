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

    void checkDuplicateHope(int productIdx, String member_id);

    void hope(int productIdx, String member_id);

    void checkAlreadyHope(int productIdx, String member_id);

    void cancelHope(int productIdx, String member_id);

    void registerComment(int productIdx, String content, String member_id);

    void checkNotDeleteComment(int commentIdx);

    void checkCommentWriter(int commentIdx, String member_id);

    void updateComment(int commentIdx, String content);

    void deleteComment(int commentIdx, String member_id);

}
