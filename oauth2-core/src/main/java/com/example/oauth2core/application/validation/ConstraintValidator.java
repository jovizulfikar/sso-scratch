package com.example.oauth2core.application.validation;

public interface ConstraintValidator {
    boolean validate(Object value);
    String getMessage();
}
