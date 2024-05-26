package com.example.oauth2core.port.repository;

import com.example.oauth2core.domain.entity.AccessToken;

public interface AccessTokenRepository {
    AccessToken save(AccessToken accessToken);
}
