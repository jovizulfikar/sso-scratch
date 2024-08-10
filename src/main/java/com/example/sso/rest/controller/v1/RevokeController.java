package com.example.sso.rest.controller.v1;

import com.example.sso.core.application.usecase.revocation.RevokeTokenRequest;
import com.example.sso.core.application.usecase.revocation.provider.RevocationProviderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/revoke")
@RequiredArgsConstructor
public class RevokeController {

    private final RevocationProviderFactory revocationProviderFactory;

    @PostMapping
    public ResponseEntity<Void> revoke(RevokeTokenRequest revokeTokenRequest) {
        var revocationProvider = revocationProviderFactory.getByTokenType(revokeTokenRequest.getTokenTypeHint());
        revocationProvider.revoke(revokeTokenRequest);
        return ResponseEntity.ok().build();
    }
}
