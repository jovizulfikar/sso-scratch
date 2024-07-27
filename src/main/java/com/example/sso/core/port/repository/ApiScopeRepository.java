package com.example.sso.core.port.repository;

import com.example.sso.core.domain.entity.ApiScope;

import java.util.Optional;

public interface ApiScopeRepository {
    Optional<ApiScope> findByName(String name);
    ApiScope save(ApiScope apiScope);
}
