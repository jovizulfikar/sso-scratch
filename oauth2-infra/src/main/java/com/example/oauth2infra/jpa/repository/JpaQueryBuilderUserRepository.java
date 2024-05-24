package com.example.oauth2infra.jpa.repository;

import com.example.oauth2infra.jpa.entity.JpaUser;
import com.example.oauth2infra.util.MapperUtil;
import com.oauth2core.domain.entity.User;
import com.oauth2core.port.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaQueryBuilderUserRepository implements UserRepository {

    private final EntityManager entityManager;
    private final MapperUtil mapper;

    @Override
    public Optional<User> findByUsername(String username) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(JpaUser.class);
        var root = criteriaQuery.from(JpaUser.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("username"), username));

        return entityManager.createQuery(criteriaQuery)
                .getResultList()
                .stream()
                .findFirst()
                .map(mapper::jpaUserToUser);
    }

}
