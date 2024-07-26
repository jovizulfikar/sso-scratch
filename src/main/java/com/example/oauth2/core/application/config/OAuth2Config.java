package com.example.oauth2.core.application.config;

public interface OAuth2Config {
    String getIssuer();
    String getPrivateKey();
    String getPublicKey();
    String getKeyId();
    String getJwksUri();
}
