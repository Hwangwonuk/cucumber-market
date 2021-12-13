package com.cucumber.market.service.impl;

import com.cucumber.market.dto.comment.CommentResponse;
import com.cucumber.market.dto.comment.ContentResponse;
import com.cucumber.market.exception.AlreadyDeletedReplyException;
import com.cucumber.market.exception.NoWriterForReplyException;
import com.cucumber.market.exception.NotExistReplyException;
import com.cucumber.market.exception.ProductNotIncludeReplyException;
import com.cucumber.market.mapper.ReplyMapper;
import com.cucumber.market.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyMapper replyMapper;

    /**
     * 대댓글 등록 메소드
     *
     * @param commentIdx 댓글번호
     * @param content 등록할 내용
     * @param member_id 로그인한 회원의 아이디
     * @return 등록한 대댓글 번호
     */
    @Override
    public CommentResponse registerReply(int commentIdx, String content, String member_id) {
        replyMapper.registerReply(commentIdx, content, member_id);

        return CommentResponse.builder()
                .commentIdx(commentIdx)
                .build();
    }

    /**
     * 대댓글 존재여부 검사 메소드
     *
     * @param replyIdx 대댓글번호
     */
    @Override
    @Transactional(readOnly = true)
    public void checkExistReply(int replyIdx) {
        if (replyMapper.checkExistReply(replyIdx) == 0) {
            throw new NotExistReplyException("해당 대댓글은 존재하지 않습니다.");
        }
    }

    /**
     * 댓글에 속하는 대댓글인지 확인하는 메소드
     *
     * @param commentIdx 댓글번호
     * @param replyIdx 대댓글번호
     */
    @Override
    @Transactional(readOnly = true)
    public void checkCommentIncludeReply(int commentIdx, int replyIdx) {
        if (replyMapper.checkCommentIncludeReply(commentIdx, replyIdx) == 0) {
            throw new ProductNotIncludeReplyException("해당 댓글에 속하지 않는 대댓글입니다.");
        }
    }

    /**
     * 대댓글 조회 메소드
     *
     * @param replyIdx 대댓글 번호
     * @return 대댓글 작성자, 아이디, 내용, 수정시간
     */
    @Override
    public ContentResponse getReply(int replyIdx) {
        return replyMapper.getReply(replyIdx);
    }

    /**
     * 대댓글 삭제여부 확인 메소드
     *
     * @param replyIdx 대댓글 번호
     */
    @Override
    @Transactional(readOnly = true)
    public void checkNotDeleteReply(int replyIdx) {
        if (replyMapper.checkNotDeleteReply(replyIdx) == 1) {
            throw new AlreadyDeletedReplyException("해당 대댓글은 이미 삭제되었습니다.");
        }
    }

    /**
     * 대댓글 작성자인지 확인하는 메소드
     *
     * @param replyIdx 대댓글 번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    @Transactional(readOnly = true)
    public void checkReplyWriter(int replyIdx, String member_id) {
        if (replyMapper.checkReplyWriter(replyIdx, member_id) == 0) {
            throw new NoWriterForReplyException("해당 대댓글의 작성자가 아닙니다.");
        }
    }

    /**
     * 대댓글 수정 메소드
     *
     * @param replyIdx 대댓글 번호
     * @param content 수정할 내용
     */
    @Override
    public void updateReply(int replyIdx, String content) {
        replyMapper.updateReply(replyIdx, content);
    }

    /**
     * 대댓글 삭제 메소드
     *
     * @param replyIdx 대댓글 번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void deleteReply(int replyIdx, String member_id) {
        replyMapper.deleteReply(replyIdx, member_id);
    }
}
