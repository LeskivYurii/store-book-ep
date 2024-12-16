package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.OrderDTO;
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
    public Page<OrderDTO> getOrdersByClient(String clientEmail, Pageable pageable) {
        return orderRepository.findAllByClientEmail(clientEmail, pageable).map(orderMapper::toOrderDto);
    }

    @Override
    public Page<OrderDTO> getOrdersByEmployee(String employeeEmail, Pageable pageable) {
        return orderRepository.findAllByEmployeeEmail(employeeEmail, pageable).map(orderMapper::toOrderDto);
    }

    @Override
    public OrderDTO addOrder(OrderDTO order) {
        return Boxed
                .of(order)
                .filter(orderDTO -> !orderDTO.getBookItems().isEmpty())
                .map(orderMapper::toOrder)
                .map(orderRepository::save)
                .map(orderMapper::toOrderDto)
                .orElseThrow(() -> new IllegalArgumentException("Orders can't created without items!"));
    }

}
