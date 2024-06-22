package com.example.oauth2infra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("oauth2")
@Setter
@Getter
public class OAuth2Config implements com.example.oauth2core.application.config.OAuth2Config {
    private String issuer;
    private String privateKey;
    private String publicKey;
    private String keyId;
}
