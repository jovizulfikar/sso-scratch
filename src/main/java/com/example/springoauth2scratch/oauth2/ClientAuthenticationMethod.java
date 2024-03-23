package com.example.springoauth2scratch.oauth2;

public record ClientAuthenticationMethod(String value) {
    public static final ClientAuthenticationMethod CLIENT_SECRET_BASIC = new ClientAuthenticationMethod("client_secret_basic");
    public static final ClientAuthenticationMethod CLIENT_SECRET_POST = new ClientAuthenticationMethod("client_secret_post");
    public static final ClientAuthenticationMethod CLIENT_SECRET_JWT = new ClientAuthenticationMethod("client_secret_jwt");
    public static final ClientAuthenticationMethod PRIVATE_KEY_JWT = new ClientAuthenticationMethod("private_key_jwt");
    public static final ClientAuthenticationMethod NONE = new ClientAuthenticationMethod("none");
}

