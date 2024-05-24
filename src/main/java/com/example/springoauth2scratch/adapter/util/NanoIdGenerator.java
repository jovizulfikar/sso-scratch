package com.example.springoauth2scratch.adapter.out.id;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.example.springoauth2scratch.port.IdGenerator;
import org.springframework.stereotype.Service;

@Service
public class NanoIdGenerator implements IdGenerator {
    private static final char[] DEFAULT_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    @Override
    public String generate() {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, NanoIdUtils.DEFAULT_SIZE);
    }

}
