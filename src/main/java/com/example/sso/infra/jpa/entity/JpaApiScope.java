package com.example.sso.infra.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "api_scopes")
@Setter
@Getter
public class JpaApiScope {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", unique = true)
    private String name;
}
