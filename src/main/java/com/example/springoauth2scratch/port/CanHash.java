package com.example.springoauth2scratch.port;

public interface CanHash {
    String hash(String src);
    boolean verify(String src, String hash);
}
