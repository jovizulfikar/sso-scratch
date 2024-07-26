package com.example.oauth2.infra.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import com.example.oauth2.infra.jpa.entity.JpaUser;
import org.springframework.stereotype.Repository;

import com.example.oauth2.core.domain.entity.User;
import com.example.oauth2.core.port.repository.UserRepository;
import com.example.oauth2.core.port.util.IdGenerator;
import com.example.oauth2.infra.util.MapperUtil;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaQueryBuilderUserRepository implements UserRepository {

    private final EntityManager entityManager;
    private final MapperUtil mapper;
    private final IdGenerator idGenerator;

    @Override
    public Optional<User> findByUsername(String username) {
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(JpaUser.class);
        var root = cq.from(JpaUser.class);

        cq.select(root)
                .where(cb.equal(root.get("username"), username));

        return entityManager.createQuery(cq)
                .getResultList()
                .stream()
                .findFirst()
                .map(mapper::user);
    }

    @Override
    @Transactional
    public User save(User user) {
        if (Objects.isNull(user.getId())) {
            user.setId(idGenerator.generate());
            entityManager.persist(mapper.jpaUser(user));
            return user;
        }

        var existingUser = entityManager.find(JpaUser.class, user.getId());
        if (Objects.isNull(existingUser)) {
            entityManager.persist(mapper.jpaUser(user));
        } else {
            entityManager.merge(mapper.jpaUser(user));
        }

        return user;
    }

}
