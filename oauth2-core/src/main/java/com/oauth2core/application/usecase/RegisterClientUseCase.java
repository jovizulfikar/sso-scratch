package com.oauth2core.application.usecase;

import com.oauth2core.domain.entity.Client;
import com.oauth2core.domain.entity.ClientSecret;
import com.oauth2core.domain.oauth2.AuthorizationGrantType;
import com.oauth2core.port.repository.ClientRepository;
import com.oauth2core.port.security.Hashing;
import com.oauth2core.port.util.IdGenerator;
import com.oauth2core.port.util.PasswordGenerator;
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
    public static class Command {
        private String clientId;
        private String clientName;
    }

    @Builder
    @Getter
    public static class Result {
        private String clientId;
        private String clientName;
        private Long clientIssuedAt;
        private String clientSecret;
        private Long clientSecretExpiresAt;
    }

    public Result registerClient(Command command) {
        var password = passwordGenerator.generate(32);

        var clientId = command.clientId + "-" + idGenerator.generate(6).toLowerCase();

        var clientSecret = ClientSecret.builder()
            .secret(passwordHash.hash(password))
            .issuedAt(LocalDateTime.now())
            .build();

        var client = Client.builder()
            .clientId(clientId)
            .name(command.clientName)
            .grantTypes(new HashSet<>(Collections.singletonList(AuthorizationGrantType.PASSWORD.getGranType())))
            .secrets(new HashSet<>(Collections.singletonList(clientSecret)))
            .issuedAt(LocalDateTime.now())
            .build();

        clientRepository.save(client);

        return Result.builder()
            .clientId(client.getClientId())
            .clientName(client.getName())
            .clientSecret(password)
            .clientIssuedAt(client.getIssuedAt().toEpochSecond(ZoneId.systemDefault().getRules().getOffset(client.getIssuedAt())))
            .build();
    }
}
