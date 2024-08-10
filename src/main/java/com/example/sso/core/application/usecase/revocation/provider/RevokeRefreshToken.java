package com.example.sso.core.application.usecase.revocation.provider;

import com.example.sso.core.application.usecase.revocation.RevokeTokenRequest;
import com.example.sso.core.application.usecase.revocation.RevokeTokenResponse;
import com.example.sso.core.common.exception.AppException;
import com.example.sso.core.port.repository.ClientRepository;
import com.example.sso.core.port.repository.RefreshTokenRepository;
import com.example.sso.core.port.security.Hashing;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class RevokeRefreshToken implements RevocationProvider {

    private final RefreshTokenRepository refreshTokenRepository;
    private final ClientRepository clientRepository;
    private final Hashing passwordHash;

    @Override
    @SneakyThrows
    public RevokeTokenResponse revoke(RevokeTokenRequest revokeTokenRequest) {
        var clientId = revokeTokenRequest.getClientId();

        // fetch client id
        var client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> AppException.build(ERROR_UNKNOWN_CLIENT));

        // verify client secret
        if (client.getSecrets().stream()
                .noneMatch(secret -> passwordHash.verify(revokeTokenRequest.getClientSecret(), secret.getSecret()))) {
            throw AppException.build(ERROR_INVALID_CLIENT_SECRET);
        }

        refreshTokenRepository.findByValueAndClientId(revokeTokenRequest.getToken(), client.getId())
                .ifPresent(refreshToken -> refreshTokenRepository.deleteByValue(refreshToken.getValue()));

        return RevokeTokenResponse.builder()
                .token(revokeTokenRequest.getToken())
                .build();
    }
}
