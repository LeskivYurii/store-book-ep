package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.request.LoginRequest;
import com.epam.rd.autocode.spring.project.security.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login-page")
    public String getLoginPage(@ModelAttribute(name = "login") LoginRequest loginRequest) {
        return "/auth/login";
    }

    @PostMapping("/login")
    public String verifyCredentials(@ModelAttribute(name = "login") @Valid LoginRequest loginRequest, BindingResult bindingResult,
                                    HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "/auth/login";
        }
        Cookie cookie = authService.login(loginRequest);
        response.addCookie(cookie);
        return "redirect:/books";
    }

}
