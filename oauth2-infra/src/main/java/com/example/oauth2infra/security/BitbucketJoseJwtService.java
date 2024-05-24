package com.example.oauth2rest.security;

import com.example.oauth2rest.util.MapperUtil;
import com.oauth2core.domain.jose.JsonWebSignature;
import com.oauth2core.domain.jose.SignatureAlgorithm;
import com.oauth2core.domain.oauth2.JwtClaims;
import com.oauth2core.port.security.JwtService;
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
                .setRequireSubject()
                .setVerificationKey(jwsPublicKey)
                .setJwsAlgorithmConstraints(
                        AlgorithmConstraints.ConstraintType.PERMIT,
                        Arrays.stream(SignatureAlgorithm.values()).map(Enum::name)
                                .toArray(String[]::new))
                .build();

        var jwtClaims = jwtConsumer.processToClaims(jwt);
        return mapper.mapTo(jwtClaims.getClaimsMap());
    }
}
