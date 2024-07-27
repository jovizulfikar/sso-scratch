package com.example.sso.infra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("app")
@Setter
@Getter
public class AppConfig implements com.example.sso.core.application.config.AppConfig {
    private List<String> uris = new ArrayList<>();
}
