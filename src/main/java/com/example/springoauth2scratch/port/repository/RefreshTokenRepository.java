package com.example.springoauth2scratch.port.repository;

import com.example.springoauth2scratch.domain.entity.RefreshToken;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
}
