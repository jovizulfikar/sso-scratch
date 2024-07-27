package com.example.sso.core.application.config;

public interface SsoConfig {
    String getIssuer();
    String getPrivateKey();
    String getPublicKey();
    String getKeyId();
    String getJwksUri();
}
