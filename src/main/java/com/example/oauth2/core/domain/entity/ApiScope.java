package com.example.oauth2.core.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ApiScope {
    private String id;
    private String name;
}
