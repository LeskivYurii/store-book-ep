package com.epam.rd.autocode.spring.project.security;

import com.epam.rd.autocode.spring.project.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthExpressions {

    private final OrderRepository orderRepository;

    public boolean isUserAllowed(String email) {
        return ((UserDetailsAdapter) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUsername()
                .equals(email);
    }

    public boolean isClientOrder(Long orderId) {
        UserDetailsAdapter userDetails = (UserDetailsAdapter) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return orderRepository.findByOrderIdAndCustomer(orderId, userDetails.getUsername()).isPresent();
    }


}