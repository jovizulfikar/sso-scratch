package com.example.oauth2rest.controller;

import org.jose4j.jwt.consumer.InvalidJwtException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.oauth2core.common.exception.AppException;
import com.example.oauth2core.common.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleException(Exception ex) {
        log.error("Error: {}", ex.getMessage(), ex);
        ProblemDetail detail = ExceptionTranslator.defaultProblemDetail();
        return ResponseEntity.of(detail).build();
    }

    @ExceptionHandler(value = { AppException.class })
    public ResponseEntity<ProblemDetail> handleAppException(AppException ex) {
        log.error("Error: {}", ex.getMessage(), ex);
        ProblemDetail detail = ExceptionTranslator.toProblemDetail(ex);
        return ResponseEntity.of(detail).build();
    }
    
    @ExceptionHandler(value = { ValidationException.class })
    public ResponseEntity<ProblemDetail> handleValidationException(ValidationException ex) {
        log.error("Error: {}", ex.getMessage(), ex);
        ProblemDetail detail = ExceptionTranslator.toProblemDetail(ex);
        return ResponseEntity.of(detail).build();
    }

    @ExceptionHandler(value = { InvalidJwtException.class })
    public ResponseEntity<ProblemDetail> handleInvalidJwtException(InvalidJwtException ex) {
        log.error("Error: {}", ex.getMessage(), ex);
        ProblemDetail detail = ExceptionTranslator.toProblemDetail(ex);
        return ResponseEntity.of(detail).build();
    }

}
