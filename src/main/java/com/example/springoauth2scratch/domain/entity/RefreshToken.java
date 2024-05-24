package com.example.springoauth2scratch.domain.oauth2;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RefreshToken {
    private final String value;
    private final LocalDateTime expiredAt;
    private final String userId;
    private final String clientId;
}
