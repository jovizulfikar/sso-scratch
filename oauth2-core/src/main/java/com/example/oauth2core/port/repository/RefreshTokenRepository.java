package com.example.oauth2core.port.repository;

import com.example.oauth2core.domain.entity.RefreshToken;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
}
