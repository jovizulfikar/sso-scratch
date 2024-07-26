package com.example.oauth2.core.port.util;

public interface IdGenerator {
    String generate();
    String generate(Integer length);
}
