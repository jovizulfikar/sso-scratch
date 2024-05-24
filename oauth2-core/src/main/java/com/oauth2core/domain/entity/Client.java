package com.example.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Data
@Builder
public class Client {
    private String id;
//    private Instant issuedAt;
    private String clientId;
    private Set<String> secrets;
//    private Instant expiresAt;
    private String name;
//    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;
    private Set<String> grantTypes = new HashSet<>();
//    private Set<String> redirectUris;
//    private Set<String> postLogoutRedirectUris;
    private Set<String> scopes;
//    private ClientSettings clientSettings;
//    private TokenSettings tokenSettings;
    private Set<String> claims;
    private Long accessTokenTtl = TimeUnit.HOURS.toSeconds(1);
    private Long refreshTokenTtl = TimeUnit.DAYS.toSeconds(30);
    private Set<String> audienceUris = new HashSet<>();
}
