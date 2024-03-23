package com.example.springoauth2scratch.domain.entity;

import lombok.Data;

@Data
public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
}
