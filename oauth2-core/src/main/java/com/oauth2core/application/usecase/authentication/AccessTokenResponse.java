package com.example.oauth2service.application.usecase.authentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessTokenResponse {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private String idToken;
    private String tokenType;
}
