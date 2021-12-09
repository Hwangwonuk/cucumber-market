package com.cucumber.market.service.impl;

import com.cucumber.market.dto.product.ContentResponse;
import com.cucumber.market.exception.AlreadyDeletedCommentException;
import com.cucumber.market.exception.NoWriterForCommentException;
import com.cucumber.market.exception.NotExistCommentException;
import com.cucumber.market.exception.ProductNotIncludeCommentException;
import com.cucumber.market.mapper.CommentMapper;
import com.cucumber.market.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    /**
     * 댓글등록 메소드
     * @param productIdx 상품번호
     * @param content 댓글내용
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void registerComment(int productIdx, String content, String member_id) {
        commentMapper.registerComment(productIdx, content, member_id);
    }

    /**
     * 댓글조회 메소드
     * @param commentIdx 댓글번호
     * @return 댓글 작성자 아이디, 내용
     */
    @Override
    public ContentResponse getComment(int commentIdx) {
        return commentMapper.getComment(commentIdx);
    }

    /**
     * 댓글 존재여부 확인 메소드
     * @param commentIdx 댓글번호
     */
    @Override
    public void checkExistComment(int commentIdx) {
        if (commentMapper.checkExistComment(commentIdx) == 0) {
            throw new NotExistCommentException("존재하지 않는 댓글입니다.");
        }
    }

    /**
     * 판매글에 속하는 댓글인지 확인하는 메소드
     * @param productIdx 판매글 번호
     * @param commentIdx 댓글번호
     */
    @Override
    public void checkProductIncludeComment(int productIdx, int commentIdx) {
        if (commentMapper.checkProductIncludeComment(productIdx, commentIdx) == 0) {
            throw new ProductNotIncludeCommentException("해당 판매글에 속하지 않는 댓글입니다.");
        }
    }

    /**
     * 댓글삭제 여부검사 메소드
     * @param commentIdx 댓글번호
     */
    @Override
    public void checkNotDeleteComment(int commentIdx) {
        if (commentMapper.checkNotDeleteComment(commentIdx) == 1) {
            throw new AlreadyDeletedCommentException("해당 댓글은 이미 삭제되었습니다.");
        }
    }

    /**
     * 댓글 작성자 확인 메소드 (작성자가 아니라면 예외 발생)
     * @param commentIdx 댓글번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void checkCommentWriter(int commentIdx, String member_id) {
        if (commentMapper.checkCommentWriter(commentIdx, member_id) == 0) {
            throw new NoWriterForCommentException("해당 댓글의 작성자가 아닙니다.");
        }
    }

    /**
     * 댓글 작성자 확인 메소드 (시비 여부 확인)
     * @param commentIdx 댓글번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public Boolean isCommentWriter(int commentIdx, String member_id) {
        if (commentMapper.checkCommentWriter(commentIdx, member_id) == 0) {
            return false;
        }
        return true;
    }

    /**
     * 댓글수정 메소드
     * @param commentIdx 댓글번호
     * @param content 수정할 내용
     */
    @Override
    public void updateComment(int commentIdx, String content) {
        commentMapper.updateComment(commentIdx, content);
    }

    /**
     * 댓글삭제 메소드
     * @param commentIdx 댓글번호
     * @param member_id 로그인한 회원의 아이디
     */
    @Override
    public void deleteComment(int commentIdx, String member_id) {
        commentMapper.deleteComment(commentIdx, member_id);
    }

}
