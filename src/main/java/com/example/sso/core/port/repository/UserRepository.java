package com.example.sso.core.port.repository;

import java.util.Optional;

import com.example.sso.core.domain.entity.User;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User save(User user);
}
