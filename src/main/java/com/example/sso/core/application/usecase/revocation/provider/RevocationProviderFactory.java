package com.example.sso.core.application.usecase.revocation.provider;

import com.example.sso.core.common.exception.AppException;
import com.example.sso.core.domain.oauth2.TokenTypeHint;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class RevocationProviderFactory {

    private final RevokeAccessToken revokeAccessToken;
    private final RevokeRefreshToken revokeRefreshToken;

    public static final String ERROR_UNSUPPORTED_TOKEN_TYPE = "REVOCATION_PROVIDER_FACTORY.UNSUPPORTED_TOKEN_TYPE";

    @SneakyThrows
    public RevocationProvider getByTokenType(String tokenType) {
        if (TokenTypeHint.ACCESS_TOKEN.getType().equals(tokenType)) {
            return revokeAccessToken;
        } else if (TokenTypeHint.REFRESH_TOKEN.getType().equals(tokenType)) {
            return revokeRefreshToken;
        } else {
            throw AppException.build(ERROR_UNSUPPORTED_TOKEN_TYPE);
        }
    }
}
