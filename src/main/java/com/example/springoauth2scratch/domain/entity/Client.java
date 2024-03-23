package com.example.springoauth2scratch.domain.entity;

import com.example.springoauth2scratch.oauth2.ClientAuthenticationMethod;
import com.example.springoauth2scratch.oauth2.settings.ClientSettings;
import com.example.springoauth2scratch.oauth2.settings.TokenSettings;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
public class Client {
    private String id;
    private Instant issuedAt;
    private String clientId;
    private Set<String> secrets;
    private Instant expiresAt;
    private String name;
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;
    private Set<String> authorizationGrantTypes = new HashSet<>();
    private Set<String> redirectUris;
    private Set<String> postLogoutRedirectUris;
    private Set<String> scopes;
    private ClientSettings clientSettings;
    private TokenSettings tokenSettings;
    private Set<String> claims;
    private Long accessTokenLifetime;
    private Long refreshTokenLifetime = 43200L;
    private Set<String> audienceUris;
}
