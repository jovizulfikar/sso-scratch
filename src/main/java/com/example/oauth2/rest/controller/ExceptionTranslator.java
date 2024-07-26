package com.example.oauth2.rest.controller;

import com.example.oauth2.core.application.usecase.RegisterClientUseCase;
import com.example.oauth2.core.application.usecase.RegisterUserUseCase;
import com.example.oauth2.core.application.usecase.authentication.provider.AuthenticationProvider;
import com.example.oauth2.core.application.usecase.authentication.provider.AuthenticationProviderFactory;
import com.example.oauth2.core.common.exception.AppException;
import com.example.oauth2.core.common.exception.ValidationException;
import com.example.oauth2.rest.middleware.filter.JwtAuthenticationFilter;
import com.example.oauth2.rest.middleware.interceptor.ApiScopeInterceptor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jose4j.jwt.consumer.ErrorCodeValidator;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionTranslator {

    public static ProblemDetail defaultProblemDetail() {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong.");
        var title = Optional.ofNullable(problemDetail.getTitle()).orElse("");
        problemDetail.setType(URI.create("/errors/" + title.toLowerCase().replace(" ", "-")));
        return problemDetail;
    }
    
    public static ProblemDetail toProblemDetail(InvalidJwtException e) { 
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "JWT rejected due to invalid claims or other invalid content.");
        var title = Optional.ofNullable(problemDetail.getTitle()).orElse("");
        var jwtErrors = e.getErrorDetails().stream().map(ErrorCodeValidator.Error::getErrorMessage).collect(Collectors.toSet());

        Map<String, Set<String>> errors = new HashMap<>();
        errors.put("jwt",jwtErrors);

        problemDetail.setType(URI.create("/errors/" + title.toLowerCase().replace(" ", "-")));
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    public static ProblemDetail toProblemDetail(AppException e) {
        ProblemDetail pd = switch (e.getType()) {
            case RegisterUserUseCase.ERROR_USERNAME_ALREADY_TAKEN: {
                pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
                pd.setType(URI.create("/errors/register-user/username-already-taken"));
                pd.setTitle("Username Already Taken");
                pd.setDetail("The username you entered is already in use. Please choose a different username and try again.");
                yield pd;
            } case AuthenticationProviderFactory.ERROR_UNSUPPORTED_GRANT_TYPE: {
                pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
                pd.setType(URI.create("/errors/authentication/unsupported-grant-type"));
                pd.setTitle("Unsupported Grant Type");
                pd.setDetail("The authorization grant type is not supported by the authorization server.");
                yield pd;
            } case AuthenticationProvider.ERROR_UNKNOWN_CLIENT: {
                pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
                pd.setType(URI.create("/errors/authentication/invalid-client"));
                pd.setTitle("Invalid Client");
                pd.setDetail("Client authentication failed. Client is unknown.");
                yield pd;
            } case AuthenticationProvider.ERROR_INVALID_CLIENT_SECRET: {
                pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
                pd.setType(URI.create("/errors/authentication/invalid-grant"));
                pd.setTitle("Invalid Grant");
                pd.setDetail("The provided authorization grant is invalid.");
                yield pd;
            } case AuthenticationProvider.ERROR_UNAUTHORIZED_AUTH_FLOW: {
                pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
                pd.setType(URI.create("/errors/authentication/unauthorized-client"));
                pd.setTitle("Unauthorized Client");
                pd.setDetail("The authenticated client is not authorized to use this authorization grant type.");
                yield pd;
            } case JwtAuthenticationFilter.ERROR_JWT_AUTH_FILTER_UNAUTHORIZED: {
                pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
                pd.setType(URI.create("/errors/unauthorized"));
                pd.setTitle("Unauthorized");
                pd.setDetail("Bearer token is missing or invalid.");
                yield pd;
            } case ApiScopeInterceptor.ERROR_INVALID_SCOPE: {
                pd = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
                pd.setType(URI.create("/errors/forbidden"));
                pd.setTitle("Forbidden");
                pd.setDetail("The provided JWT token does not have the required scope to access this resource.");
                yield pd;
            } case AuthenticationProvider.ERROR_INVALID_REFRESH_TOKEN: {
                pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
                pd.setType(URI.create("/errors/authentication/invalid-refresh-token"));
                pd.setTitle("Invalid Refresh Token");
                pd.setDetail("Refresh token is invalid or has expired.");
                yield pd;
            }
            default: yield ExceptionTranslator.defaultProblemDetail();
        };

        if (!e.getData().isEmpty()) {
            pd.setProperty("data", e.getData());
        }
        
        return pd;
    }

    public static ProblemDetail toProblemDetail(ValidationException e) {
        Map<String, Set<String>> errors = new HashMap<>(e.getErrors());
        for (Map.Entry<String,Set<String>> fieldErrors : errors.entrySet()) {
            fieldErrors.setValue(fieldErrors.getValue().stream()
                .map(ExceptionTranslator::mapValidationError)
                .collect(Collectors.toSet()));
        }

        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setType(URI.create("/errors/validation"));
        pd.setTitle("Validation Error");
        pd.setDetail(mapValidationError(e.getFirstError()));
        pd.setProperty("errors", errors);
        return pd;
    }

    private static String mapValidationError(String type) {
        Map<String, String> messages = new HashMap<>();

        messages.put(RegisterUserUseCase.ERROR_USERNAME_VALIDATION_NOT_NULL, "Username is required.");
        messages.put(RegisterUserUseCase.ERROR_USERNAME_VALIDATION_MIN_LENGTH, "Username must be at least 3 characters long.");
        messages.put(RegisterUserUseCase.ERROR_PASSWORD_VALIDATION_NOT_NULL, "Password is required.");
        messages.put(RegisterUserUseCase.ERROR_PASSWORD_VALIDATION_MIN_LENGTH, "Password must be at least 8 characters long.");

        messages.put(RegisterClientUseCase.ERROR_CLIENT_ID_VALIDATION_NOT_NULL, "Client ID is required");
        messages.put(RegisterClientUseCase.ERROR_CLIENT_NAME_VALIDATION_NOT_NULL, "Client Name is required");

        var message = messages.get(type);
        return Objects.nonNull(message) ? message : type;
    }
}
