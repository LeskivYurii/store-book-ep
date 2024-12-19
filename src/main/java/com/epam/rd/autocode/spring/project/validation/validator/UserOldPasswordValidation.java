package com.epam.rd.autocode.spring.project.validation.validator;

import com.epam.rd.autocode.spring.project.dto.request.UpdateClientRequest;
import com.epam.rd.autocode.spring.project.dto.request.UpdateEmployeeRequest;
import com.epam.rd.autocode.spring.project.model.User;
import com.epam.rd.autocode.spring.project.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserOldPasswordValidation implements Validator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return UpdateEmployeeRequest.class.equals(clazz) || UpdateClientRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Object oldPassword = new BeanWrapperImpl(target)
                .getPropertyValue("oldPassword");
        Object newPassword = new BeanWrapperImpl(target)
                .getPropertyValue("password");
        Object email = new BeanWrapperImpl(target)
                .getPropertyValue("email");

        if(oldPassword != null && newPassword != null && !"".equals(oldPassword) && !"".equals(newPassword)) {
            User user = userRepository.findUserByEmail((String) email);
            if(passwordEncoder.matches((String) oldPassword, user.getPassword())) {
                errors.rejectValue("oldPassword", "400", "Old password is wrong");
            }
        }
    }
}
