package com.example.oauth2.core.application.usecase.authentication;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccessTokenRequest {
    private String grantType;
    private String username;
    private String password;
    private String scope;
    private String clientId;
    private String clientSecret;
    private String refreshToken;
}
