package com.example.springoauth2scratch.domain.jose;

import lombok.Builder;
import lombok.Getter;

import java.security.Key;

@Builder
@Getter
public class JsonWebSignature {
    private JwtClaims claims;
    private Key key;
    private String keyId;
    private SignatureAlgorithm algorithm;
}
