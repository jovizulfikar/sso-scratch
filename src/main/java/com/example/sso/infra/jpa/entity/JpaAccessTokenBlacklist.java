package com.example.sso.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_token_blacklist")
@Builder
@Getter
public class JpaAccessTokenBlacklist {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "jti", nullable = false, unique = true)
    private String jti;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;
}
