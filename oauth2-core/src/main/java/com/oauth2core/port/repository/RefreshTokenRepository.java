package com.oauth2core.port.repository;

import com.oauth2core.domain.entity.RefreshToken;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
}
