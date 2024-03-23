package com.example.springoauth2scratch.application.service;

import com.example.springoauth2scratch.application.config.OAuth2Config;
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
import java.util.Objects;;

@RequiredArgsConstructor
public class KeyManager {

    private final OAuth2Config oauth2Config;
    private KeyPair keyPair;

    @SneakyThrows
    private KeyPair getKeyPair() {
        if (Objects.nonNull(keyPair)) {
            return keyPair;
        }

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        byte[] privateKeyBytes = Base64.getDecoder().decode(oauth2Config.getPrivateKey());
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        byte[] publicKeyBytes = Base64.getDecoder().decode(oauth2Config.getPublicKey());
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
}
