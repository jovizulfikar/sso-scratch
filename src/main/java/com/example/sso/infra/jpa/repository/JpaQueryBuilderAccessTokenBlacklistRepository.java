package com.example.sso.infra.jpa.repository;

import com.example.sso.core.application.service.KeyManager;
import com.example.sso.core.port.repository.AccessTokenBlacklistRepostory;
import com.example.sso.core.port.security.JwtService;
import com.example.sso.core.port.util.IdGenerator;
import com.example.sso.infra.jpa.entity.JpaAccessTokenBlacklist;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Repository
@RequiredArgsConstructor
public class JpaQueryBuilderAccessTokenBlacklistRepository implements AccessTokenBlacklistRepostory {

    private final EntityManager entityManager;
    private final JwtService jwtService;
    private final KeyManager keyManager;
    private final IdGenerator idGenerator;

    @Override
    @Transactional
    public String save(String jwt) {
        var claims = jwtService.getClaims(jwt, keyManager.getRsaPublicKey());

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var countQuery = criteriaBuilder.createQuery(Long.class);
        var root = countQuery.from(JpaAccessTokenBlacklist.class);

        countQuery.select(criteriaBuilder.count(root))
                .where(criteriaBuilder.equal(root.get("jti"), claims.getJti()));

        var count = entityManager.createQuery(countQuery).getSingleResult();
        if (count > 0) {
            return claims.getJti();
        }

        var accessTokenBlacklist = JpaAccessTokenBlacklist.builder()
                .id(idGenerator.generate())
                .jti(claims.getJti())
                .expiredAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(claims.getExp()), ZoneId.systemDefault()))
                .build();

        entityManager.persist(accessTokenBlacklist);
        return claims.getJti();
    }
}
