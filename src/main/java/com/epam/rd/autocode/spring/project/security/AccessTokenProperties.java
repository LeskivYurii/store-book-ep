package com.epam.rd.autocode.spring.project.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.crypto.SecretKey;

import static io.jsonwebtoken.io.Decoders.BASE64;

@ConfigurationProperties("security.jwt")
public record AccessTokenProperties (String secret, int expiration) {

    public SecretKey getSecretKey() {
        byte[] keyBytes = BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
