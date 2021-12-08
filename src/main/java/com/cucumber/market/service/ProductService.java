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

}
