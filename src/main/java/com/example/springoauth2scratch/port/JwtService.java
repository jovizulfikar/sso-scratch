package com.example.springoauth2scratch.port;

import com.example.springoauth2scratch.domain.jose.JsonWebSignature;

public interface JwtService {
    String sign(JsonWebSignature jws);
    String verify(String jwt);
}
