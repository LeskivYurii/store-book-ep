package com.epam.rd.autocode.spring.project.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperHelper {

    private final PasswordEncoder passwordEncoder;

    @Named("toEncodedPassword")
    public String toEncodedPassword(String password) {
        if (password != null && !password.isEmpty()) {
            return passwordEncoder.encode(password);
        }
        return null;
    }

    @Named("toOauthEncodedPassword")
    public String toOauthEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
