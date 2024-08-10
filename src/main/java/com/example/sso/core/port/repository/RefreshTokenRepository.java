package com.example.sso.core.port.repository;

import com.example.sso.core.domain.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findByValue(String value);
    Optional<RefreshToken> findByValueAndClientId(String value, String clientId);
    void deleteByValue(String value);
}
