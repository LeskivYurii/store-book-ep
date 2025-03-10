package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.request.LoginRequest;
import com.epam.rd.autocode.spring.project.security.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MessageSource messageSource;

    @GetMapping("/login-page")
    public String getLoginPage(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               @RequestParam(value = "blocked", required = false) String blocked, Model model) {
        model.addAttribute("login", new LoginRequest());
        if (error != null) {
            model.addAttribute("errorMessage", messageSource.getMessage("error.auth.invalidCred",
                    null, LocaleContextHolder.getLocale()));
        }
        if (logout != null) {
            model.addAttribute("logoutMessage",  messageSource.getMessage("logout.success",
                    null, LocaleContextHolder.getLocale()));
        }
        if (blocked != null) {
            model.addAttribute("blockedMessage", messageSource.getMessage("error.auth.blocked",
                    null, LocaleContextHolder.getLocale()));
        }

        return "auth/login";
    }

    @PostMapping("/login")
    public String verifyCredentials(@ModelAttribute(name = "login") @Valid LoginRequest loginRequest,
                                    BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }
        Cookie cookie = authService.login(loginRequest);
        response.addCookie(cookie);
        return "redirect:/books";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        response.addCookie(authService.logout());
        return "redirect:/auth/login-page?logout=true";
    }

}
