package com.cucumber.market.exception.advice;

import com.cucumber.market.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
