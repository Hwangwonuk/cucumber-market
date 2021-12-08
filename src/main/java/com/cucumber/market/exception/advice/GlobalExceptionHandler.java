package com.cucumber.market.exception.advice;

import com.cucumber.market.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/*
 @ExceptionHandler 를 사용해서 예외를 깔끔하게 처리할 수 있지만, 정상 코드와 예외 처리 코드가 하나의 컨트롤러에 섞여 있게된다.
 @ControllerAdvice 또는 @RestControllerAdvice 를 사용하면 둘을 분리할 수 있다.

 @ControllerAdvice
 @ControllerAdvice 는 대상으로 지정한 여러 컨트롤러에 @ExceptionHandler , @InitBinder 기능을 부여해주는 역할을 한다.
 @ControllerAdvice 에 대상을 지정하지 않으면 모든 컨트롤러에 적용된다. (글로벌 적용)

 @RestControllerAdvice 는 @ControllerAdvice 와 같고, @ResponseBody 가 추가되어 있다.
 @Controller , @RestController 의 차이와 같다
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // PK 중복 Exception
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> dataIntegrityViolationException(final DataIntegrityViolationException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 정보를 찾지 못했을때 Exception
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ExceptionResponse> memberNotFoundException(final MemberNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 로그인 실패시 Exception
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ExceptionResponse> passwordMismatchException(final PasswordMismatchException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // 로그인 되지 않은 상태에서 로그인이 필요한 API를 호출할때
    @ExceptionHandler(NotYetSignInException.class)
    public ResponseEntity<ExceptionResponse> notYetSignInException(final NotYetSignInException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // ADMIN 권한이 있어야하는데 없을 때
    @ExceptionHandler(NoAdminAuthorityException.class)
    public ResponseEntity<ExceptionResponse> noAdminAuthorityException(final NoAdminAuthorityException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // 입력한 분류명(Category)이 없을 때
    @ExceptionHandler(CategoryNameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> categoryNameNotFoundException(final CategoryNameNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 이미 로그인된 상태에서 로그인을 시도할 때
    @ExceptionHandler(AlreadySignInException.class)
    public ResponseEntity<ExceptionResponse> alreadySignInException(final AlreadySignInException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 이미 로그아웃된 상태 혹은 로그인되지 않은 상태에서 로그아웃을 시도할 때
    @ExceptionHandler(AlreadySignOutException.class)
    public ResponseEntity<ExceptionResponse> alreadySignOutException(final AlreadySignOutException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 이미 탈퇴한 상태의 회원의 정보로 로그인을 시도할 때
    @ExceptionHandler(AlreadyInActiveMemberException.class)
    public ResponseEntity<ExceptionResponse> alreadyInActiveMemberException(final AlreadyInActiveMemberException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 조회 데이터의 페이지 번호가 자연수가 아닌 경우
    @ExceptionHandler(PageNoPositiveException.class)
    public ResponseEntity<ExceptionResponse> pageNegativeNotAvailableException(final PageNoPositiveException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 상품에 찜이 돼있는데 찜을 등록하려는 경우
    @ExceptionHandler(AlreadyHopeRegisteredException.class)
    public ResponseEntity<ExceptionResponse> alreadyHopeRegisteredException(final AlreadyHopeRegisteredException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 상품에 찜이 안돼있는데 찜을 해제하려는 경우
    @ExceptionHandler(NotYetHopeRegisteredException.class)
    public ResponseEntity<ExceptionResponse> notYetHopeRegisteredException(final NotYetHopeRegisteredException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 이미 삭제된 댓글인데 삭제하려는 경우
    @ExceptionHandler(AlreadyDeletedCommentException.class)
    public ResponseEntity<ExceptionResponse> alreadyDeletedCommentException(final AlreadyDeletedCommentException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 해당 댓글의 작성자가 아닌데 댓글을 수정/삭제 하려는 경우
    @ExceptionHandler(NoWriterForCommentException.class)
    public ResponseEntity<ExceptionResponse> noWriterForCommentException(final NoWriterForCommentException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 해당 판매글이나 댓글의 작성자가 아닌데 대댓글을 등록하려는 경우
    @ExceptionHandler(NoWriterForProductOrCommentException.class)
    public ResponseEntity<ExceptionResponse> noWriterForProductOrCommentException(final NoWriterForProductOrCommentException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 이미 삭제된 대댓글인데 삭제하려는 경우
    @ExceptionHandler(AlreadyDeletedReplyException.class)
    public ResponseEntity<ExceptionResponse> alreadyDeletedReplyException(final AlreadyDeletedReplyException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 이미 관리자인데 관리자로 등록할 경우
    @ExceptionHandler(AlreadyAdminException.class)
    public ResponseEntity<ExceptionResponse> alreadyAdminException(final AlreadyAdminException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
