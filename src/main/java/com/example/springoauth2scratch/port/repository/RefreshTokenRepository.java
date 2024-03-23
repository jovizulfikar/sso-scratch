package com.example.springoauth2scratch.port.repository;

import com.example.springoauth2scratch.domain.oauth2.RefreshToken;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
}
