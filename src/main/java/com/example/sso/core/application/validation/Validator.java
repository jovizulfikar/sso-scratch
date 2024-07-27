package com.example.sso.core.application.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validator {

    private List<ConstraintValidator> constraintValidators =  new ArrayList<>();

    public static Validator build() {
        return new Validator();
    }

    public Validator addConstraint(ConstraintValidator constraintValidator) {
        constraintValidators.add(constraintValidator);
        return this;
    }

    public <T> Map<String, String> validate(T value) {
        Map<String, String> errorMap = new HashMap<>();
        constraintValidators.forEach(constraint -> {
            if (!constraint.validate(value)) {
                errorMap.put(ConstraintValidator.class.getName(), constraint.getMessage());
            }
        });

        return errorMap;
    }
}
