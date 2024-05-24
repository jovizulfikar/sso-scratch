package com.example.port.security;

import com.example.domain.jose.JsonWebSignature;
import com.example.domain.oauth2.JwtClaims;

import java.security.Key;

public interface JwtService {
    String sign(JsonWebSignature jws);
    JwtClaims verify(String jwt, Key jwsKey);
}
