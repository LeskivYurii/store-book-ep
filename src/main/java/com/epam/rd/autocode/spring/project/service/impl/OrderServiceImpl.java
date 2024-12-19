package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.request.CreateOrderRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetOrderDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetOrderListResponse;
import com.epam.rd.autocode.spring.project.mapper.OrderMapper;
import com.epam.rd.autocode.spring.project.repo.OrderRepository;
import com.epam.rd.autocode.spring.project.service.OrderService;
import com.epam.rd.autocode.spring.project.util.Boxed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Page<GetOrderListResponse> getOrdersByClient(String clientEmail, Pageable pageable) {
        return orderRepository.findAllByClientEmail(clientEmail, pageable).map(orderMapper::toGetOrderListResponse);
    }

    @Override
    public Page<GetOrderListResponse> getOrdersByEmployee(String employeeEmail, Pageable pageable) {
        return orderRepository.findAllByEmployeeEmail(employeeEmail, pageable).map(orderMapper::toGetOrderListResponse);
    }

    @Override
    public GetOrderDetailsResponse addOrder(CreateOrderRequest order) {
        return Boxed
                .of(order)
                .filter(orderDTO -> !orderDTO.getBookItems().isEmpty())
                .map(orderMapper::toOrder)
                .map(orderRepository::save)
                .map(orderMapper::toGetOrderDetailsResponse)
                .orElseThrow(() -> new IllegalArgumentException("Orders can't created without items!"));
    }

}
