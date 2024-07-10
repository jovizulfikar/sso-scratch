package com.example.oauth2shell.command;

import com.example.oauth2core.application.usecase.RegisterClientUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@ShellCommandGroup("Client")
@RequiredArgsConstructor
public class ClientCommand {

    private final RegisterClientUseCase registerClientUseCase;
    private final ObjectMapper objectMapper;

    @ShellMethod(key = "client:register", value = "Register OAuth2 Client")
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
