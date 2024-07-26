package com.example.oauth2.rest.controller.openid;

import com.example.oauth2.core.application.usecase.oidc.GetJwksUseCase;
import com.example.oauth2.core.application.usecase.oidc.GetOpenidConfigurationUseCase;
import com.example.oauth2.core.domain.jose.JsonWebKeySet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/.well-known/openid-configuration")
@RequiredArgsConstructor
public class OpenidConfigurationController {

    private final GetJwksUseCase getJwksUseCase;
    private final GetOpenidConfigurationUseCase getOpenidConfigurationUseCase;
    private final ObjectMapper mapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    public ResponseEntity<String> getOpenidConfiguration() {
        var response = getOpenidConfigurationUseCase.getOpenidConfiguration();
        return ResponseEntity.ok(mapper.writeValueAsString(response));
    }

    @GetMapping("/jwks")
    public ResponseEntity<JsonWebKeySet> getJwks() {
        var response = getJwksUseCase.getJwks();
        return ResponseEntity.ok(response);
    }

}
