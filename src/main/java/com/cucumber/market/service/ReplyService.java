package com.cucumber.market.service;

import com.cucumber.market.dto.comment.CommentResponse;
import com.cucumber.market.dto.comment.ContentResponse;

public interface ReplyService {

    CommentResponse registerReply(int commentIdx, String content, String member_id);

    void checkExistReply(int replyIdx);

    void checkCommentIncludeReply(int commentIdx, int replyIdx);

    ContentResponse getReply(int replyIdx);

    void checkNotDeleteReply(int replyIdx);

    void checkReplyWriter(int replyIdx, String member_id);

    void updateReply(int replyIdx, String content);

    void deleteReply(int replyIdx, String member_id);
}
