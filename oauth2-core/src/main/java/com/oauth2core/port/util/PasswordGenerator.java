package com.oauth2core.port.util;

public interface PasswordGenerator {
    String generate();
    String generate(Integer length);
}
