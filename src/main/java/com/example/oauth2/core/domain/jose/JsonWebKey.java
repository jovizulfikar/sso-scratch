package com.example.oauth2.core.domain.jose;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JsonWebKey {
    private final String kid;
    private final String kty;
    private final String use;
    private final String e;
    private final String n;
    private final String alg;
}
