package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.request.AddCartItemRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetBookItemResponse;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.mapper.BookItemMapper;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.model.CartItem;
import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import com.epam.rd.autocode.spring.project.repo.CartItemRepository;
import com.epam.rd.autocode.spring.project.repo.ClientRepository;
import com.epam.rd.autocode.spring.project.service.CartItemService;
import com.epam.rd.autocode.spring.project.util.Boxed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ClientRepository clientRepository;
    private final BookRepository bookRepository;
    private final BookItemMapper bookItemMapper;

    public void addItemToCart(AddCartItemRequest addCartItemRequest) {
        Boxed
                .of(addCartItemRequest)
                .map(addCartItemRequest1 -> toCartItem(addCartItemRequest))
                .ifPresent(cartItemRepository::save);
    }

    public List<GetBookItemResponse> findClientCart(String email) {
        return cartItemRepository.findAllByClientEmail(email).stream().map(bookItemMapper::toGetBookItemRequest).toList();
    }

    public void deleteCartByClientEmail(String clientEmail) {
        cartItemRepository.deleteAllByClientEmail(clientEmail);
    }

    public void deleteItemFromCart(Long id) {
        cartItemRepository.deleteById(id);
    }


    private CartItem toCartItem(AddCartItemRequest addCartItemRequest) {
        Book book = bookRepository.findById(addCartItemRequest.getBookId())
                .orElseThrow(() -> new NotFoundException("Book with %s id doesn't exist".formatted(addCartItemRequest.getBookId())));
        Client client = clientRepository.findClientByEmail(addCartItemRequest.getClientEmail())
                .orElseThrow(() -> new NotFoundException("Client with %s email does't exist!".formatted(addCartItemRequest.getClientEmail())));

        return CartItem.builder()
                .book(book)
                .client(client)
                .quantity(addCartItemRequest.getQuantity())
                .build();
    }

}
