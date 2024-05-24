package com.example.domain.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthorizationGrantType {
    AUTHORIZATION_CODE("authorization_code"),
    REFRESH_TOKEN("refresh_token"),
    CLIENT_CREDENTIALS("client_credentials"),
    PASSWORD("password"),
    JWT_BEARER("urn:ietf:params:oauth:grant-type:jwt-bearer"),
    DEVICE_CODE("urn:ietf:params:oauth:grant-type:device_code");

    private final String granType;
}
