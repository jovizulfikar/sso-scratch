package com.example.springoauth2scratch.adapter.out.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name = "clients")
@NoArgsConstructor
@Getter
@Setter
public class JpaClient {
    @Id
    private String id;

    @ElementCollection
    @CollectionTable(name = "client_secrets", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "secret")
    private Set<String> secrets;

    @ElementCollection
    @CollectionTable(name = "client_grant_types", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "grant_type")
    private Set<String> grantTypes;

    @Column(name = "access_token_lifetime")
    private Long accessTokenLifetime;
}
