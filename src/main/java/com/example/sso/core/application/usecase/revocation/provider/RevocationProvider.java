package com.example.sso.core.application.usecase.revocation.provider;

import com.example.sso.core.application.usecase.revocation.RevokeTokenRequest;
import com.example.sso.core.application.usecase.revocation.RevokeTokenResponse;

public interface RevocationProvider {
    RevokeTokenResponse revoke(RevokeTokenRequest revokeTokenRequest);
}
