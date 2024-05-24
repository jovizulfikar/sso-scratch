package com.example.port.repository;

import com.example.domain.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
}
