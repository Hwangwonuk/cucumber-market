package com.cucumber.market.mapper;

import com.cucumber.market.dto.product.ProductUploadForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {
    void uploadProduct(ProductUploadForm productUploadForm);

    int getMyLatestProduct(String member_id);

    void updateThumbnailIdx(@Param("productIdx") int productIdx, @Param("thumbnailIdx") int thumbnailIdx);

    int checkDuplicateHope(@Param("productIdx") int productIdx, @Param("member_id") String member_id);

    void hope(@Param("productIdx") int productIdx, @Param("member_id") String member_id);

    void cancelHope(@Param("productIdx") int productIdx, @Param("member_id") String member_id);

    void registerComment(@Param("productIdx") int productIdx, @Param("content") String content, @Param("member_id") String member_id);

    int checkNotDeleteComment(int commentIdx);

    int checkCommentWriter(@Param("commentIdx") int commentIdx, @Param("member_id") String member_id);

    void updateComment(@Param("commentIdx") int commentIdx, @Param("content") String content);

    void deleteComment(@Param("commentIdx") int commentIdx,@Param("member_id") String member_id);
}
