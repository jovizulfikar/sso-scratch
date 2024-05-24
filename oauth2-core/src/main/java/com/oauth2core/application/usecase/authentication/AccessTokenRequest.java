package com.oauth2core.application.usecase.authentication;

import lombok.Data;

@Data
public class AccessTokenRequest {
    private String grantType;
    private String username;
    private String password;
    private String scope;
    private String clientId;
}
