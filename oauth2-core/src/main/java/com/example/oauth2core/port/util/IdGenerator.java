package com.example.oauth2core.port.util;

public interface IdGenerator {
    String generate();
    String generate(Integer length);
}
