package com.cucumber.market.service;

public interface CommentService {

    void registerComment(int productIdx, String content, String member_id);

    void checkNotDeleteComment(int commentIdx);

    void checkCommentWriter(int commentIdx, String member_id);

    void updateComment(int commentIdx, String content);

    void deleteComment(int commentIdx, String member_id);

}
