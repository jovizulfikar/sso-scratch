package com.example.sso.core.application.usecase.revocation;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RevokeTokenRequest {
    private String clientId;
    private String token;
    private String tokenTypeHint;
}
