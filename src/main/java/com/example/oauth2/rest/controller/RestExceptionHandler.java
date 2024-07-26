package com.example.oauth2.rest.controller;

import com.example.oauth2.core.common.exception.AppException;
import com.example.oauth2.core.common.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.URI;
import java.util.Optional;

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

    @ExceptionHandler(value = { NoHandlerFoundException.class })
    public ResponseEntity<ProblemDetail> handleRouteNotFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
        log.error("Error: {}", ex.getMessage(), ex);
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Route " + request.getMethod() + " " +  request.getRequestURI() + " not found.");
        var title = Optional.ofNullable(detail.getTitle()).orElse("");
        detail.setType(URI.create("/errors/" + title.toLowerCase().replace(" ", "-")));
        return ResponseEntity.of(detail).build();
    }
}
