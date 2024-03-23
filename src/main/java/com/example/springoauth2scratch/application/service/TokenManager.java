package com.example.springoauth2scratch.application.service;

import com.example.springoauth2scratch.application.config.OAuth2Config;
import com.example.springoauth2scratch.common.util.Base64;
import com.example.springoauth2scratch.domain.entity.Client;
import com.example.springoauth2scratch.domain.entity.User;
import com.example.springoauth2scratch.domain.jose.JsonWebSignature;
import com.example.springoauth2scratch.domain.jose.JwtClaims;
import com.example.springoauth2scratch.domain.jose.SignatureAlgorithm;
import com.example.springoauth2scratch.domain.oauth2.AccessToken;
import com.example.springoauth2scratch.domain.oauth2.RefreshToken;
import com.example.springoauth2scratch.port.IdGenerator;
import com.example.springoauth2scratch.port.JwtService;
import com.example.springoauth2scratch.port.repository.AccessTokenRepository;
import com.example.springoauth2scratch.port.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@RequiredArgsConstructor
public class TokenManager {

    private final OAuth2Config oauth2Config;
    private final IdGenerator jtiGenerator;
    private final KeyManager keyManager;
    private final JwtService jwtService;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final IdGenerator refreshTokenGenerator;

    @SneakyThrows
    public AccessToken generateAccessToken(Client client, User user, Set<String> scope) {
        var iat = System.currentTimeMillis();
        var exp = iat + (client.getAccessTokenLifetime() * 1000);
        var jti = jtiGenerator.generate();

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
                .build();

        var token = jwtService.sign(jws);
        var issuedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(iat), ZoneId.systemDefault());

        var accessToken = AccessToken.builder()
                .value(token)
                .expiresIn(client.getAccessTokenLifetime())
                .issuedAt(issuedAt)
                .userId(user.getId())
                .build();

        return accessTokenRepository.save(accessToken);
    }

    public RefreshToken generateRefreshToken(Client client, User user) {
        var issuedAt = System.currentTimeMillis();
        long refreshTokenLifetime = client.getRefreshTokenLifetime() < 1 ? 1000L : client.getRefreshTokenLifetime() * 1000;
        var expiredAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(issuedAt + refreshTokenLifetime), ZoneId.systemDefault());

        var refreshToken = RefreshToken.builder()
                .value(refreshTokenGenerator.generate())
                .expiredAt(expiredAt)
                .userId(user.getId())
                .clientId(client.getId())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }
}
