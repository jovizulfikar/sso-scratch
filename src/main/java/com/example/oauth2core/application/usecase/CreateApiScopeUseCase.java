package com.example.oauth2core.application.usecase;

import com.example.oauth2core.domain.entity.ApiScope;
import com.example.oauth2core.port.repository.ApiScopeRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class CreateApiScopeUseCase {

    private final ApiScopeRepository apiScopeRepository;

    @Builder
    @Getter
    public static class Command {
        private String name;
    }

    public void createApiScope(Command command) {
        var apiScope = apiScopeRepository.findByName(command.name).orElse(null);

        if (Objects.nonNull(apiScope)) {
            return;
        }

        apiScope = ApiScope.builder()
                .name(command.name)
                .build();
        
        apiScopeRepository.save(apiScope);
    }
}
