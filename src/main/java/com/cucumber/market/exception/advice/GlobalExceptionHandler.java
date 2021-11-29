package com.cucumber.market.exception.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // PK 중복과 관련된 Exception
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> dataIntegrityViolationException(final DataIntegrityViolationException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
