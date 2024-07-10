package com.example.oauth2tools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.oauth2infra"})
public class OAuth2ToolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2ToolsApplication.class, args);
	}

}
