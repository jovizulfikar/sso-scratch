package com.example.springoauth2scratch.jose.jws;

public record MacAlgorithm(String name) implements JwsAlgorithm {
    public static final MacAlgorithm HS256 = new MacAlgorithm("HS256");
    public static final MacAlgorithm HS384 = new MacAlgorithm("HS384");
    public static final MacAlgorithm HS512 = new MacAlgorithm("HS512");
}
