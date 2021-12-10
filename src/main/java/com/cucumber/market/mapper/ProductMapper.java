package com.cucumber.market.mapper;

import com.cucumber.market.dto.product.FindDetailProductResponse;
import com.cucumber.market.dto.product.FindProductResponse;
import com.cucumber.market.dto.product.ProductUpdateRequest;
import com.cucumber.market.dto.product.ProductUploadRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    void uploadProduct(ProductUploadRequest productUploadRequest);

    int getMyLatestProduct(String member_id);

    void updateThumbnailIdx(@Param("productIdx") int productIdx, @Param("thumbnailIdx") int thumbnailIdx);

    int checkProductWriter(@Param("productIdx") int productIdx, @Param("member_id") String member_id);

    void updateProduct(ProductUpdateRequest productUpdateRequest);

    List<FindProductResponse> findProductByPagination(@Param("contentNum") int contentNum,
                                                      @Param("offset") int offset,
                                                      @Param("smallCategoryName") String smallCategoryName,
                                                      @Param("findTitle") String findTitle);

    int checkExistProduct(int productIdx);

    FindDetailProductResponse findDetailProduct(int productIdx);

    int checkNotSoldOutProduct(int productIdx);

    void soldOutProduct(@Param("productIdx") int productIdx, @Param("member_id") String member_id);

    int checkNotDeleteProduct(int productIdx);

    void deleteProduct(@Param("productIdx") int productIdx, @Param("member_id") String member_id);

}
