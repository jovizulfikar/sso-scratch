package com.example.sso.core.application.usecase.revocation.provider;

import com.example.sso.core.application.usecase.revocation.RevokeTokenRequest;
import com.example.sso.core.port.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RevokeRefreshToken implements RevocationProvider {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void revoke(RevokeTokenRequest revokeTokenRequest) {
        refreshTokenRepository.findByValue(revokeTokenRequest.getToken())
                .ifPresent(refreshToken -> refreshTokenRepository.deleteByValue(refreshToken.getValue()));
    }
}
