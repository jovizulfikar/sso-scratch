package com.example.oauth2.core.application.usecase.oidc;

import com.example.oauth2.core.application.config.OAuth2Config;
import lombok.*;

@RequiredArgsConstructor
public class GetOpenidConfigurationUseCase {

    private final OAuth2Config oAuth2Config;

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String jwksUri;
    }

    public Response getOpenidConfiguration() {
        return Response.builder()
                .jwksUri(oAuth2Config.getJwksUri())
                .build();
    }
}
