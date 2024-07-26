package com.example.oauth2.core.port.repository;

import java.util.Optional;

import com.example.oauth2.core.domain.entity.User;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User save(User user);
}
