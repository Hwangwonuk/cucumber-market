package com.cucumber.market.service;

import com.cucumber.market.dto.product.ContentResponse;

public interface CommentService {

    void registerComment(int productIdx, String content, String member_id);

    ContentResponse getComment(int commentIdx);

    void checkExistComment(int commentIdx);

    void checkProductIncludeComment(int productIdx, int commentIdx);

    void checkNotDeleteComment(int commentIdx);

    void checkCommentWriter(int commentIdx, String member_id);

    void updateComment(int commentIdx, String content);

    void deleteComment(int commentIdx, String member_id);

}
