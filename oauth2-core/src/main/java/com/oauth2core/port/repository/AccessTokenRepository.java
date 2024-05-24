package com.oauth2core.port.repository;

import com.oauth2core.domain.entity.AccessToken;

public interface AccessTokenRepository {
    AccessToken save(AccessToken accessToken);
}
