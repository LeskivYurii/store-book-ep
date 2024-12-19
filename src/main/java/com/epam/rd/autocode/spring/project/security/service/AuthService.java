package com.epam.rd.autocode.spring.project.security.service;


import com.epam.rd.autocode.spring.project.dto.request.LoginRequest;
import jakarta.servlet.http.Cookie;

public interface AuthService {

    Cookie login(LoginRequest loginRequest);

}
