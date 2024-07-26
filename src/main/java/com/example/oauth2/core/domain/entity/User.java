package com.example.oauth2.core.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
}
