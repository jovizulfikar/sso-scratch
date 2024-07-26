package com.example.oauth2.core.port.repository;

import com.example.oauth2.core.domain.entity.ApiScope;

import java.util.Optional;

public interface ApiScopeRepository {
    Optional<ApiScope> findByName(String name);
    ApiScope save(ApiScope apiScope);
}
