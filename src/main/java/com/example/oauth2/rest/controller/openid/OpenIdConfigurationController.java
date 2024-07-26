package com.example.oauth2.rest.controller.openid;

import com.example.oauth2.core.application.usecase.GetJwksUseCase;
import com.example.oauth2.core.domain.jose.JsonWebKeySet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/.well-known/openid-configuration")
@RequiredArgsConstructor
public class OpenIdConfigurationController {

    private final GetJwksUseCase getJwksUseCase;

    @GetMapping("/jwks")
    public ResponseEntity<JsonWebKeySet> getJwks() {
        var response = getJwksUseCase.getJwks();
        return ResponseEntity.ok(response);
    }

}
