package com.example.oauth2rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.oauth2infra", "com.example.oauth2rest"})
@EntityScan(basePackages = {"com.example.oauth2infra"})
public class Oauth2RestApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2RestApplication.class, args);
	}

}
