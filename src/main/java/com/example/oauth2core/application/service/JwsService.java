package com.example.oauth2core.application.service;

import com.example.oauth2core.application.config.OAuth2Config;
import com.example.oauth2core.domain.entity.Client;
import com.example.oauth2core.domain.entity.User;
import com.example.oauth2core.domain.jose.JsonWebSignature;
import com.example.oauth2core.domain.jose.SignatureAlgorithm;
import com.example.oauth2core.domain.oauth2.JwtClaims;
import com.example.oauth2core.port.util.IdGenerator;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class JwsService {
    
    private final OAuth2Config oauth2Config;
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
                .iss(oauth2Config.getIssuer())
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
                .keyId(oauth2Config.getKeyId())
                .build();
    }

    public JsonWebSignature generateJws(Client client, Set<String> scopes) {
        return generateJws(client, null, scopes);
    }
}
