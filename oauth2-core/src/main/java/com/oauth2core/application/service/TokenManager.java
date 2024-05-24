package com.oauth2core.application.service;

import com.oauth2core.application.config.OAuth2Config;
import com.oauth2core.domain.entity.AccessToken;
import com.oauth2core.domain.entity.Client;
import com.oauth2core.domain.entity.RefreshToken;
import com.oauth2core.domain.entity.User;
import com.oauth2core.domain.jose.JsonWebSignature;
import com.oauth2core.domain.jose.SignatureAlgorithm;
import com.oauth2core.domain.oauth2.JwtClaims;
import com.oauth2core.port.repository.AccessTokenRepository;
import com.oauth2core.port.repository.RefreshTokenRepository;
import com.oauth2core.port.security.JwtService;
import com.oauth2core.port.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class TokenManager {

    private final OAuth2Config oauth2Config;
    private final IdGenerator idGenerator;
    private final KeyManager keyManager;
    private final JwtService jwtService;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @SneakyThrows
    public AccessToken generateAccessToken(Client client, User user, Set<String> scope) {
        var iat = System.currentTimeMillis();
        var exp = iat + TimeUnit.SECONDS.toMillis(client.getAccessTokenTtl());
        var jti = idGenerator.generate();

        var rsaPrivateKey = keyManager.getRsaPrivateKey();

        var claims = JwtClaims.builder()
                .iss(oauth2Config.getIssuer())
                .exp(exp)
                .iat(iat)
                .aud(client.getAudienceUris())
                .sub(user.getId())
                .clientId(client.getClientId())
                .jti(jti)
                .scope(scope)
                .build();

        var jws = JsonWebSignature.builder()
                .claims(claims)
                .key(rsaPrivateKey)
                .algorithm(SignatureAlgorithm.RS256)
                .keyId(oauth2Config.getKeyId())
                .build();

        var token = jwtService.sign(jws);
        var issuedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(iat), ZoneId.systemDefault());

        var accessToken = AccessToken.builder()
                .id(jti)
                .value(token)
                .expiresIn(client.getAccessTokenTtl())
                .issuedAt(issuedAt)
                .userId(user.getId())
                .build();

        return accessTokenRepository.save(accessToken);
    }

    public RefreshToken generateRefreshToken(Client client, User user) {
        var issuedAt = System.currentTimeMillis();
        long refreshTokenTtl = TimeUnit.SECONDS.toMillis(client.getRefreshTokenTtl());
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
}
