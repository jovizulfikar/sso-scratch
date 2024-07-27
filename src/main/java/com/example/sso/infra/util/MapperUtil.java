package com.example.sso.infra.util;

import com.example.sso.core.domain.entity.ApiScope;
import com.example.sso.core.domain.entity.Client;
import com.example.sso.core.domain.entity.RefreshToken;
import com.example.sso.core.domain.entity.User;
import com.example.sso.core.domain.oauth2.JwtClaims;
import com.example.sso.infra.jpa.entity.JpaApiScope;
import com.example.sso.infra.jpa.entity.JpaClient;
import com.example.sso.infra.jpa.entity.JpaRefreshToken;
import com.example.sso.infra.jpa.entity.JpaUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperUtil {

    ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @SneakyThrows
    default String toJson(JwtClaims o) {
        return OBJECT_MAPPER.writeValueAsString(o);
    }

    @SneakyThrows
    default <T> T fromJson(String json, TypeReference<T> typeReference) {
        return OBJECT_MAPPER.readValue(json, typeReference);
    }

    Client client(JpaClient jpaClient);
    User user(JpaUser jpaUser);
    JpaClient jpaClient(Client client);
    JpaUser jpaUser(User user);
    JpaRefreshToken jpaRefreshToken(RefreshToken refreshToken);
    ApiScope apiScope(JpaApiScope jpaApiScope);
    JpaApiScope jpaApiScope(ApiScope apiScope);
    RefreshToken refreshToken(JpaRefreshToken jpaRefreshToken);
}
