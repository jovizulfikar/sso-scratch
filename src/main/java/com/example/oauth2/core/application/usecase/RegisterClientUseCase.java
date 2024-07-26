package com.example.oauth2.core.application.usecase;

import com.example.oauth2.core.domain.entity.Client;
import com.example.oauth2.core.domain.entity.ClientSecret;
import com.example.oauth2.core.domain.oauth2.AuthorizationGrantType;
import com.example.oauth2.core.port.repository.ClientRepository;
import com.example.oauth2.core.port.security.Hashing;
import com.example.oauth2.core.port.util.IdGenerator;
import com.example.oauth2.core.port.util.PasswordGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashSet;

@RequiredArgsConstructor
public class RegisterClientUseCase {

    private final ClientRepository clientRepository;
    private final IdGenerator idGenerator;
    private final Hashing passwordHash;
    private final PasswordGenerator passwordGenerator;

    @Builder
    @Getter
    public static class Request {
        private String clientId;
        private String clientName;
    }

    @Builder
    @Getter
    public static class Response {
        private String clientId;
        private String clientName;
        private Long clientIssuedAt;
        private String clientSecret;
        private Long clientSecretExpiresAt;
    }

    public Response registerClient(Request request) {
        var password = passwordGenerator.generate(32);

        var clientId = request.clientId + "-" + idGenerator.generate(6).toLowerCase();

        var clientSecret = ClientSecret.builder()
            .secret(passwordHash.hash(password))
            .issuedAt(LocalDateTime.now())
            .build();

        var client = Client.builder()
            .clientId(clientId)
            .name(request.clientName)
            .grantTypes(new HashSet<>(Collections.singletonList(AuthorizationGrantType.CLIENT_CREDENTIALS.getGranType())))
            .secrets(new HashSet<>(Collections.singletonList(clientSecret)))
            .issuedAt(LocalDateTime.now())
            .build();

        clientRepository.save(client);

        return Response.builder()
            .clientId(client.getClientId())
            .clientName(client.getName())
            .clientSecret(password)
            .clientIssuedAt(client.getIssuedAt().toEpochSecond(ZoneId.systemDefault().getRules().getOffset(client.getIssuedAt())))
            .build();
    }
}
