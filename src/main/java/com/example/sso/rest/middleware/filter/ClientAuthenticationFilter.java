package com.example.sso.rest.middleware.filter;

import com.example.sso.core.application.service.ClientService;
import com.example.sso.core.common.exception.AppException;
import com.example.sso.core.common.util.Base64;
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

import java.util.List;
import java.util.Optional;

@Component
@Order(3)
@RequiredArgsConstructor
public class ClientAuthenticationFilter extends OncePerRequestFilter {

    private final ClientService clientService;

    private final PathPatternParser patternParser = new PathPatternParser();

    public static final String ERROR_CLIENT_AUTH_FILTER_UNAUTHORIZED = "CLIENT_AUTH_FILTER.UNAUTHORIZED";

    @Override
    @SneakyThrows
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) {
        var authHeader = Optional.ofNullable(request.getHeader("Authorization")).orElse("");

        String basicToken;
        if (authHeader.startsWith("Basic") && authHeader.split("\\s+").length > 1) {
            basicToken = Base64.decode(authHeader.substring(7));
        } else {
            throw new AppException(ERROR_CLIENT_AUTH_FILTER_UNAUTHORIZED);
        }

        var credentials = basicToken.split(":");
        if (credentials.length < 2) {
            throw new AppException(ERROR_CLIENT_AUTH_FILTER_UNAUTHORIZED);
        }

        var client = clientService.authenticate(credentials[0], credentials[1]);
        request.setAttribute("client", client);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        var patterns = List.of(
                patternParser.parse("POST /api/v1/token/revoke")
        );

        return patterns.parallelStream()
                .noneMatch(pattern -> pattern.matches(PathContainer.parsePath("POST " + request.getRequestURI())));
    }
}
