package com.cucumber.market.mapper;

import com.cucumber.market.dto.comment.ContentResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {
    void registerComment(@Param("productIdx") int productIdx, @Param("content") String content, @Param("member_id") String member_id);

    ContentResponse getComment(int commentIdx);

    int checkExistComment(int commentIdx);

    int checkProductIncludeComment(@Param("productIdx") int productIdx, @Param("commentIdx") int commentIdx);

    int checkNotDeleteComment(int commentIdx);

    int checkCommentWriter(@Param("commentIdx") int commentIdx, @Param("member_id") String member_id);

    void updateComment(@Param("commentIdx") int commentIdx, @Param("content") String content);

    void deleteComment(@Param("commentIdx") int commentIdx, @Param("member_id") String member_id);
}
