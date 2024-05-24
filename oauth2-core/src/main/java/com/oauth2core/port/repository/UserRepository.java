package com.oauth2core.port.repository;

import com.oauth2core.domain.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
}
