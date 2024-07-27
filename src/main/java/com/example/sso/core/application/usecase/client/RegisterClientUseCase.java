package com.example.sso.core.application.usecase.client;

import com.example.sso.core.application.validation.Validator;
import com.example.sso.core.application.validation.constraint.NotNull;
import com.example.sso.core.common.exception.ValidationException;
import com.example.sso.core.domain.entity.Client;
import com.example.sso.core.domain.entity.ClientSecret;
import com.example.sso.core.domain.oauth2.AuthorizationGrantType;
import com.example.sso.core.port.repository.ClientRepository;
import com.example.sso.core.port.security.Hashing;
import com.example.sso.core.port.util.IdGenerator;
import com.example.sso.core.port.util.PasswordGenerator;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RequiredArgsConstructor
public class RegisterClientUseCase {

    private final ClientRepository clientRepository;
    private final IdGenerator idGenerator;
    private final Hashing passwordHash;
    private final PasswordGenerator passwordGenerator;

    public static final String ERROR_CLIENT_ID_VALIDATION_NOT_NULL = "REGISTER_CLIENT_USE_CASE.CLIENT_ID_VALIDATION_NOT_NULL";
    public static final String ERROR_CLIENT_NAME_VALIDATION_NOT_NULL = "REGISTER_CLIENT_USE_CASE.CLIENT_NAME_VALIDATION_NOT_NULL";

    @Setter
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
        validate(request);

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

    @SneakyThrows
    private void validate(RegisterClientUseCase.Request request) {
        var clientIdErrors = Validator.build()
                .addConstraint(new NotNull(ERROR_CLIENT_ID_VALIDATION_NOT_NULL))
                .validate(request.getClientId())
                .values();

        var clientNameErrors = Validator.build()
                .addConstraint(new NotNull(ERROR_CLIENT_NAME_VALIDATION_NOT_NULL))
                .validate(request.getClientName())
                .values();

        if (clientIdErrors.isEmpty() && clientNameErrors.isEmpty()) {
            return;
        }

        Map<String, Set<String>> errors = new HashMap<>();
        errors.put("username", new HashSet<>(clientIdErrors));
        errors.put("password", new HashSet<>(clientNameErrors));
        throw new ValidationException(errors);
    }
}
