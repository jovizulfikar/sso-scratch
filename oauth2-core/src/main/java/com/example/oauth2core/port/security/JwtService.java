package com.example.oauth2core.port.security;

import java.security.Key;

import com.example.oauth2core.domain.jose.JsonWebSignature;
import com.example.oauth2core.domain.oauth2.JwtClaims;

public interface JwtService {
    String sign(JsonWebSignature jws);
    JwtClaims verify(String jwt, Key jwsKey);
}
