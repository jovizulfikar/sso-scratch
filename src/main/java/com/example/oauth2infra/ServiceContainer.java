package com.example.oauth2infra;

import com.example.oauth2core.application.service.JwsService;
import com.example.oauth2core.application.service.KeyManager;
import com.example.oauth2core.application.service.RefreshTokenService;
import com.example.oauth2core.application.usecase.CreateApiScopeUseCase;
import com.example.oauth2core.application.usecase.GetJwksUseCase;
import com.example.oauth2core.application.usecase.RegisterClientUseCase;
import com.example.oauth2core.application.usecase.RegisterUserUseCase;
import com.example.oauth2core.application.usecase.authentication.provider.AuthenticationProviderFactory;
import com.example.oauth2core.application.usecase.authentication.provider.ClientCredentials;
import com.example.oauth2core.application.usecase.authentication.provider.RefreshToken;
import com.example.oauth2core.application.usecase.authentication.provider.ResourceOwnerPasswordCredentials;
import com.example.oauth2infra.config.OAuth2Config;
import com.example.oauth2infra.jpa.repository.JpaQueryBuilderApiScopeRepository;
import com.example.oauth2infra.jpa.repository.JpaQueryBuilderClientRepository;
import com.example.oauth2infra.jpa.repository.JpaQueryBuilderRefreshTokenRepository;
import com.example.oauth2infra.jpa.repository.JpaQueryBuilderUserRepository;
import com.example.oauth2infra.security.BcryptHash;
import com.example.oauth2infra.security.BitbucketJoseJwtService;
import com.example.oauth2infra.util.NanoIdGenerator;
import com.example.oauth2infra.util.PassayPasswordGenerator;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@RequiredArgsConstructor
public class ServiceContainer {

    private final OAuth2Config oAuth2Config;
    private final NanoIdGenerator nanoIdGenerator;
    private final BcryptHash bcryptHash;
    private final PassayPasswordGenerator passayPasswordGenerator;
    private final BitbucketJoseJwtService bitbucketJoseJwtService;
    private final JpaQueryBuilderClientRepository jpaQueryBuilderClientRepository;
    private final JpaQueryBuilderUserRepository jpaQueryBuilderUserRepository;
    private final JpaQueryBuilderRefreshTokenRepository jpaQueryBuilderRefreshTokenRepository;
    private final JpaQueryBuilderApiScopeRepository jpaApiScopeRepository;
    
    @Bean
    public RegisterClientUseCase registerClientUseCase() {
        return new RegisterClientUseCase(jpaQueryBuilderClientRepository, nanoIdGenerator, bcryptHash, passayPasswordGenerator);
    }

    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        return objectMapper;
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase() {
        return new RegisterUserUseCase(jpaQueryBuilderUserRepository, bcryptHash);
    }

    @Bean
    public ClientCredentials clientCredentials() {
        return new ClientCredentials(jpaQueryBuilderClientRepository, bcryptHash, jwsService(), bitbucketJoseJwtService, refreshTokenService());
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public KeyManager keyManager() {
        return new KeyManager(oAuth2Config);
    }

    @Bean
    public JwsService jwsService() {
        return new JwsService(oAuth2Config, nanoIdGenerator, keyManager());
    }

    @Bean
    public RefreshTokenService refreshTokenService() {
        return new RefreshTokenService(nanoIdGenerator, jpaQueryBuilderRefreshTokenRepository);
    }

    @Bean
    public RefreshToken refreshToken() {
        return new RefreshToken(jpaQueryBuilderClientRepository, bcryptHash, jpaQueryBuilderRefreshTokenRepository, jwsService(), bitbucketJoseJwtService, refreshTokenService());
    }

    @Bean
    public AuthenticationProviderFactory authenticationProviderFactory() {
        return new AuthenticationProviderFactory(resourceOwnerPasswordCredentials(), clientCredentials(), refreshToken());
    }

    @Bean
    public ResourceOwnerPasswordCredentials resourceOwnerPasswordCredentials() {
        return new ResourceOwnerPasswordCredentials(jpaQueryBuilderClientRepository, jpaQueryBuilderUserRepository, bcryptHash, jwsService(), bitbucketJoseJwtService, refreshTokenService());
    }

    @Bean
    public CreateApiScopeUseCase createApiScopeUseCase() {
        return new CreateApiScopeUseCase(jpaApiScopeRepository);
    }

    @Bean
    public GetJwksUseCase getJwksUseCase() {
        return new GetJwksUseCase(keyManager(), oAuth2Config);
    }
}
