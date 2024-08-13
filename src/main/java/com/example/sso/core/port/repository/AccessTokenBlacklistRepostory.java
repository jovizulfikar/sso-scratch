package com.example.sso.core.port.repository;

public interface AccessTokenBlacklistRepostory {
    String save(String jwt);
}
