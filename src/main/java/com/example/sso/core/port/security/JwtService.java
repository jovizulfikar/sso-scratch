package com.example.sso.core.port.security;

import com.example.sso.core.domain.jose.JsonWebSignature;
import com.example.sso.core.domain.oauth2.JwtClaims;

import java.security.Key;

public interface JwtService {
    String sign(JsonWebSignature jws);
    JwtClaims verify(String jwt, Key jwsKey);
}
