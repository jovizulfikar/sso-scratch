package com.example.oauth2infra.jpa.repository;

import com.example.oauth2core.domain.entity.RefreshToken;
import com.example.oauth2core.port.repository.RefreshTokenRepository;
import com.example.oauth2core.port.util.IdGenerator;
import com.example.oauth2infra.jpa.entity.JpaRefreshToken;
import com.example.oauth2infra.util.MapperUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaQueryBuilderRefreshTokenRepository implements RefreshTokenRepository {
    
    private final EntityManager entityManager;
    private final MapperUtil mapper;
    private final IdGenerator idGenerator;

    @Override
    @Transactional
    public RefreshToken save(RefreshToken refreshToken) {
        if (Objects.isNull(refreshToken.getId())) {
            refreshToken.setId(idGenerator.generate());
            entityManager.persist(mapper.jpaRefreshToken(refreshToken));
            return refreshToken;
        }

        var existingRefreshToken = entityManager.find(JpaRefreshToken.class, refreshToken.getId());
        if (Objects.isNull(existingRefreshToken)) {
            entityManager.persist(mapper.jpaRefreshToken(refreshToken));
        } else {
            entityManager.merge(mapper.jpaRefreshToken(refreshToken));
        }

        return refreshToken;
    }

    @Override
    public Optional<RefreshToken> findByValueAndClientId(String value, String clientId) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(JpaRefreshToken.class);
        var root = criteriaQuery.from(JpaRefreshToken.class);

        criteriaQuery.select(root)
            .where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("value"), value),
                criteriaBuilder.equal(root.get("clientId"), clientId)
            ));

        return entityManager.createQuery(criteriaQuery)
                .getResultList()
                .stream()
                .findFirst()
                .map(mapper::refreshToken);
    }

    @Override
    @Transactional
    public void deleteByValue(String value) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaDelete = criteriaBuilder.createCriteriaDelete(JpaRefreshToken.class);
        var root = criteriaDelete.from(JpaRefreshToken.class);

        criteriaDelete.where(criteriaBuilder.equal(root.get("value"), value));
        entityManager.createQuery(criteriaDelete).executeUpdate();
    }


}
