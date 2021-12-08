package com.cucumber.market.service.impl;

import com.cucumber.market.exception.AlreadyDeletedReplyException;
import com.cucumber.market.exception.NoWriterForProductOrCommentException;
import com.cucumber.market.exception.NoWriterForReplyException;
import com.cucumber.market.mapper.CommentMapper;
import com.cucumber.market.mapper.ProductMapper;
import com.cucumber.market.mapper.ReplyMapper;
import com.cucumber.market.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyMapper replyMapper;

    private final ProductMapper productMapper;

    private final CommentMapper commentMapper;

    /**
     * 대댓글 등록 메소드
     * @param commentIdx 댓글번호
     * @param content 등록할 내용
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void registerReply(int commentIdx, String content, String member_id) {
        replyMapper.registerReply(commentIdx, content, member_id);
    }

    /**
     * 상품글 작성자 or 댓글 작성자 검사 메소드
     * @param productIdx 글번호
     * @param commentIdx 댓글번호
     * @param member_id 대댓글 작성을 시도하는 회원의 아이디
     */
    @Override
    public void checkProductOrCommentWriter(int productIdx, int commentIdx, String member_id) {
        if (productMapper.checkProductWriter(productIdx, member_id) == 0 || commentMapper.checkCommentWriter(commentIdx, member_id) == 0) {
            throw new NoWriterForProductOrCommentException("해당 글이나 댓글의 작성자가 아닙니다.");
        }
    }

    /**
     * 삭제된 대댓글인지 확인하는 메소드
     * @param replyIdx 대댓글 번호
     */
    @Override
    public void checkNotDeleteReply(int replyIdx) {
        if (replyMapper.checkNotDeleteReply(replyIdx) == 1) {
            throw new AlreadyDeletedReplyException("해당 대댓글은 이미 삭제되었습니다.");
        }
    }

    /**
     * 대댓글 작성자인지 확인하는 메소드
     * @param replyIdx 대댓글 번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void checkReplyWriter(int replyIdx, String member_id) {
        if (replyMapper.checkReplyWriter(replyIdx, member_id) == 0) {
            throw new NoWriterForReplyException("해당 대댓글의 작성자가 아닙니다.");
        }
    }

    /**
     * 대댓글 수정 메소드
     * @param replyIdx 대댓글 번호
     * @param content 수정할 내용
     */
    @Override
    public void updateReply(int replyIdx, String content) {
        replyMapper.updateReply(replyIdx, content);
    }

    /**
     * 대댓글 삭제 메소드
     * @param replyIdx 대댓글 번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void deleteReply(int replyIdx, String member_id) {
        replyMapper.deleteReply(replyIdx, member_id);
    }
}
