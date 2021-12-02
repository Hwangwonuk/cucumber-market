package com.cucumber.market.exception.advice;

import com.cucumber.market.exception.CategoryNameNotFoundException;
import com.cucumber.market.exception.MemberNotFoundException;
import com.cucumber.market.exception.PasswordMismatchException;
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
}
