package com.example.sso.core.application.usecase.revocation;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RevokeTokenResponse {
    private String token;
}
