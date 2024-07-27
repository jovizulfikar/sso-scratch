package com.example.sso.core.application.usecase.oidc;

import com.example.sso.core.application.config.SsoConfig;
import lombok.*;

@RequiredArgsConstructor
public class GetOpenidConfigurationUseCase {

    private final SsoConfig ssoConfig;

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String jwksUri;
    }

    public Response getOpenidConfiguration() {
        return Response.builder()
                .jwksUri(ssoConfig.getJwksUri())
                .build();
    }
}
