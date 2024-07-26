package com.example.oauth2.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Setter
@Getter
public class JpaRefreshToken {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "value", unique = true)
    private String value;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "user_id", nullable = true)
    private String userId;

    @Column(name = "client_id")
    private String clientId;
}
