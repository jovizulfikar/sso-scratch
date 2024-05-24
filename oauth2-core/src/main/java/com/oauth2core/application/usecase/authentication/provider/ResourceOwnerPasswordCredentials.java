package com.oauth2core.application.usecase.authentication.provider;

import com.oauth2core.application.service.TokenManager;
import com.oauth2core.application.usecase.authentication.AccessTokenRequest;
import com.oauth2core.application.usecase.authentication.AccessTokenResponse;
import com.oauth2core.common.exception.AppException;
import com.oauth2core.domain.oauth2.AuthorizationGrantType;
import com.oauth2core.domain.oauth2.TokenType;
import com.oauth2core.port.repository.ClientRepository;
import com.oauth2core.port.repository.UserRepository;
import com.oauth2core.port.security.Hashing;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ResourceOwnerPasswordCredentials implements AuthenticationProvider {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final TokenManager tokenManager;
    private final Hashing passwordHash;

    @Override
    @SneakyThrows
    public AccessTokenResponse authenticate(AccessTokenRequest accessTokenRequest) {
        var clientId = accessTokenRequest.getClientId();

        // fetch client id
        var client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> AppException.build(ERROR_UNKNOWN_CLIENT));

        // verify client grants
        if (!client.getGrantTypes().contains(AuthorizationGrantType.PASSWORD.getGranType())) {
            throw AppException.build(ERROR_UNAUTHORIZED_CLIENT);
        }

        // verify user pass
        var user = userRepository.findByUsername(accessTokenRequest.getUsername())
                .orElseThrow(() -> AppException.build(ERROR_INVALID_RESOURCE_OWNER_CREDENTIALS));

        if (!passwordHash.verify(accessTokenRequest.getPassword(), user.getPassword())) {
            throw AppException.build(ERROR_INVALID_RESOURCE_OWNER_CREDENTIALS);
        }

        var scopes = Arrays.stream(accessTokenRequest.getScope().split("\\s+"))
                .filter(s -> client.getApiScopes().stream()
                    .anyMatch(apiScope -> apiScope.getName().equalsIgnoreCase(s)))
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
