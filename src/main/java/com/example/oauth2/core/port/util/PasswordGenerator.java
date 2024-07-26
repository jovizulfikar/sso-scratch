package com.example.oauth2.core.port.util;

public interface PasswordGenerator {
    String generate();
    String generate(Integer length);
}
