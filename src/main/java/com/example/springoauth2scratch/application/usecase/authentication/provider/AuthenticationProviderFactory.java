package com.example.springoauth2scratch.application.authentication.provider;

import com.example.springoauth2scratch.common.exception.AppException;
import com.example.springoauth2scratch.domain.oauth2.AuthorizationGrantType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class AuthenticationProviderFactory {
    private final ResourceOwnerPasswordCredentials resourceOwnerPasswordCredentials;

    public static final String ERROR_UNSUPPORTED_GRANT_TYPE = "AUTHENTICATION_PROVIDER_FACTORY.UNSUPPORTED_GRANT_TYPE";

    @SneakyThrows
    public AuthenticationProvider getByGrantType(String grantType) {
        if (AuthorizationGrantType.PASSWORD.getGranType().equals(grantType)) {
            return resourceOwnerPasswordCredentials;
        } else {
            throw AppException.build(ERROR_UNSUPPORTED_GRANT_TYPE);
        }
    }
}
