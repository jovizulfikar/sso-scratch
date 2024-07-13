package com.example.oauth2core.application.usecase;

import com.example.oauth2core.application.config.OAuth2Config;
import com.example.oauth2core.application.service.KeyManager;
import com.example.oauth2core.common.util.Base64;
import com.example.oauth2core.domain.jose.JsonWebKey;
import com.example.oauth2core.domain.jose.JsonWebKeySet;
import com.example.oauth2core.domain.jose.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class GetJwksUseCase {

    private final KeyManager keyManager;
    private final OAuth2Config oAuth2Config;

    public JsonWebKeySet getJwks() {
        var publicKey = keyManager.getRsaPublicKey();

        var jwk = JsonWebKey.builder()
                .kty("RSA")
                .use("sig")
                .alg(SignatureAlgorithm.RS256.name())
                .kid(oAuth2Config.getKeyId())
                .e(Base64.encode(publicKey.getPublicExponent().toByteArray()))
                .n(Base64.encode(keyManager.getPublicKeyMagnitude()))
                .build();

        return JsonWebKeySet.builder()
            .keys(Arrays.asList(jwk))
            .build();
    }
}
