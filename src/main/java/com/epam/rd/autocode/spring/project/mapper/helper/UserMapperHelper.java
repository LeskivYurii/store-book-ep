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
        return passwordEncoder.encode(password);
    }

}
