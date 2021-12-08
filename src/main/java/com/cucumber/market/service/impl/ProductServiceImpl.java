package com.cucumber.market.service.impl;

import com.cucumber.market.dto.product.FileUploadForm;
import com.cucumber.market.dto.product.ProductUploadForm;
import com.cucumber.market.dto.product.ProductUploadResponse;
import com.cucumber.market.file.FileStore;
import com.cucumber.market.mapper.FileMapper;
import com.cucumber.market.mapper.ProductMapper;
import com.cucumber.market.mapper.ReplyMapper;
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


}
