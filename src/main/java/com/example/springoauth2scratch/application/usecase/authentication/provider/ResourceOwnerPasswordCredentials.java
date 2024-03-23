package com.example.springoauth2scratch.application.usecase.authentication.provider;

import com.example.springoauth2scratch.application.service.TokenManager;
import com.example.springoauth2scratch.application.usecase.authentication.AccessTokenRequest;
import com.example.springoauth2scratch.application.usecase.authentication.AccessTokenResponse;
import com.example.springoauth2scratch.common.exception.AppException;
import com.example.springoauth2scratch.common.util.Base64;
import com.example.springoauth2scratch.domain.oauth2.AuthorizationGrantType;
import com.example.springoauth2scratch.domain.oauth2.TokenType;
import com.example.springoauth2scratch.port.CanHash;
import com.example.springoauth2scratch.port.repository.ClientRepository;
import com.example.springoauth2scratch.port.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ResourceOwnerPasswordCredentials implements AuthenticationProvider {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final TokenManager tokenManager;
    private final CanHash clientSecretHash;
    private final CanHash passwordHash;

    @Override
    @SneakyThrows
    public AccessTokenResponse authenticate(AccessTokenRequest accessTokenRequest) {
        var clientId = Base64.decode(accessTokenRequest.getClientId());
        var clientSecret = Base64.decode(accessTokenRequest.getClientSecret());

        // verify client id
        var client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> AppException.build(ERROR_UNKNOWN_CLIENT));

        // verify client secret
        client.getSecrets().stream()
                .filter(s -> clientSecretHash.verify(clientSecret, s))
                .findFirst()
                .orElseThrow(() -> AppException.build(ERROR_UNKNOWN_CLIENT));

        // verify client grants
        if (!client.getAuthorizationGrantTypes().contains(AuthorizationGrantType.PASSWORD.getGranType())) {
            throw AppException.build(ERROR_UNAUTHORIZED_CLIENT);
        }

        // verify user pass
        var user = userRepository.findByUsername(accessTokenRequest.getUsername())
                .orElseThrow(() -> AppException.build(ERROR_INVALID_RESOURCE_OWNER_CREDENTIALS));

        if (!passwordHash.verify(accessTokenRequest.getPassword(), user.getPassword())) {
            throw AppException.build(ERROR_INVALID_RESOURCE_OWNER_CREDENTIALS);
        }

        var scopes = Arrays.stream(accessTokenRequest.getScope().split("\\s+"))
                .filter(s -> client.getScopes().contains(s))
                .collect(Collectors.toSet());

        var accessToken = tokenManager.generateAccessToken(client, user, scopes);
        var refreshToken = tokenManager.generateRefreshToken(client, user);

        return AccessTokenResponse.builder()
                .accessToken(accessToken.getValue())
                .expiresIn(accessToken.getExpiresIn())
                .tokenType(TokenType.BEARER.getType())
                .refreshToken(refreshToken.getValue())
                .build();
    }
}
