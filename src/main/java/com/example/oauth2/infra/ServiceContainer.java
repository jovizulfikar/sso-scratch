package com.example.oauth2.infra;

import com.example.oauth2.core.application.config.OAuth2Config;
import com.example.oauth2.core.application.service.JwsService;
import com.example.oauth2.core.application.service.KeyManager;
import com.example.oauth2.core.application.service.RefreshTokenService;
import com.example.oauth2.core.application.usecase.apiscope.CreateApiScopeUseCase;
import com.example.oauth2.core.application.usecase.oidc.GetJwksUseCase;
import com.example.oauth2.core.application.usecase.client.RegisterClientUseCase;
import com.example.oauth2.core.application.usecase.oidc.GetOpenidConfigurationUseCase;
import com.example.oauth2.core.application.usecase.user.RegisterUserUseCase;
import com.example.oauth2.core.application.usecase.authentication.provider.AuthenticationProviderFactory;
import com.example.oauth2.core.application.usecase.authentication.provider.ClientCredentials;
import com.example.oauth2.core.application.usecase.authentication.provider.RefreshToken;
import com.example.oauth2.core.application.usecase.authentication.provider.ResourceOwnerPasswordCredentials;
import com.example.oauth2.core.port.repository.ApiScopeRepository;
import com.example.oauth2.core.port.repository.ClientRepository;
import com.example.oauth2.core.port.repository.RefreshTokenRepository;
import com.example.oauth2.core.port.repository.UserRepository;
import com.example.oauth2.core.port.security.Hashing;
import com.example.oauth2.core.port.security.JwtService;
import com.example.oauth2.core.port.util.IdGenerator;
import com.example.oauth2.core.port.util.PasswordGenerator;
import com.example.oauth2.infra.config.AppOAuth2Config;
import com.example.oauth2.infra.jpa.repository.JpaQueryBuilderApiScopeRepository;
import com.example.oauth2.infra.jpa.repository.JpaQueryBuilderClientRepository;
import com.example.oauth2.infra.jpa.repository.JpaQueryBuilderRefreshTokenRepository;
import com.example.oauth2.infra.jpa.repository.JpaQueryBuilderUserRepository;
import com.example.oauth2.infra.security.BcryptHash;
import com.example.oauth2.infra.security.BitbucketJoseJwtService;
import com.example.oauth2.infra.util.NanoIdGenerator;
import com.example.oauth2.infra.util.PassayPasswordGenerator;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

@Configuration
@RequiredArgsConstructor
public class ServiceContainer {

    @Bean
    public OAuth2Config oAuth2Config(AppOAuth2Config appOAuth2Config) {
        return  appOAuth2Config;
    }

    @Bean
    public ApiScopeRepository apiScopeRepository(JpaQueryBuilderApiScopeRepository jpaApiScopeRepository) {
        return jpaApiScopeRepository;
    }

    @Bean
    public RefreshTokenRepository refreshTokenRepository(JpaQueryBuilderRefreshTokenRepository jpaQueryBuilderRefreshTokenRepository) {
        return jpaQueryBuilderRefreshTokenRepository;
    }

    @Bean
    public UserRepository userRepository(JpaQueryBuilderUserRepository jpaQueryBuilderUserRepository) {
        return jpaQueryBuilderUserRepository;
    }

    @Bean
    public ClientRepository clientRepository(JpaQueryBuilderClientRepository jpaQueryBuilderClientRepository) {
        return jpaQueryBuilderClientRepository;
    }

    @Bean
    public JwtService jwtService(BitbucketJoseJwtService bitbucketJoseJwtService) {
        return bitbucketJoseJwtService;
    }

    @Bean
    public PasswordGenerator passwordGenerator(PassayPasswordGenerator passayPasswordGenerator) {
        return passayPasswordGenerator;
    }

    @Bean
    public IdGenerator idGenerator(NanoIdGenerator nanoIdGenerator) {
        return nanoIdGenerator;
    }

    @Bean
    public Hashing hashing(BcryptHash bcryptHash) {
        return bcryptHash;
    }
    
    @Bean
    public RegisterClientUseCase registerClientUseCase(
            ClientRepository clientRepository,
            IdGenerator idGenerator,
            Hashing hashing,
            PasswordGenerator passwordGenerator
    ) {
        return new RegisterClientUseCase(clientRepository, idGenerator, hashing, passwordGenerator);
    }

    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        return objectMapper;
    }

    @Bean
    public ObjectMapper snakeCaseObjectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return objectMapper;
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserRepository userRepository, Hashing hashing) {
        return new RegisterUserUseCase(userRepository, hashing);
    }

    @Bean
    public ClientCredentials clientCredentials(
            ClientRepository clientRepository,
            Hashing hashing,
            JwsService jwsService,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        return new ClientCredentials(clientRepository, hashing, jwsService, jwtService, refreshTokenService);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public KeyManager keyManager(OAuth2Config oAuth2Config) {
        return new KeyManager(oAuth2Config);
    }

    @Bean
    public JwsService jwsService(OAuth2Config oAuth2Config, IdGenerator idGenerator, KeyManager keyManager) {
        return new JwsService(oAuth2Config, idGenerator, keyManager);
    }

    @Bean
    public RefreshTokenService refreshTokenService(IdGenerator idGenerator, RefreshTokenRepository refreshTokenRepository) {
        return new RefreshTokenService(idGenerator, refreshTokenRepository);
    }

    @Bean
    public RefreshToken refreshToken(
            ClientRepository clientRepository,
            Hashing hashing,
            RefreshTokenRepository refreshTokenRepository,
            JwsService jwsService,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        return new RefreshToken(clientRepository, hashing, refreshTokenRepository, jwsService, jwtService, refreshTokenService);
    }

    @Bean
    public AuthenticationProviderFactory authenticationProviderFactory(
            ResourceOwnerPasswordCredentials resourceOwnerPasswordCredentials,
            ClientCredentials clientCredentials,
            RefreshToken refreshToken
    ) {
        return new AuthenticationProviderFactory(resourceOwnerPasswordCredentials, clientCredentials, refreshToken);
    }

    @Bean
    public ResourceOwnerPasswordCredentials resourceOwnerPasswordCredentials(
            ClientRepository clientRepository,
            UserRepository userRepository,
            Hashing hashing,
            JwsService jwsService,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        return new ResourceOwnerPasswordCredentials(clientRepository, userRepository, hashing, jwsService, jwtService, refreshTokenService);
    }

    @Bean
    public CreateApiScopeUseCase createApiScopeUseCase(ApiScopeRepository apiScopeRepository) {
        return new CreateApiScopeUseCase(apiScopeRepository);
    }

    @Bean
    public GetJwksUseCase getJwksUseCase(KeyManager keyManager, OAuth2Config oAuth2Config) {
        return new GetJwksUseCase(keyManager, oAuth2Config);
    }

    @Bean
    public GetOpenidConfigurationUseCase getOpenidConfigurationUseCase(OAuth2Config oAuth2Config) {
        return new GetOpenidConfigurationUseCase(oAuth2Config);
    }
}
