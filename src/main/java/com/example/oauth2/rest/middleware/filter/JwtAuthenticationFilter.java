package com.example.oauth2.rest.middleware.filter;

import com.example.oauth2.core.application.service.KeyManager;
import com.example.oauth2.core.common.exception.AppException;
import com.example.oauth2.core.port.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.PathContainer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@Order(2)
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final KeyManager keyManager;
    private final PathPatternParser patternParser = new PathPatternParser();
    private final JwtService jwtService;

    public static final String ERROR_JWT_AUTH_FILTER_UNAUTHORIZED = "JWT_AUTH_FILTER.UNAUTHORIZED";


    @Override
    @SneakyThrows
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var authHeader = Optional.ofNullable(request.getHeader("Authorization")).orElse("");

        String bearerToken;
        if (authHeader.startsWith("Bearer") && authHeader.split("\\s+").length > 1) {
            bearerToken = authHeader.substring(7);
        } else {
            throw new AppException(ERROR_JWT_AUTH_FILTER_UNAUTHORIZED);
        }
        
        var claims = jwtService.verify(bearerToken, keyManager.getRsaPublicKey());
        request.setAttribute("jwtClaims", claims);

        filterChain.doFilter(request, response);
    }
    
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        var patterns = Arrays.asList(
            patternParser.parse("POST /api/v1/users")
        );

        return patterns.parallelStream()
                .noneMatch(pattern -> pattern.matches(PathContainer.parsePath("POST " + request.getRequestURI())));
	}
}
