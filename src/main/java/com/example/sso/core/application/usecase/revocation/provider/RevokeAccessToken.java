package com.example.sso.core.application.usecase.revocation.provider;

import com.example.sso.core.application.usecase.revocation.RevokeTokenRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RevokeAccessToken implements RevocationProvider {

    @Override
    public void revoke(RevokeTokenRequest revokeTokenRequest) {

    }
}
