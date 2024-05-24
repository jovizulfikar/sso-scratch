package com.example.port.repository;

import com.example.domain.entity.AccessToken;

public interface AccessTokenRepository {
    AccessToken save(AccessToken accessToken);
}
