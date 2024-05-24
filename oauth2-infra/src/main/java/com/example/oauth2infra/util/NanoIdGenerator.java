package com.example.oauth2rest.util;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.oauth2core.port.util.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NanoIdGenerator implements IdGenerator {
    private static final char[] DEFAULT_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    @Override
    public String generate() {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, NanoIdUtils.DEFAULT_SIZE);
    }

    @Override
    public String generate(Integer length) {
        if (Objects.isNull(length) || length < 1) {
            return generate();
        }

        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, length);
    }

}
