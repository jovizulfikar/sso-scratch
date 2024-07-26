package com.example.oauth2.core.domain.oauth2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class JwtClaims {
    private String iss;
    private Long iat;
    private Long exp;
    private Set<String> aud;
    private String sub;
    private String clientId;
    private String jti;

    @Builder.Default
    private Set<String> scope = new HashSet<>();

    private Long authTime;
    private Byte acr;
    private String amr;
}
