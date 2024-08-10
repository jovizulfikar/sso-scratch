package com.example.sso.core.domain.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenTypeHint {
    ACCESS_TOKEN("access_token"),
    REFRESH_TOKEN("refresh_token");

    private final String type;
}
