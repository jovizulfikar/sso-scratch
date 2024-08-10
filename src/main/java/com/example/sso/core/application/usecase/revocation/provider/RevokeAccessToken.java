package com.example.sso.core.application.usecase.revocation.provider;

import com.example.sso.core.application.usecase.revocation.RevokeTokenRequest;
import com.example.sso.core.application.usecase.revocation.RevokeTokenResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RevokeAccessToken implements RevocationProvider {

    @Override
    public RevokeTokenResponse revoke(RevokeTokenRequest revokeTokenRequest) {
        return RevokeTokenResponse.builder()
                .token(revokeTokenRequest.getToken())
                .build();
    }
}
