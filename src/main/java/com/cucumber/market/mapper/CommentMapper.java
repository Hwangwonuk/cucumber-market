package com.cucumber.market.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {
    void registerComment(@Param("productIdx") int productIdx, @Param("content") String content, @Param("member_id") String member_id);

    int checkNotDeleteComment(int commentIdx);

    int checkCommentWriter(@Param("commentIdx") int commentIdx, @Param("member_id") String member_id);

    void updateComment(@Param("commentIdx") int commentIdx, @Param("content") String content);

    void deleteComment(@Param("commentIdx") int commentIdx, @Param("member_id") String member_id);
}
