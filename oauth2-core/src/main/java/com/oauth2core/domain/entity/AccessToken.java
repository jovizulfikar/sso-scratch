package com.oauth2core.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AccessToken {
    private String id;
    private String value;
    private Long expiresIn;
    private LocalDateTime issuedAt;
    private String userId;
}
