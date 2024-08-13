package com.example.sso.infra.security;

import com.example.sso.core.application.config.AppConfig;
import com.example.sso.core.domain.jose.JsonWebSignature;
import com.example.sso.core.domain.jose.SignatureAlgorithm;
import com.example.sso.core.domain.oauth2.JwtClaims;
import com.example.sso.core.port.security.JwtService;
import com.example.sso.infra.util.MapperUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class BitbucketJoseJwtService implements JwtService {

    private final MapperUtil mapper;
    private final AppConfig appConfig;

    @Override
    @SneakyThrows
    public String sign(JsonWebSignature jws) {

        var joseJws = new org.jose4j.jws.JsonWebSignature();
        var claims = mapper.toJson(jws.getClaims());

        joseJws.setPayload(claims);
        joseJws.setKey(jws.getKey());
        joseJws.setKeyIdHeaderValue(jws.getKeyId());
        joseJws.setAlgorithmHeaderValue(jws.getAlgorithm().name());
        return joseJws.getCompactSerialization();
    }

    @Override
    @SneakyThrows
    public JwtClaims verify(String jwt, Key jwsPublicKey) {
        var jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setVerificationKey(jwsPublicKey)
                .setExpectedAudience(appConfig.getUris().toArray(new String[0]))
                .setJwsAlgorithmConstraints(
                        AlgorithmConstraints.ConstraintType.PERMIT,
                        Arrays.stream(SignatureAlgorithm.values()).map(Enum::name)
                                .toArray(String[]::new))
                .build();

        var jwtClaims = jwtConsumer.processToClaims(jwt);
        return mapper.fromJson(jwtClaims.getRawJson(), new TypeReference<>() {});
    }

    @Override
    @SneakyThrows
    public JwtClaims getClaims(String jwt, Key jwsPublicKey) {
        var jwtConsumer = new JwtConsumerBuilder()
                .setVerificationKey(jwsPublicKey)
                .build();

        var jwtClaims = jwtConsumer.processToClaims(jwt);
        return mapper.fromJson(jwtClaims.getRawJson(), new TypeReference<>() {});
    }
}
