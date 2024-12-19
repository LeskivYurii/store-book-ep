package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.request.AddCartItemRequest;
import com.epam.rd.autocode.spring.project.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @GetMapping("/clients/{email}/cart")
    public String cartItems(@PathVariable String email, Model model) {
        model.addAttribute("items", cartItemService.findClientCart(email));
        return "/cart/cart-list";
    }

    @PostMapping("/clients/{email}/cart")
    public String addItem(@PathVariable String email, @ModelAttribute(name = "items") AddCartItemRequest addCartItemRequest) {
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
