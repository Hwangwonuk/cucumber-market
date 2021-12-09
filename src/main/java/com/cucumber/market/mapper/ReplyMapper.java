package com.cucumber.market.mapper;

import com.cucumber.market.dto.product.ContentResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReplyMapper {
    void registerReply(@Param("commentIdx") int commentIdx, @Param("content") String content, @Param("member_id") String member_id);

    int checkExistReply(int replyIdx);

    int checkCommentIncludeReply(@Param("commentIdx") int commentIdx, @Param("replyIdx") int replyIdx);

    ContentResponse getReply(int replyIdx);

    int checkNotDeleteReply(int replyIdx);

    int checkReplyWriter(@Param("replyIdx") int replyIdx, @Param("member_id") String member_id);

    void updateReply(@Param("replyIdx") int replyIdx, @Param("member_id") String content);

    void deleteReply(@Param("replyIdx") int replyIdx, @Param("member_id") String member_id);
}
