package com.example.oauth2.core.port.security;

public interface Hashing {
    String hash(String src);
    boolean verify(String src, String hash);
}
