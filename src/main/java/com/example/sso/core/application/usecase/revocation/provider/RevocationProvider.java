package com.example.sso.core.application.usecase.revocation.provider;

import com.example.sso.core.application.usecase.revocation.RevokeTokenRequest;
import com.example.sso.core.application.usecase.revocation.RevokeTokenResponse;

public interface RevocationProvider {
    String ERROR_UNKNOWN_CLIENT = "REVOCATION_PROVIDER.CLIENT_UNKNOWN_CLIENT";
    String ERROR_INVALID_CLIENT_SECRET = "REVOCATION_PROVIDER.INVALID_CLIENT_SECRET";

    RevokeTokenResponse revoke(RevokeTokenRequest revokeTokenRequest);
}
