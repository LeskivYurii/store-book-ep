package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.request.AddCartItemRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetBookItemResponse;
import com.epam.rd.autocode.spring.project.service.CartItemService;
import com.epam.rd.autocode.spring.project.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;
    private final ClientService clientService;

    @GetMapping("/clients/{email}/cart")
    public String cartItems(@PathVariable String email, Model model) {
        List<GetBookItemResponse> books =  cartItemService.findClientCart(email);
        model.addAttribute("books", books);
        model.addAttribute("client", clientService.getClientByEmail(email));
        model.addAttribute("totalPrice", books
                .stream()
                .map(GetBookItemResponse::getPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
        return "/cart/cart-list";
    }

    @PostMapping("/clients/{email}/cart")
    public String addItem(@PathVariable String email, @ModelAttribute(name = "item") AddCartItemRequest addCartItemRequest) {
        cartItemService.addItemToCart(addCartItemRequest);
        return "redirect:/clients/" + email + "/cart";
    }

    @DeleteMapping("/clients/{email}/cart/{id}")
    public String deleteItem(@PathVariable String email, @PathVariable Long id) {
        cartItemService.deleteItemFromCart(id);
        return "redirect:/clients/" + email + "/cart";
    }

    @DeleteMapping("/clients/{email}/cart")
    public String cleanCart(@PathVariable String email) {
        cartItemService.deleteCartByClientEmail(email);
        return "redirect:/clients/" + email + "/cart";
    }

}
