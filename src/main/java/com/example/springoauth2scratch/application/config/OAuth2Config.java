package com.example.springoauth2scratch.application.config;

import lombok.Data;

@Data
public class OAuth2Config {
    private final String issuer;
    private final String privateKey;
    private final String publicKey;
}
