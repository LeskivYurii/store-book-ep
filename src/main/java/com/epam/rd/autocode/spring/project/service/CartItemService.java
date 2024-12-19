package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.request.AddCartItemRequest;
import com.epam.rd.autocode.spring.project.dto.request.GetBookItemRequest;

import java.util.List;

public interface CartItemService {

    void addItemToCart(AddCartItemRequest addCartItemRequest);

    List<GetBookItemRequest> findClientCart(String email);

    void deleteCartByClientEmail(String email);
    void deleteItemFromCart(Long itemId);
}
