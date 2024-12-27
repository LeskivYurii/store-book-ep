package com.epam.rd.autocode.spring.project.validation.validator;

import com.epam.rd.autocode.spring.project.repo.UserRepository;
import com.epam.rd.autocode.spring.project.security.UserDetailsAdapter;
import com.epam.rd.autocode.spring.project.validation.annotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser") || value != null) {
            return !userRepository.existsByEmail(value);
        } else if (((UserDetailsAdapter) authentication.getPrincipal()).getUser().getRole().equals("ROLE_EMPLOYEE")) {
            return true;
        } else  if (((UserDetailsAdapter) authentication.getPrincipal()).getUser().getRole().equals("ROLE_CLIENT")) {
            return (SecurityContextHolder.getContext().getAuthentication().getName().equals(value) || !userRepository.existsByEmail(value));

        }

        return true;
    }

}
