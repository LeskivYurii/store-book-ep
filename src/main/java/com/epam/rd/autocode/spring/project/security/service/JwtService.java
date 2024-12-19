package com.epam.rd.autocode.spring.project.security.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

public interface JwtService {

    String getSubject(String token);
    boolean isTokenValid(String token);
    String generateToken(String username);
    LocalDateTime getExpirationDate(String token);

}
