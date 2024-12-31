package com.epam.rd.autocode.spring.project.validation.validator;

import com.epam.rd.autocode.spring.project.dto.request.UpdateClientRequest;
import com.epam.rd.autocode.spring.project.dto.request.UpdateEmployeeRequest;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.model.User;
import com.epam.rd.autocode.spring.project.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
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
        User user = userRepository.findUserByEmail((String) email)
                .orElseThrow(() -> new NotFoundException("User with %s email doesn't exist".formatted(email)));

        if(oldPassword != null && newPassword != null && !"".equals(oldPassword) && !"".equals(newPassword)) {
            if(passwordEncoder.matches((String) oldPassword, user.getPassword())) {
                errors.rejectValue("oldPassword", "400", "Old password is wrong");
            }
        }  else if(!"".equals(newPassword) && newPassword != null && ("".equals(oldPassword)) && !passwordEncoder
                .matches("", user.getPassword())) {
            errors.rejectValue("oldPassword", "400", "Enter old password to verify correctness!");

        }
    }
}
