package com.example.sso.core.application.usecase.authentication.provider;

import com.example.sso.core.application.service.ClientService;
import com.example.sso.core.application.service.JwsService;
import com.example.sso.core.application.service.RefreshTokenService;
import com.example.sso.core.application.usecase.authentication.AccessTokenRequest;
import com.example.sso.core.application.usecase.authentication.AccessTokenResponse;
import com.example.sso.core.common.exception.AppException;
import com.example.sso.core.domain.entity.ApiScope;
import com.example.sso.core.domain.oauth2.AuthorizationGrantType;
import com.example.sso.core.domain.oauth2.TokenType;
import com.example.sso.core.port.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AuthenticateClientCredentials implements AuthenticationProvider {

    private final JwsService jwsService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final ClientService clientService;
    
    @Override
    @SneakyThrows
    public AccessTokenResponse authenticate(AccessTokenRequest accessTokenRequest) {
        var client = clientService.authenticate(accessTokenRequest.getClientId(), accessTokenRequest.getClientSecret());

        // verify client grants
        if (!client.getGrantTypes().contains(AuthorizationGrantType.CLIENT_CREDENTIALS.getGranType())) {
            throw AppException.build(ERROR_UNAUTHORIZED_AUTH_FLOW);
        }

        // generate access token
        var scopes = client.getApiScopes().stream()
                .map(ApiScope::getName)
                .collect(Collectors.toSet());

        var jws = jwsService.generateJws(client, scopes);
        var accessToken = jwtService.sign(jws);
        var refreshToken = refreshTokenService.generateRefreshToken(client);

        return AccessTokenResponse.builder()
                .accessToken(accessToken)
                .expiresIn(client.getAccessTokenTtl())
                .tokenType(TokenType.BEARER.getType())
                .refreshToken(refreshToken.getValue())
                .build();
    }
}
