package com.example.oauth2rest.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import com.example.oauth2core.application.usecase.RegisterUserUseCase;
import com.example.oauth2core.common.exception.AppException;
import com.example.oauth2core.common.exception.ValidationException;

public class ExceptionTranslator {

    public static ProblemDetail defaultProblemDetail() {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong.");
        detail.setType(URI.create("/errors/" + detail.getTitle().toLowerCase().replace(" ", "-")));
        return detail;
    }

    public static ProblemDetail toProblemDetail(AppException e) {
        ProblemDetail pd = switch (e.getType()) {
            case RegisterUserUseCase.ERROR_USERNAME_ALREADY_TAKEN: {
                pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
                pd.setType(URI.create("/errors/register-user/username-already-taken"));
                pd.setTitle("Username Already Taken");
                pd.setDetail("The username you entered is already in use. Please choose a different username and try again.");
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
                .map(fieldError -> mapValidationError(fieldError))
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

        var message = messages.get(type);
        return Objects.nonNull(message) ? message : type;
    }
}
