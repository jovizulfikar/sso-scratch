package com.example.sso.core.domain.jose;

import lombok.Builder;
import lombok.Getter;

import java.security.Key;

import com.example.sso.core.domain.oauth2.JwtClaims;

@Builder
@Getter
public class JsonWebSignature {
    private JwtClaims claims;
    private Key key;
    private String keyId;
    private SignatureAlgorithm algorithm;
}
