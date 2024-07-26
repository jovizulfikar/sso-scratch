package com.example.oauth2.core.port.security;

import com.example.oauth2.core.domain.jose.JsonWebSignature;
import com.example.oauth2.core.domain.oauth2.JwtClaims;

import java.security.Key;

public interface JwtService {
    String sign(JsonWebSignature jws);
    JwtClaims verify(String jwt, Key jwsKey);
}
