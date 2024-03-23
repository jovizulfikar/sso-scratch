package com.example.springoauth2scratch.adapter.out.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@NoArgsConstructor
@Setter
@Getter
public class JpaUser {
    @Id
    private String id;

    private String password;
}
