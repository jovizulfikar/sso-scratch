package com.example.sso.rest.middleware.interceptor;

import com.example.sso.core.common.exception.AppException;
import com.example.sso.core.domain.oauth2.JwtClaims;
import com.example.sso.rest.annotation.ApiScope;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Objects;

@Component
@Order(1)
public class ApiScopeInterceptor implements HandlerInterceptor {

    public static final String ERROR_INVALID_SCOPE = "API_SCOPE_INTERCEPTOR.INVALID_SCOPE";

    @Override
    public boolean preHandle(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull Object handler
    ) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        var apiScopeAnnotation = handlerMethod.getMethod().getAnnotation(ApiScope.class);

        if (Objects.isNull(apiScopeAnnotation)) {
            return true;
        }


        var apiScopes = apiScopeAnnotation.value();
        if (apiScopes.length < 1) {
            return true;
        }

        var scopeClaim = (JwtClaims) request.getAttribute("jwtClaims");
        boolean isEligible = scopeClaim.getScope().stream()
                .anyMatch(scope -> Arrays.asList(apiScopes).contains(scope));

        if (!isEligible) {
            throw AppException.build(ERROR_INVALID_SCOPE);
        }

        return true;
    }
}
