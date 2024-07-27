package com.example.sso.core.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Builder
public class Client {

    private String id;
    private String clientId;
    private String name;
    private LocalDateTime issuedAt;

    @Builder.Default
    private Set<ClientSecret> secrets = new HashSet<>();

    @Builder.Default
    private Set<String> grantTypes = new HashSet<>();

    @Builder.Default
    private Set<ApiScope> apiScopes = new HashSet<>();

    // @Builder.Default
    // private Set<String> claims = new HashSet<>();

    @Builder.Default
    private Long accessTokenTtl = TimeUnit.HOURS.toSeconds(1);

    @Builder.Default
    private Long refreshTokenTtl = TimeUnit.DAYS.toSeconds(30);

    @Builder.Default
    private Set<String> audienceUris = new HashSet<>();
}
