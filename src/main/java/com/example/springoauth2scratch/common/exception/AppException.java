package com.example.springoauth2scratch.common.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class AppException extends Exception {

    private final String type;
    private final Map<String, Object> data = new HashMap<>();

    public AppException(String type) {
        super(type);
        this.type = type;
    }

    public static AppException build(String type) {
        return new AppException(type);
    }

    public AppException put(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public Object get(String key) {
        return data.get(key);
    }
}
