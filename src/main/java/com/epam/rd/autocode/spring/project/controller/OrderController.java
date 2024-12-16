package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.BookItemDTO;
import com.epam.rd.autocode.spring.project.dto.OrderDTO;
import com.epam.rd.autocode.spring.project.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/clients/{email}/orders")
    public String getAllClientOrders(@PathVariable String email, @PageableDefault Pageable pageable, Model model) {
        model.addAttribute("orders", orderService.getOrdersByClient(email, pageable));
        return "/order/order-list";
    }

    @GetMapping("/employees/{email}/orders")
    public String getAllEmployeeOrders(@PathVariable String email, @PageableDefault Pageable pageable, Model model) {
        model.addAttribute("orders", orderService.getOrdersByEmployee(email, pageable));
        return "/order/order-list";
    }

    @PostMapping("/orders")
    public void createOrder(@ModelAttribute OrderDTO orderDTO, HttpSession httpSession, Model model) {
        orderDTO.setBookItems((List<BookItemDTO>) httpSession.getAttribute("items"));
        model.addAttribute("order", orderService.addOrder(orderDTO));
    }
}
