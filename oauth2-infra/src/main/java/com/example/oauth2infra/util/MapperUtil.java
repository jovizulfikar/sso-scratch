package com.example.oauth2rest.util;

import com.example.oauth2rest.jpa.entity.JpaClient;
import com.example.oauth2rest.jpa.entity.JpaUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauth2core.domain.entity.Client;
import com.oauth2core.domain.entity.User;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface MapperUtil {

    ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @SneakyThrows
    default String toJson(Object o) {
        return OBJECT_MAPPER.writeValueAsString(o);
    }

    default <T> T mapTo(Map<String, Object> map) {
        return OBJECT_MAPPER.convertValue(map, new TypeReference<>() {
        });
    }

    Client jpaClientToClient(JpaClient jpaClient);
    User jpaUserToUser(JpaUser jpaUser);
    JpaClient clientToJpaClient(Client client);
}
