package com.example.oauth2.core.port.repository;

import java.util.Optional;

import com.example.oauth2.core.domain.entity.Client;

public interface ClientRepository {
    Optional<Client> findByClientId(String clientId);
    Client save(Client client);
}
