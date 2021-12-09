package com.cucumber.market.service;

import com.cucumber.market.dto.product.ProductUpdateRequest;
import com.cucumber.market.dto.product.ProductUploadRequest;
import com.cucumber.market.dto.product.ProductUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    /*ProductUploadResponse uploadProductV2(ProductUploadRequest productUploadRequest,
                                          List<MultipartFile> multipartFiles,
                                          String member_id) throws IOException;*/

    ProductUploadResponse uploadProduct(ProductUploadRequest productUploadRequest, String member_id);

    ProductUploadResponse uploadProductImages(List<MultipartFile> images, String member_id) throws IOException;

    void checkProductWriter(int productIdx, String member_id);

    ProductUploadResponse updateProduct(int productIdx, ProductUpdateRequest productUpdateRequest, String member_id);

    void checkNotSoldOutProduct(int productIdx);

    void soldOutProduct(int productIdx, String member_id);

    void checkNotDeleteProduct(int productIdx);

    void deleteProduct(int productIdx, String member_id);

    void checkProductOrCommentWriter(int productIdx, int commentIdx, String member_id);

    void checkProductOrReplyWriter(int productIdx, int replyIdx, String member_id);
}
