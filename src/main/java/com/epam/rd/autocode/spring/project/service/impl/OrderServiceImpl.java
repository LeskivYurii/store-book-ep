package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.request.CreateOrderRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetOrderDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetOrderListResponse;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.mapper.OrderMapper;
import com.epam.rd.autocode.spring.project.model.BookItem;
import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.model.Order;
import com.epam.rd.autocode.spring.project.model.User;
import com.epam.rd.autocode.spring.project.model.enums.OrderStatus;
import com.epam.rd.autocode.spring.project.repo.CartItemRepository;
import com.epam.rd.autocode.spring.project.repo.ClientRepository;
import com.epam.rd.autocode.spring.project.repo.EmployeeRepository;
import com.epam.rd.autocode.spring.project.repo.OrderRepository;
import com.epam.rd.autocode.spring.project.security.UserDetailsAdapter;
import com.epam.rd.autocode.spring.project.service.EmployeeService;
import com.epam.rd.autocode.spring.project.service.OrderService;
import com.epam.rd.autocode.spring.project.util.Boxed;
import com.epam.rd.autocode.spring.project.validation.validator.UserOldPasswordValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartItemRepository cartItemRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Page<GetOrderListResponse> getOrdersByClient(String clientEmail, Pageable pageable) {
        return orderRepository.findAllByClientEmail(clientEmail, pageable).map(orderMapper::toGetOrderListResponse);
    }

    @Override
    public Page<GetOrderListResponse> getOrdersByEmployee(String employeeEmail, Pageable pageable) {
        return orderRepository.findAllByEmployeeEmail(employeeEmail, pageable).map(orderMapper::toGetOrderListResponse);
    }

    @Override
    @PreAuthorize("hasRole('CLIENT')")
    public GetOrderDetailsResponse createOrder() {
        String clientEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return Boxed
                .of(clientEmail)
                .map(cartItemRepository::findAllByClientEmail)
                .filter(cartItems -> !cartItems.isEmpty())
                .map(cartItems -> orderMapper.toOrder(cartItems, clientEmail))
                .map(orderRepository::save)
                .doWith(order -> cartItemRepository.deleteAllByClientEmail(clientEmail))
                .map(orderMapper::toGetOrderDetailsResponse)
                .orElseThrow(() -> new IllegalArgumentException("Orders can't be created without items!"));
    }

    @Override
    public GetOrderDetailsResponse findOrderById(Long id) {
        return Boxed
                .of(id)
                .flatOpt(orderRepository::findById)
                .map(orderMapper::toGetOrderDetailsResponse)
                .orElseThrow(() -> new NotFoundException("Order with %s doesn't exist!".formatted(id)));
    }

    @Override
    public Page<GetOrderListResponse> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toGetOrderListResponse);
    }

    @Override
    public GetOrderDetailsResponse updateStatus(Long id, OrderStatus orderStatus) {
        return Boxed
                .of(id)
                .flatOpt(orderRepository::findById)
                .doWith(order -> order.setStatus(orderStatus))
                .doWith(this::returnQuantityIfCancelled)
                .doWith(this::updateEmployeeEmail)
                .map(orderRepository::save)
                .map(orderMapper::toGetOrderDetailsResponse)
                .orElseThrow(() -> new NotFoundException("Order with %s doesn't exist!".formatted(id)));
    }

    private void returnQuantityIfCancelled(Order order) {
        if (OrderStatus.CANCELLED.equals(order.getStatus())) {
            order.getBookItems().forEach(this::subtractQuantity);
        }
    }

    private void subtractQuantity(BookItem bookItem) {
        bookItem.getBook().setQuantity(bookItem.getBook().getQuantity() + bookItem.getQuantity());
    }

    private void updateEmployeeEmail(Order order) {
        User userDetails = ((UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        if ("ROLE_EMPLOYEE".equals(userDetails.getRole())) {
            order.setEmployee(employeeRepository.findEmployeeByEmail(userDetails.getEmail()).orElse(null));
        }
    }
}
