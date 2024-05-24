package com.example.oauth2service.common.exception;

public class OAuth2InvalidClientException extends OAuth2Exception {

    public OAuth2InvalidClientException(String errorDescription) {
        super("invalid_client", errorDescription);
    }

}
