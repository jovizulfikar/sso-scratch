package com.example.oauth2.rest.controller.v1;

import com.example.oauth2.rest.annotation.ApiScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.oauth2.core.application.usecase.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping
    @ApiScope("oauth2.register-user")
    public ResponseEntity<RegisterUserUseCase.Response> postUsers(@RequestBody RegisterUserUseCase.Request request) {
        var response = registerUserUseCase.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
