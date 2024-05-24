package com.example.springoauth2scratch.port.repository;

import com.example.springoauth2scratch.domain.entity.AccessToken;

public interface AccessTokenRepository {
    AccessToken save(AccessToken accessToken);
}
