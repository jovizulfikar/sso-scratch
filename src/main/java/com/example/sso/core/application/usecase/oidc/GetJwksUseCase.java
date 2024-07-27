package com.example.sso.core.application.usecase.oidc;

import com.example.sso.core.application.config.SsoConfig;
import com.example.sso.core.application.service.KeyManager;
import com.example.sso.core.common.util.Base64;
import com.example.sso.core.domain.jose.JsonWebKey;
import com.example.sso.core.domain.jose.JsonWebKeySet;
import com.example.sso.core.domain.jose.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class GetJwksUseCase {

    private final KeyManager keyManager;
    private final SsoConfig ssoConfig;

    public JsonWebKeySet getJwks() {
        var publicKey = keyManager.getRsaPublicKey();

        var jwk = JsonWebKey.builder()
                .kty("RSA")
                .use("sig")
                .alg(SignatureAlgorithm.RS256.name())
                .kid(ssoConfig.getKeyId())
                .e(Base64.encode(publicKey.getPublicExponent().toByteArray()))
                .n(Base64.encode(keyManager.getPublicKeyMagnitude()))
                .build();

        return JsonWebKeySet.builder()
            .keys(Arrays.asList(jwk))
            .build();
    }
}
