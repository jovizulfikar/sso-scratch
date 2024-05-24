package com.example.port.repository;

import com.example.domain.entity.Client;

import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findByClientId(String clientId);
    Client save(Client client);
}
