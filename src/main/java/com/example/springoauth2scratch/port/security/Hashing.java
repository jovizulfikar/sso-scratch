package com.example.springoauth2scratch.port.security;

public interface Hash {
    String hash(String src);
    boolean verify(String src, String hash);
}
