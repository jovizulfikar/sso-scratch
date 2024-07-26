package com.example.oauth2.rest.middleware.filter;

import com.example.oauth2.core.common.exception.AppException;
import com.example.oauth2.rest.controller.RestExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final RestExceptionHandler restExceptionHandler;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ServletException e) {
            var cause = e.getCause();

            ResponseEntity<ProblemDetail> responseEntity;
            if (cause instanceof AppException) {
                responseEntity = restExceptionHandler.handleAppException((AppException) cause);
            } else if (cause instanceof InvalidJwtException) {
                responseEntity = restExceptionHandler.handleInvalidJwtException((InvalidJwtException) cause);
            } else {
                responseEntity = restExceptionHandler.handleException((Exception) cause);
            }

            writeErroResponse(response, responseEntity);
        } catch (Exception e) {
            var responseEntity = restExceptionHandler.handleException(e);
            writeErroResponse(response, responseEntity);
        }
    }
    
    @SneakyThrows
    private void writeErroResponse(HttpServletResponse response, ResponseEntity<?> responseEntity) {
        response.setStatus(responseEntity.getStatusCode().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));
        response.getWriter().flush();
    }
}
