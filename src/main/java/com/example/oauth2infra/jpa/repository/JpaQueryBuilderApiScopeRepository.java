package com.example.oauth2infra.jpa.repository;

import com.example.oauth2core.domain.entity.ApiScope;
import com.example.oauth2core.port.repository.ApiScopeRepository;
import com.example.oauth2core.port.util.IdGenerator;
import com.example.oauth2infra.jpa.entity.JpaApiScope;
import com.example.oauth2infra.util.MapperUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaQueryBuilderApiScopeRepository implements ApiScopeRepository {

    private final EntityManager entityManager;
    private final MapperUtil mapper;
    private final IdGenerator idGenerator;

    @Override
    public Optional<ApiScope> findByName(String name) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(JpaApiScope.class);
        var root = criteriaQuery.from(JpaApiScope.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("name"), name));

        return entityManager.createQuery(criteriaQuery)
                .getResultList()
                .stream()
                .findFirst()
                .map(mapper::apiScope);
    }

    @Override
    @Transactional
    public ApiScope save(ApiScope apiScope) {
        if (Objects.isNull(apiScope.getId())) {
            apiScope.setId(idGenerator.generate());
            entityManager.persist(mapper.jpaApiScope(apiScope));
            return apiScope;
        }

        var existingApiScope = entityManager.find(JpaApiScope.class, apiScope.getId());
        if (Objects.isNull(existingApiScope)) {
            entityManager.persist(mapper.jpaApiScope(apiScope));
        } else {
            entityManager.merge(mapper.jpaApiScope(apiScope));
        }

        return apiScope;
    }
    
}
