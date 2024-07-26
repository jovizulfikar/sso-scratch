package com.example.oauth2.core.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ClientSecret {
    private String id;
    private String clientId;
    private String secret;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
}
