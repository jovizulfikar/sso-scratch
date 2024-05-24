package com.example.springoauth2scratch.adapter.jpa.repository;

import com.example.springoauth2scratch.adapter.jpa.entity.JpaClient;
import com.example.springoauth2scratch.adapter.util.MapperUtil;
import com.example.springoauth2scratch.domain.entity.Client;
import com.example.springoauth2scratch.port.repository.ClientRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaQueryBuilderClientRepository implements ClientRepository {

    private final EntityManager entityManager;
    private final MapperUtil mapper;

    @Override
    public Optional<Client> findByClientId(String clientId) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(JpaClient.class);
        var root = criteriaQuery.from(JpaClient.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("client_id"), clientId));

        return entityManager.createQuery(criteriaQuery)
                .getResultList()
                .stream()
                .findFirst()
                .map(mapper::JpaClientToClient);
    }
}
