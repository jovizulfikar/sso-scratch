package com.example.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RefreshToken {
    private String id;
    private String value;
    private LocalDateTime expiredAt;
    private String userId;
    private String clientId;
}
