package com.example.springoauth2scratch.domain.oauth2;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AccessToken {
    private final String value;
    private final Long expiresIn;
    private final LocalDateTime issuedAt;
    private final String userId;
}
