package com.example.springoauth2scratch.port.repository;

import com.example.springoauth2scratch.domain.entity.Client;

import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findByClientId(String id);
}
