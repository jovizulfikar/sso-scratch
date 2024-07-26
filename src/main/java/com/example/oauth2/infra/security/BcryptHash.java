package com.example.oauth2.infra.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.oauth2.core.port.security.Hashing;

@Service
public class BcryptHash implements Hashing {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String hash(String src) {
        return encoder.encode(src);
    }

    @Override
    public boolean verify(String src, String hash) {
        return encoder.matches(src, hash);
    }
}
