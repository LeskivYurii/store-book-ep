package com.epam.rd.autocode.spring.project.security.service.impl;

import com.epam.rd.autocode.spring.project.dto.request.LoginRequest;
import com.epam.rd.autocode.spring.project.security.AccessTokenProperties;
import com.epam.rd.autocode.spring.project.security.UserDetailsAdapter;
import com.epam.rd.autocode.spring.project.security.service.AuthService;
import com.epam.rd.autocode.spring.project.security.service.JwtService;
import com.epam.rd.autocode.spring.project.util.Boxed;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public static final String AUTH_COOKIE_NAME = "bookShopJwt";

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AccessTokenProperties accessTokenProperties;

    @Override
    public Cookie login(LoginRequest loginRequest) {
        return Boxed
                .of(loginRequest)
                .map(loginRequest1 -> authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest1.getUsername(), loginRequest1.getPassword())))
                .map(authentication -> (UserDetailsAdapter) authentication.getPrincipal())
                .map(UserDetailsAdapter::getUsername)
                .map(jwtService::generateToken)
                .map(token -> new Cookie(AUTH_COOKIE_NAME, token))
                .doWith(cookie -> cookie.setHttpOnly(true))
                .doWith(cookie -> cookie.setPath("/"))
                .doWith(cookie -> cookie.setMaxAge(accessTokenProperties.expiration()))
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
    }

}
