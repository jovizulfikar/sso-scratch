package com.example.oauth2infra.util;

import com.example.oauth2core.domain.entity.ApiScope;
import com.example.oauth2core.domain.entity.Client;
import com.example.oauth2core.domain.entity.RefreshToken;
import com.example.oauth2core.domain.entity.User;
import com.example.oauth2core.domain.oauth2.JwtClaims;
import com.example.oauth2infra.jpa.entity.JpaApiScope;
import com.example.oauth2infra.jpa.entity.JpaClient;
import com.example.oauth2infra.jpa.entity.JpaRefreshToken;
import com.example.oauth2infra.jpa.entity.JpaUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import org.mapstruct.Mapper;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface MapperUtil {

    ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @SneakyThrows
    default String toJson(JwtClaims o) {
        return OBJECT_MAPPER.writeValueAsString(o);
    }

    default <T> T mapTo(Map<String, Object> map) {
        return OBJECT_MAPPER.convertValue(map, new TypeReference<>() {
        });
    }

    Client client(JpaClient jpaClient);
    User user(JpaUser jpaUser);
    JpaClient jpaClient(Client client);
    JpaUser jpaUser(User user);
    JpaRefreshToken jpaRefreshToken(RefreshToken refreshToken);
    ApiScope apiScope(JpaApiScope jpaApiScope);
    JpaApiScope jpaApiScope(ApiScope apiScope);
}
