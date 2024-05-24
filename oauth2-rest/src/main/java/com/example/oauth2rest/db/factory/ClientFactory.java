package com.example.oauth2service.clirunner.db.factory;

import com.example.oauth2service.domain.entity.Client;
import com.example.oauth2service.domain.oauth2.AuthorizationGrantType;
import com.example.oauth2service.port.util.IdGenerator;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ClientFactory {

    private final IdGenerator idGenerator;

    public Client make() {
        Faker faker = new Faker();
        return Client.builder()
                .id(idGenerator.generate())
                .clientId(faker.app().name().replaceAll("[^a-zA-Z0-9]", ""))
                .name(faker.company().name())
                .secrets(new HashSet<>(List.of(faker.internet().password())))
                .grantTypes(new HashSet<>(List.of(AuthorizationGrantType.PASSWORD.name())))
                .accessTokenTtl(TimeUnit.HOURS.toSeconds(1))
                .accessTokenTtl(TimeUnit.DAYS.toSeconds(365))
                .build();
    }
}
