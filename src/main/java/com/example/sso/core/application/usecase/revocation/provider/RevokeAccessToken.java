package com.example.sso.core.application.usecase.revocation.provider;

import com.example.sso.core.application.usecase.revocation.RevokeTokenRequest;
import com.example.sso.core.application.usecase.revocation.RevokeTokenResponse;
import com.example.sso.core.port.repository.AccessTokenBlacklistRepostory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RevokeAccessToken implements RevocationProvider {

    private final AccessTokenBlacklistRepostory accessTokenBlacklistRepostory;

    @Override
    public RevokeTokenResponse revoke(RevokeTokenRequest revokeTokenRequest) {
        accessTokenBlacklistRepostory.save(revokeTokenRequest.getToken());
        return RevokeTokenResponse.builder()
                .token(revokeTokenRequest.getToken())
                .build();
    }
}
