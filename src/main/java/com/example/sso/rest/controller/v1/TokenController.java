package com.example.sso.rest.controller.v1;

import com.example.sso.core.application.usecase.authentication.AccessTokenRequest;
import com.example.sso.core.application.usecase.authentication.AccessTokenResponse;
import com.example.sso.core.application.usecase.authentication.provider.AuthenticationProviderFactory;
import com.example.sso.core.application.usecase.revocation.RevokeTokenRequest;
import com.example.sso.core.application.usecase.revocation.RevokeTokenResponse;
import com.example.sso.core.application.usecase.revocation.provider.RevocationProviderFactory;
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
    private final RevocationProviderFactory revocationProviderFactory;
    
    @PostMapping
    public ResponseEntity<AccessTokenResponse> postToken(@RequestBody AccessTokenRequest accessTokenRequest) {
        var response = authenticationProviderFactory.getByGrantType(accessTokenRequest.getGrantType())
                .authenticate(accessTokenRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/revoke")
    public ResponseEntity<RevokeTokenResponse> revoke(@RequestBody RevokeTokenRequest revokeTokenRequest) {
        var revocationProvider = revocationProviderFactory.getByTokenType(revokeTokenRequest.getTokenTypeHint());
        var response = revocationProvider.revoke(revokeTokenRequest);
        return ResponseEntity.ok(response);
    }
}
