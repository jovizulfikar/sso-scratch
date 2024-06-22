package com.example.oauth2rest.controller.v1;

import com.example.oauth2core.application.usecase.authentication.AccessTokenRequest;
import com.example.oauth2core.application.usecase.authentication.AccessTokenResponse;
import com.example.oauth2core.application.usecase.authentication.provider.AuthenticationProviderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
public class TokenController {

    private final AuthenticationProviderFactory authenticationProviderFactory;
    
    @PostMapping
    public ResponseEntity<AccessTokenResponse> postToken(@RequestBody AccessTokenRequest accessTokenRequest) {
        var response = authenticationProviderFactory.getByGrantType(accessTokenRequest.getGrantType())
                .authenticate(accessTokenRequest);
        return ResponseEntity.ok(response);
    }
}
