package com.example.sso.core.port.repository;

import java.util.Optional;

import com.example.sso.core.domain.entity.Client;

public interface ClientRepository {
    Optional<Client> findByClientId(String clientId);
    Client save(Client client);
}
