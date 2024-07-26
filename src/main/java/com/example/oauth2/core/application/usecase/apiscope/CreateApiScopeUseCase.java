package com.example.oauth2.core.application.usecase.apiscope;

import com.example.oauth2.core.domain.entity.ApiScope;
import com.example.oauth2.core.port.repository.ApiScopeRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class CreateApiScopeUseCase {

    private final ApiScopeRepository apiScopeRepository;

    @Builder
    @Getter
    public static class Request {
        private String name;
    }

    public void createApiScope(Request request) {
        var apiScope = apiScopeRepository.findByName(request.name).orElse(null);

        if (Objects.nonNull(apiScope)) {
            return;
        }

        apiScope = ApiScope.builder()
                .name(request.name)
                .build();
        
        apiScopeRepository.save(apiScope);
    }
}
