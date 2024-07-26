package com.example.oauth2.core.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RefreshToken {
    private String id;
    private String value;
    private LocalDateTime expiredAt;
    private String userId;
    private String clientId;
}
