package com.example.oauth2shell.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauth2core.application.usecase.RegisterClientUseCase;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@ShellComponent
@RequiredArgsConstructor
public class RegisterClientCommand {
    
    private final RegisterClientUseCase registerClientUseCase;
    private final ObjectMapper objectMapper;

    @ShellMethod(key = "register-client", value = "Register OAuth2 Client")
    @SneakyThrows
    public String registerClient(
        @ShellOption("--client-id") String clientId, 
        @ShellOption("--client-name") String clientName
    ) {
        var command = RegisterClientUseCase.Command.builder()
            .clientId(clientId)
            .clientName(clientName)
            .build();

        var result = registerClientUseCase.registerClient(command);
        return objectMapper.writeValueAsString(result);
    }
}
