package com.example.springoauth2scratch.port.repository;

import com.example.springoauth2scratch.domain.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
}
