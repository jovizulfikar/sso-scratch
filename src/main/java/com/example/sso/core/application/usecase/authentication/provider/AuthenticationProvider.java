package com.example.sso.core.application.usecase.authentication.provider;

import com.example.sso.core.application.usecase.authentication.AccessTokenRequest;
import com.example.sso.core.application.usecase.authentication.AccessTokenResponse;

public interface AuthenticationProvider {
    String ERROR_UNAUTHORIZED_AUTH_FLOW = "AUTHENTICATION_PROVIDER.UNAUTHORIZED_AUTH_FLOW";
    String ERROR_INVALID_RESOURCE_OWNER_CREDENTIALS = "AUTHENTICATION_PROVIDER.INVALID_RESOURCE_OWNER_CREDENTIALS";
    String ERROR_INVALID_REFRESH_TOKEN = "AUTHENTICATION_PROVIDER.INVALID_REFRESH_TOKEN";

    AccessTokenResponse authenticate(AccessTokenRequest accessTokenRequest);
}
