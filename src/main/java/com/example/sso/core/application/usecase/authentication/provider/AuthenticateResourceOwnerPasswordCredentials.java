package com.example.sso.core.application.usecase.authentication.provider;

import com.example.sso.core.common.exception.AppException;
import com.example.sso.core.domain.oauth2.AuthorizationGrantType;
import com.example.sso.core.domain.oauth2.TokenType;
import com.example.sso.core.application.service.JwsService;
import com.example.sso.core.application.service.RefreshTokenService;
import com.example.sso.core.application.usecase.authentication.AccessTokenRequest;
import com.example.sso.core.application.usecase.authentication.AccessTokenResponse;
import com.example.sso.core.port.repository.ClientRepository;
import com.example.sso.core.port.repository.UserRepository;
import com.example.sso.core.port.security.Hashing;
import com.example.sso.core.port.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AuthenticateResourceOwnerPasswordCredentials implements AuthenticationProvider {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final Hashing passwordHash;
    private final JwsService jwsService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    @SneakyThrows
    public AccessTokenResponse authenticate(AccessTokenRequest accessTokenRequest) {
        var clientId = accessTokenRequest.getClientId();

        // fetch client id
        var client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> AppException.build(ERROR_UNKNOWN_CLIENT));
        
        // verify client secret
        client.getSecrets().stream()
                .filter(secret -> passwordHash.verify(accessTokenRequest.getClientSecret(), secret.getSecret()))
                .findFirst().orElseThrow(() -> AppException.build(ERROR_INVALID_CLIENT_SECRET));

        // verify client grants
        if (!client.getGrantTypes().contains(AuthorizationGrantType.PASSWORD.getGranType())) {
            throw AppException.build(ERROR_UNAUTHORIZED_AUTH_FLOW);
        }

        // verify user pass
        var user = userRepository.findByUsername(accessTokenRequest.getUsername())
                .orElseThrow(() -> AppException.build(ERROR_INVALID_RESOURCE_OWNER_CREDENTIALS));

        if (!passwordHash.verify(accessTokenRequest.getPassword(), user.getPassword())) {
            throw AppException.build(ERROR_INVALID_RESOURCE_OWNER_CREDENTIALS);
        }

        var scopes = client.getApiScopes().stream()
                .map(scope -> scope.getName())
                .collect(Collectors.toSet());

        var jws = jwsService.generateJws(client, user, scopes);
        var accessToken = jwtService.sign(jws);
        var refreshToken = refreshTokenService.generateRefreshToken(client, user);

        return AccessTokenResponse.builder()
                .accessToken(accessToken)
                .expiresIn(client.getAccessTokenTtl())
                .tokenType(TokenType.BEARER.getType())
                .refreshToken(refreshToken.getValue())
                .build();
    }
}
