package com.example.oauth2infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.oauth2infra.jpa.repository.JpaQueryBuilderClientRepository;
import com.example.oauth2infra.security.BcryptHash;
import com.example.oauth2infra.util.NanoIdGenerator;
import com.example.oauth2infra.util.PassayPasswordGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauth2core.application.usecase.RegisterClientUseCase;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ServiceContainer {

    private final JpaQueryBuilderClientRepository clientRepository;
    private final NanoIdGenerator nanoIdGenerator;
    private final BcryptHash bcryptHash;
    private final PassayPasswordGenerator passayPasswordGenerator;
    
    @Bean
    public RegisterClientUseCase registerClientUseCase() {
        return new RegisterClientUseCase(clientRepository, nanoIdGenerator, bcryptHash, passayPasswordGenerator);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
