package com.example.oauth2shell.command;

import com.example.oauth2core.application.usecase.CreateApiScopeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class CreateApiScopeCommand {

    private final CreateApiScopeUseCase createApiScopeUseCase;

    @ShellMethod(key = "create-api-scope", value = "Create API Scope")
    public void createApiScope(@ShellOption("--name") String name) {
        var command = CreateApiScopeUseCase.Command.builder()
                .name(name)
                .build();
        
        createApiScopeUseCase.createApiScope(command);
    }   
}
