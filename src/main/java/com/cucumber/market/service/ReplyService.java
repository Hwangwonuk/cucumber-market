package com.cucumber.market.service;

public interface ReplyService {

    void registerReply(int commentIdx, String content, String member_id);

    void checkProductOrCommentWriter(int productIdx, int commentIdx, String member_id);

    void checkNotDeleteReply(int replyIdx);

    void checkReplyWriter(int replyIdx, String member_id);

    void updateReply(int replyIdx, String content);

    void deleteReply(int replyIdx, String member_id);
}
