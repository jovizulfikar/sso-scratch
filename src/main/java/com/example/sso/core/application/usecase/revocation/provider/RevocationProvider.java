package com.example.sso.core.application.usecase.revocation.provider;

import com.example.sso.core.application.usecase.revocation.RevokeTokenRequest;

public interface RevocationProvider {
    void revoke(RevokeTokenRequest revokeTokenRequest);
}
