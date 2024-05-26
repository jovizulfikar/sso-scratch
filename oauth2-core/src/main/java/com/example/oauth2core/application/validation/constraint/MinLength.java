package com.example.oauth2core.application.validation.constraint;

import java.util.Objects;

import com.example.oauth2core.application.validation.ConstraintValidator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MinLength implements ConstraintValidator {

    private final Integer min;
    private final String message;

    @Override
    public boolean validate(Object value) {
        if (Objects.isNull(value)) {
            return true;
        }

        if (value instanceof String) {
            return ((String) value).length() >= min;
        }

        throw new UnsupportedOperationException("Can't validate type of " + value.getClass().getName());
    }
}
