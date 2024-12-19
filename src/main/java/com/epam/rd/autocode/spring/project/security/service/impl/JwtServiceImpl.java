package com.epam.rd.autocode.spring.project.security.service.impl;

import com.epam.rd.autocode.spring.project.security.AccessTokenProperties;
import com.epam.rd.autocode.spring.project.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final AccessTokenProperties accessTokenProperties;

    public JwtServiceImpl(AccessTokenProperties accessTokenProperties) {
        this.accessTokenProperties = accessTokenProperties;
    }

    public String generateToken(String username) {
        return Jwts
                .builder()
                .issuer("book-shop")
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (accessTokenProperties.expiration() * 1000L)))
                .signWith(accessTokenProperties.getSecretKey())
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(accessTokenProperties.getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            log.error("Error happened during token validation with message {}", e.getMessage(), e);
        }
        return false;
    }

    public String getSubject(String token) {
        return Jwts
                .parser()
                .verifyWith(accessTokenProperties.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public LocalDateTime getExpirationDate(String token) {
        return Jwts
                .parser()
                .verifyWith(accessTokenProperties.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}

