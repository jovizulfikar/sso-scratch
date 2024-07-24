package com.example.oauth2infra.jpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "client_secrets")
@Setter
@Getter
public class JpaClientSecret {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "secret")
    private String secret;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}
