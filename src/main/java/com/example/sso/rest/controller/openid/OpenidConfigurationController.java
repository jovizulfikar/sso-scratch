package com.example.sso.rest.controller.openid;

import com.example.sso.core.application.usecase.oidc.GetJwksUseCase;
import com.example.sso.core.application.usecase.oidc.GetOpenidConfigurationUseCase;
import com.example.sso.core.domain.jose.JsonWebKeySet;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/.well-known/openid-configuration")
@RequiredArgsConstructor
public class OpenidConfigurationController {

    private final GetJwksUseCase getJwksUseCase;
    private final GetOpenidConfigurationUseCase getOpenidConfigurationUseCase;
    private final ObjectMapper snakeCaseObjectMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    public ResponseEntity<String> getOpenidConfiguration() {
        var response = getOpenidConfigurationUseCase.getOpenidConfiguration();
        return ResponseEntity.ok(snakeCaseObjectMapper.writeValueAsString(response));
    }

    @GetMapping("/jwks")
    public ResponseEntity<JsonWebKeySet> getJwks() {
        var response = getJwksUseCase.getJwks();
        return ResponseEntity.ok(response);
    }

}
