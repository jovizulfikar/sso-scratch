package com.example.sso.core.application.service;

import com.example.sso.core.domain.entity.Client;
import com.example.sso.core.domain.entity.RefreshToken;
import com.example.sso.core.domain.entity.User;
import com.example.sso.core.port.repository.RefreshTokenRepository;
import com.example.sso.core.port.util.IdGenerator;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class RefreshTokenService {

    private final IdGenerator idGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(Client client, User user) {
        user = Objects.nonNull(user) ? user : User.builder().build();
        var issuedAt = System.currentTimeMillis();
        var refreshTokenTtl = TimeUnit.SECONDS.toMillis(client.getRefreshTokenTtl());
        var expiredAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(issuedAt + refreshTokenTtl), ZoneId.systemDefault());

        var refreshToken = RefreshToken.builder()
                .id(idGenerator.generate())
                .value(idGenerator.generate())
                .expiredAt(expiredAt)
                .userId(user.getId())
                .clientId(client.getId())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken generateRefreshToken(Client client) {
        return generateRefreshToken(client, null);
    }
}
