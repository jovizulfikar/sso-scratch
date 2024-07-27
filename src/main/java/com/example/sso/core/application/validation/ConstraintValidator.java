package com.example.sso.core.application.validation;

public interface ConstraintValidator {
    boolean validate(Object value);
    String getMessage();
}
