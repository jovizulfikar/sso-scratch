package com.example.springoauth2scratch.domain.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenType {
    BEARER("bearer");

    private final String type;
}
