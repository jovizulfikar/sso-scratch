package com.example.springoauth2scratch.adapter.jpa.repository;

import com.example.springoauth2scratch.adapter.jpa.entity.JpaUser;
import com.example.springoauth2scratch.adapter.util.MapperUtil;
import com.example.springoauth2scratch.domain.entity.User;
import com.example.springoauth2scratch.port.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaQuerBuilderUserRepository implements UserRepository {

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
                .map(mapper::JpaUserToUser);
    }

}
