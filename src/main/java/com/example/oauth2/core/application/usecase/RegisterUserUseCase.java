package com.example.oauth2.core.application.usecase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.example.oauth2.core.application.validation.constraint.MinLength;
import com.example.oauth2.core.application.validation.constraint.NotNull;
import com.example.oauth2.core.common.exception.AppException;
import com.example.oauth2.core.common.exception.ValidationException;
import com.example.oauth2.core.domain.entity.User;
import com.example.oauth2.core.port.repository.UserRepository;
import com.example.oauth2.core.port.security.Hashing;
import com.example.oauth2.core.application.validation.Validator;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final Hashing passwordHash;

    public static final String ERROR_USERNAME_ALREADY_TAKEN = "REGISTER_USER_USE_CASE.USERNAME_ALREADY_TAKEN";
    public static final String ERROR_USERNAME_VALIDATION_NOT_NULL = "REGISTER_USER_USE_CASE.USERNAME_VALIDATION.NOT_NULL";
    public static final String ERROR_USERNAME_VALIDATION_MIN_LENGTH = "REGISTER_USER_USE_CASE.USERNAME_VALIDATION.MIN_LENGTH";
    public static final String ERROR_PASSWORD_VALIDATION_NOT_NULL = "REGISTER_USER_USE_CASE.PASSWORD_VALIDATION.NOT_NULL";
    public static final String ERROR_PASSWORD_VALIDATION_MIN_LENGTH = "REGISTER_USER_USE_CASE.PASSWORD_VALIDATION.MIN_LENGTH";

    @Setter
    @Getter
    public static class Command {
        private String username;
        private String password;
    }

    @Builder
    @Getter
    public static class Result {
        private String userId;
    }

    @SneakyThrows
    public Result registerUser(Command command) {
        validate(command);

        var user = userRepository.findByUsername(command.username).orElse(null);
        if (Objects.nonNull(user)) {
            throw AppException.build(ERROR_USERNAME_ALREADY_TAKEN)
                .put("username", command.getUsername());
        }

        user = User.builder()
            .username(command.username)
            .password(passwordHash.hash(command.password))
            .build();
    
        userRepository.save(user);

        return Result.builder()
            .userId(user.getId())
            .build();
    }

    @SneakyThrows
    private void validate(Command command) {
        var usernameErrors = Validator.build()
            .addConstraint(new NotNull(ERROR_USERNAME_VALIDATION_NOT_NULL))
            .addConstraint(new MinLength(3, ERROR_USERNAME_VALIDATION_MIN_LENGTH))
            .validate(command.getUsername())
            .values();

        var passowrdErrors = Validator.build()
            .addConstraint(new NotNull(ERROR_PASSWORD_VALIDATION_NOT_NULL))
            .addConstraint(new MinLength(8, ERROR_PASSWORD_VALIDATION_MIN_LENGTH))
            .validate(command.getPassword())
            .values();

        if (usernameErrors.isEmpty() && passowrdErrors.isEmpty()) {
            return;
        }
        
        Map<String, Set<String>> errors = new HashMap<>();
        errors.put("username", new HashSet<>(usernameErrors));
        errors.put("password", new HashSet<>(passowrdErrors));
        throw new ValidationException(errors);
    }
    
}
