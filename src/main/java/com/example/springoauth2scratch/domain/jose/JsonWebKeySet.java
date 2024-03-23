package com.example.springoauth2scratch.domain.jose;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class JsonWebKeySet {
    private List<JsonWebKey> keys;
}
