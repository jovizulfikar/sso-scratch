package com.oauth2core.common.exception;

import lombok.Getter;

@Getter
public class OAuth2Exception extends Exception {

    private final String error;
    private final String errorDescription;

    public OAuth2Exception(String error, String errorDescription) {
        super(String.format("%s: %s", error, errorDescription));
        this.error = error;
        this.errorDescription = errorDescription;
    }
}
