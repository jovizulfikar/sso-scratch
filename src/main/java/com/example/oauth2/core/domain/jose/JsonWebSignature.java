package com.example.oauth2.core.domain.jose;

import lombok.Builder;
import lombok.Getter;

import java.security.Key;

import com.example.oauth2.core.domain.oauth2.JwtClaims;

@Builder
@Getter
public class JsonWebSignature {
    private JwtClaims claims;
    private Key key;
    private String keyId;
    private SignatureAlgorithm algorithm;
}
