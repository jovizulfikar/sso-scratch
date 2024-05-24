package com.example.springoauth2scratch.application.authentication;

import lombok.Data;

@Data
public class AccessTokenRequest {
    private String grantType;
    private String username;
    private String password;
    private String scope;
    private String clientId;
}
