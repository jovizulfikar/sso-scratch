package com.example.sso.core.application.service;

import com.example.sso.core.application.config.SsoConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

@RequiredArgsConstructor
public class KeyManager {

    private final SsoConfig ssoConfig;
    private KeyPair keyPair;

    @SneakyThrows
    private KeyPair getKeyPair() {
        if (Objects.nonNull(keyPair)) {
            return keyPair;
        }

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        byte[] privateKeyBytes = Base64.getDecoder().decode(ssoConfig.getPrivateKey());
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        byte[] publicKeyBytes = Base64.getDecoder().decode(ssoConfig.getPublicKey());
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        keyPair = new KeyPair(publicKey, privateKey);
        return keyPair;
    }

    public RSAPublicKey getRsaPublicKey() {
        return (RSAPublicKey) getKeyPair().getPublic();
    }

    public RSAPrivateKey getRsaPrivateKey() {
        return (RSAPrivateKey) getKeyPair().getPrivate();
    }

    public byte[] getPublicKeyMagnitude() {
        var bigInteger = getRsaPublicKey().getModulus();
        byte[] twosComplementBytes = bigInteger.toByteArray();
        byte[] magnitude;

        if ((bigInteger.bitLength() % 8 == 0) && (twosComplementBytes[0] == 0) && twosComplementBytes.length > 1) {
            magnitude = new byte[twosComplementBytes.length - 1];
            System.arraycopy(twosComplementBytes, 1, magnitude, 0, twosComplementBytes.length - 1);
        } else {
            magnitude = twosComplementBytes;
        }

        return magnitude;
    }
}
