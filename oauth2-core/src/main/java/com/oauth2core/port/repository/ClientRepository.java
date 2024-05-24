package com.oauth2core.port.repository;

import com.oauth2core.domain.entity.Client;

import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findByClientId(String clientId);
    Client save(Client client);
}
