package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.response.GetOrderDetailsResponse;
import com.epam.rd.autocode.spring.project.model.enums.OrderStatus;
import com.epam.rd.autocode.spring.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/clients/{email}/orders")
    @PreAuthorize("hasRole('EMPLOYEE') or @authExpressions.isUserAllowed(#email)")
    public String getAllClientOrders(@PathVariable String email, @PageableDefault Pageable pageable, Model model) {
        model.addAttribute("orders", orderService.getOrdersByClient(email, pageable));
        model.addAttribute("statuses", OrderStatus.values());
        return "order/order-list";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employees/{email}/orders")
    public String getAllEmployeeOrders(@PathVariable String email, @PageableDefault Pageable pageable, Model model) {
        model.addAttribute("orders", orderService.getOrdersByEmployee(email, pageable));
        model.addAttribute("statuses", OrderStatus.values());
        return "order/order-list";
    }

    @PostMapping("/orders")
    @PreAuthorize("hasRole('CLIENT')")
    public String createOrder() {
      GetOrderDetailsResponse getOrderDetailsResponse = orderService.createOrder();
        System.out.print(getOrderDetailsResponse);
      return "redirect:/orders/" + getOrderDetailsResponse.getId();
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/orders")
    public String getAllOrders(Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("orders", orderService.findAll(pageable));
        model.addAttribute("statuses", OrderStatus.values());
        return "order/order-list";
    }

    @PreAuthorize("hasRole('EMPLOYEE') or @authExpressions.isClientOrder(#id)")
    @GetMapping("/orders/{id}")
    public String findOrderById(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.findOrderById(id));
        model.addAttribute("statuses", OrderStatus.values());
        return "order/order-details";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PatchMapping("/orders/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        orderService.updateStatus(id, status);
        return "redirect:/orders/" + id;
    }
}
