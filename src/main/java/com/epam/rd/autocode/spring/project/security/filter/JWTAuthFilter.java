package com.epam.rd.autocode.spring.project.security.filter;

import com.epam.rd.autocode.spring.project.security.service.JwtService;
import com.epam.rd.autocode.spring.project.security.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Stream;

import static com.epam.rd.autocode.spring.project.security.service.impl.AuthServiceImpl.AUTH_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getAccessToken(request);
        if (token != null && jwtService.isTokenValid(token)) {
            setAuthenticationContext(token);
        }
        doFilter(request, response, filterChain);
    }

    private void setAuthenticationContext(String token) {
        String username = jwtService.getSubject(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getAccessToken(HttpServletRequest request) {
        if(request.getCookies() == null || request.getCookies().length == 0) {
            return null;
        }

        return Stream.of(request.getCookies())
                .filter(cookie -> cookie.getName().equals(AUTH_COOKIE_NAME))
                .findAny()
                .map(Cookie::getValue)
                .orElse(null);
    }

}
