package com.example.sso.core.port.util;

public interface PasswordGenerator {
    String generate();
    String generate(Integer length);
}
