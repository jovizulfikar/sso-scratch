package com.example.sso.core.application.usecase.revocation.provider;

import com.example.sso.core.application.usecase.revocation.RevokeTokenRequest;
import com.example.sso.core.application.usecase.revocation.RevokeTokenResponse;
import com.example.sso.core.port.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class RevokeRefreshToken implements RevocationProvider {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @SneakyThrows
    public RevokeTokenResponse revoke(RevokeTokenRequest revokeTokenRequest) {
        refreshTokenRepository.findByValueAndClientId(revokeTokenRequest.getToken(), revokeTokenRequest.getClientId())
                .ifPresent(refreshToken -> refreshTokenRepository.deleteByValue(refreshToken.getValue()));

        return RevokeTokenResponse.builder()
                .token(revokeTokenRequest.getToken())
                .build();
    }
}
