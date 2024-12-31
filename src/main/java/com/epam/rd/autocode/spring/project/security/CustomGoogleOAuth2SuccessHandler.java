package com.epam.rd.autocode.spring.project.security;

import com.epam.rd.autocode.spring.project.dto.request.CreateClientRequest;
import com.epam.rd.autocode.spring.project.repo.UserRepository;
import com.epam.rd.autocode.spring.project.security.service.JwtService;
import com.epam.rd.autocode.spring.project.service.ClientService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.epam.rd.autocode.spring.project.security.service.impl.AuthServiceImpl.AUTH_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class CustomGoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final ClientService clientService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AccessTokenProperties accessTokenProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String email = oauthToken.getPrincipal().getAttribute("email");
        if (!userRepository.existsByEmail(email)) {
            String firstName = oauthToken.getPrincipal().getAttribute("given_name");
            CreateClientRequest createClientRequest = new CreateClientRequest(email, "", "", firstName, null, null);
            clientService.addOauthClient(createClientRequest);
        }
        if (userRepository.existsByEmailAndIsActive(email, false)) {
            response.sendRedirect("/auth/login-page?blocked=true");
        } else {
            String token = jwtService.generateToken(email);
            Cookie cookie = new Cookie(AUTH_COOKIE_NAME, token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(accessTokenProperties.expiration());
            response.addCookie(cookie);
            response.sendRedirect("/books");
        }
    }
}
