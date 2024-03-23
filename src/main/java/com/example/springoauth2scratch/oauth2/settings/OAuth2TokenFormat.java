package com.example.springoauth2scratch.oauth2.settings;

public record OAuth2TokenFormat(String value) {
	public static final OAuth2TokenFormat SELF_CONTAINED = new OAuth2TokenFormat("self-contained");
	public static final OAuth2TokenFormat REFERENCE = new OAuth2TokenFormat("reference");

}
