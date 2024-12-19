package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.request.CreateOrderRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetOrderDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetOrderListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Page<GetOrderListResponse> getOrdersByClient(String clientEmail, Pageable pageable);

    Page<GetOrderListResponse> getOrdersByEmployee(String employeeEmail, Pageable pageable);

    GetOrderDetailsResponse addOrder(CreateOrderRequest order);
}
