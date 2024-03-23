package com.example.springoauth2scratch.oauth2.settings;

import com.example.springoauth2scratch.jose.jws.SignatureAlgorithm;
import lombok.Data;

import java.time.Duration;

@Data
public class TokenSettings {
    private Duration getAuthorizationCodeTimeToLive;
    private Duration getAccessTokenTimeToLive;
    private OAuth2TokenFormat getAccessTokenFormat;
    private Duration getDeviceCodeTimeToLive;
    private boolean isReuseRefreshTokens;
    private Duration getRefreshTokenTimeToLive;
    private SignatureAlgorithm getIdTokenSignatureAlgorithm;
}
