package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.request.AddCartItemRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetBookItemResponse;

import java.util.List;

public interface CartItemService {

    void addItemToCart(AddCartItemRequest addCartItemRequest);

    List<GetBookItemResponse> findClientCart(String email);

    void deleteCartByClientEmail(String email);
    void deleteItemFromCart(Long itemId);
}
