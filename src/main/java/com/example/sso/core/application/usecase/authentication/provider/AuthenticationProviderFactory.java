package com.example.sso.core.application.usecase.authentication.provider;

import com.example.sso.core.common.exception.AppException;
import com.example.sso.core.domain.oauth2.AuthorizationGrantType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class AuthenticationProviderFactory {

    private final AuthenticateResourceOwnerPasswordCredentials authenticateResourceOwnerPasswordCredentials;
    private final AuthenticateClientCredentials authenticateClientCredentials;
    private final AuthenticateRefreshToken authenticateRefreshToken;

    public static final String ERROR_UNSUPPORTED_GRANT_TYPE = "AUTHENTICATION_PROVIDER_FACTORY.UNSUPPORTED_GRANT_TYPE";

    @SneakyThrows
    public AuthenticationProvider getByGrantType(String grantType) {
        if (AuthorizationGrantType.PASSWORD.getGranType().equals(grantType)) {
            return authenticateResourceOwnerPasswordCredentials;
        } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.getGranType().equals(grantType)) {
            return authenticateClientCredentials;
        } else if (AuthorizationGrantType.REFRESH_TOKEN.getGranType().equals(grantType)) {
            return authenticateRefreshToken;
        } else {
            throw AppException.build(ERROR_UNSUPPORTED_GRANT_TYPE);
        }
    }
}
