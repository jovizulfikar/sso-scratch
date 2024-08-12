package com.example.sso.infra.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class JpaClient {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "client_id", unique = true, nullable = false)
    private String clientId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client_id")
    private Set<JpaClientSecret> secrets;

    @Column(name = "name")
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_grant_types", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "grant_type")
    private Set<String> grantTypes;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_audience_uris", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "audience_uri")
    private Set<String> audienceUris;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "client_api_scope", 
        joinColumns = @JoinColumn(name = "client_id"), 
        inverseJoinColumns = @JoinColumn(name = "api_scope_id"))
    private Set<JpaApiScope> apiScopes;

    @Column(name = "access_token_ttl")
    private Long accessTokenTtl;

    @Column(name = "refresh_token_ttl")
    private Long refreshTokenTtl;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;
}
