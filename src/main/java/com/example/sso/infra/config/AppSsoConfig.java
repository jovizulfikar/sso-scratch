package com.example.sso.infra.config;

import com.example.sso.core.application.config.SsoConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("openid")
@Setter
@Getter
public class AppSsoConfig implements SsoConfig {
    private String issuer;
    private String privateKey;
    private String publicKey;
    private String keyId;
    private String jwksUri;
}
