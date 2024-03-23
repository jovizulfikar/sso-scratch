package com.example.springoauth2scratch.jose.jws;

public record SignatureAlgorithm(String name) implements JwsAlgorithm {
    public static final SignatureAlgorithm RS256 = new SignatureAlgorithm("RS256");
    public static final SignatureAlgorithm RS384 = new SignatureAlgorithm("RS384");
    public static final SignatureAlgorithm RS512 = new SignatureAlgorithm("RS512");
    public static final SignatureAlgorithm ES256 = new SignatureAlgorithm("ES256");
    public static final SignatureAlgorithm ES384 = new SignatureAlgorithm("ES384");
    public static final SignatureAlgorithm ES512 = new SignatureAlgorithm("ES512");
    public static final SignatureAlgorithm PS256 = new SignatureAlgorithm("PS256");
    public static final SignatureAlgorithm PS384 = new SignatureAlgorithm("PS384");
    public static final SignatureAlgorithm PS512 = new SignatureAlgorithm("PS512");
}
