package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.request.CreateOrderRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetOrderDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetOrderListResponse;
import com.epam.rd.autocode.spring.project.model.enums.OrderStatus;
import org.hibernate.engine.spi.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Page<GetOrderListResponse> getOrdersByClient(String clientEmail, Pageable pageable);

    Page<GetOrderListResponse> getOrdersByEmployee(String employeeEmail, Pageable pageable);

    GetOrderDetailsResponse createOrder();

    GetOrderDetailsResponse findOrderById(Long id);

    Page<GetOrderListResponse> findAll(Pageable pageable);

    GetOrderDetailsResponse updateStatus(Long id, OrderStatus orderStatus);
}
