package com.example.oauth2rest.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "id")
    private String id;

    @Column(name = "client_id", unique = true, nullable = false)
    private String clientId;

//    @ElementCollection
//    @CollectionTable(name = "client_secrets", joinColumns = @JoinColumn(name = "client_id"))
//    @Column(name = "secret")
//    private Set<String> secrets;

    @Column(name = "name")
    private String name;

    @ElementCollection
    @CollectionTable(name = "client_grant_types", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "grant_type")
    private Set<String> grantTypes;

    @ElementCollection
    @CollectionTable(name = "client_audience_uris", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "audience_uri")
    private Set<String> audienceUris;

    @ElementCollection
    @CollectionTable(name = "client_scopes", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "scope")
    private Set<String> scopes;

    @ElementCollection
    @CollectionTable(name = "client_claims", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "grant_type")
    private Set<String> claims;

    @Column(name = "access_token_ttl")
    private Long accessTokenTtl;

    @Column(name = "refresh_token_ttl")
    private Long refreshTokenTtl;
}
