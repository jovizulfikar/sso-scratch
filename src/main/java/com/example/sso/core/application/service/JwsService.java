package com.example.sso.core.application.service;

import com.example.sso.core.application.config.SsoConfig;
import com.example.sso.core.domain.entity.Client;
import com.example.sso.core.domain.entity.User;
import com.example.sso.core.domain.jose.JsonWebSignature;
import com.example.sso.core.domain.jose.SignatureAlgorithm;
import com.example.sso.core.domain.oauth2.JwtClaims;
import com.example.sso.core.port.util.IdGenerator;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class JwsService {
    
    private final SsoConfig ssoConfig;
    private final IdGenerator idGenerator;
    private final KeyManager keyManager;

    public JsonWebSignature generateJws(Client client, User user, Set<String> scopes) {
        var userId = Optional.ofNullable(user).map(User::getId).orElse(null);
        var iat = System.currentTimeMillis();
        var exp = iat + TimeUnit.SECONDS.toMillis(client.getAccessTokenTtl());
        var jti = idGenerator.generate();
        var aud = !client.getAudienceUris().isEmpty() ? client.getAudienceUris() : null;

        var rsaPrivateKey = keyManager.getRsaPrivateKey();

        var claims = JwtClaims.builder()
                .iss(ssoConfig.getIssuer())
                .exp(exp)
                .iat(iat)
                .aud(aud)
                .sub(userId)
                .clientId(client.getClientId())
                .jti(jti)
                .scope(scopes)
                .build();

        return JsonWebSignature.builder()
                .claims(claims)
                .key(rsaPrivateKey)
                .algorithm(SignatureAlgorithm.RS256)
                .keyId(ssoConfig.getKeyId())
                .build();
    }

    public JsonWebSignature generateJws(Client client, Set<String> scopes) {
        return generateJws(client, null, scopes);
    }
}
