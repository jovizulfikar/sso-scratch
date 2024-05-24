package com.example.oauth2rest.jpa.repository;

import com.example.oauth2rest.jpa.entity.JpaClient;
import com.example.oauth2rest.util.MapperUtil;
import com.oauth2core.domain.entity.Client;
import com.oauth2core.port.repository.ClientRepository;
import com.oauth2core.port.util.IdGenerator;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaQueryBuilderClientRepository implements ClientRepository {

    private final EntityManager entityManager;
    private final MapperUtil mapper;
    private final IdGenerator idGenerator;

    @Override
    public Optional<Client> findByClientId(String clientId) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(JpaClient.class);
        var root = criteriaQuery.from(JpaClient.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("clientId"), clientId));

        return entityManager.createQuery(criteriaQuery)
                .getResultList()
                .stream()
                .findFirst()
                .map(mapper::jpaClientToClient);
    }

    @Override
    public Client save(Client client) {
        if (Objects.isNull(client.getId())) {
            client.setId(idGenerator.generate());
            entityManager.persist(client);
            return client;
        }

        var existingClient = entityManager.find(JpaClient.class, client.getClientId());
        if (Objects.isNull(existingClient)) {
            entityManager.persist(client);
        } else {
            entityManager.merge(client);
        }

        return client;
    }
}
