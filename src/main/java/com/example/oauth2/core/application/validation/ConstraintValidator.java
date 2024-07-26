package com.example.oauth2.core.application.validation;

public interface ConstraintValidator {
    boolean validate(Object value);
    String getMessage();
}
