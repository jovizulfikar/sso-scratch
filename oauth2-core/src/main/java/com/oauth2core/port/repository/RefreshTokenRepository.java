package com.example.port.repository;

import com.example.domain.entity.RefreshToken;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
}
