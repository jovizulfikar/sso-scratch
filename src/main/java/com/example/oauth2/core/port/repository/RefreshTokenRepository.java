package com.example.oauth2.core.port.repository;

import com.example.oauth2.core.domain.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findByValueAndClientId(String value, String clientId);
    void deleteByValue(String value);
}
