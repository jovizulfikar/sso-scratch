package com.example.sso.infra.jpa.repository;

import com.example.sso.core.domain.entity.Client;
import com.example.sso.core.port.repository.ClientRepository;
import com.example.sso.core.port.util.IdGenerator;
import com.example.sso.infra.jpa.entity.JpaClient;
import com.example.sso.infra.util.MapperUtil;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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
                .map(mapper::client);
    }

    @Override
    @Transactional
    public Client save(Client client) {
        if (Objects.isNull(client.getId())) {
            cleanEntity(client);
            entityManager.persist(mapper.jpaClient(client));
            return client;
        }

        var existingClient = entityManager.find(JpaClient.class, client.getId());
        cleanEntity(client);
        if (Objects.isNull(existingClient)) {
            entityManager.persist(mapper.jpaClient(client));
        } else {
            entityManager.merge(mapper.jpaClient(client));
        }

        return client;
    }

    private void cleanEntity(Client client) {
        if (Objects.isNull(client.getId())) {
            client.setId(idGenerator.generate());
        }

        client.getSecrets().forEach(secret -> {
            secret.setClientId(client.getId());
            if (Objects.isNull(secret.getId())) {
                secret.setId(idGenerator.generate());
            }
        });
    }
}
