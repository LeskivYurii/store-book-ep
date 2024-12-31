package com.epam.rd.autocode.spring.project.mapper.helper;

import com.epam.rd.autocode.spring.project.dto.response.GetBookItemResponse;
import com.epam.rd.autocode.spring.project.exception.NotEnoughBookQuantityException;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.mapper.BookItemMapper;
import com.epam.rd.autocode.spring.project.model.BookItem;
import com.epam.rd.autocode.spring.project.model.CartItem;
import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.model.Employee;
import com.epam.rd.autocode.spring.project.repo.ClientRepository;
import com.epam.rd.autocode.spring.project.repo.EmployeeRepository;
import com.epam.rd.autocode.spring.project.util.Boxed;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapperHelper {

    private final BookItemMapper bookItemMapper;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Named("toClient")
    public Client toClient(String email) {
        return clientRepository.findClientByEmail(email)
                .orElseThrow(() -> new NotFoundException("Client with %s email doesn't exist!".formatted(email)));
    }

    @Named("toEmployee")
    public Employee toEmployee(String email) {
        return employeeRepository.findEmployeeByEmail(email).orElse(null);
    }

    @Named("toBookItems")
    public List<BookItem> toBookItems(List<CartItem> bookItemDTOS) {
        return bookItemDTOS
                .stream()
                .map(this::toBookItem)
                .toList();

    }

    @Named("toPrice")
    public BigDecimal toOrderPrice(List<CartItem> cartItemList) {
        return cartItemList
                .stream()
                .map(bookItem -> bookItem.getBook().getPrice().multiply(BigDecimal.valueOf(
                        bookItem.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Named("toGetBookItemResponse")
    public List<GetBookItemResponse> toGetBookItemResponseList(List<BookItem> bookItems) {
        return bookItems
                .stream()
                .map(this::toGetBookItemResponse )
                .toList();
    }

    private GetBookItemResponse toGetBookItemResponse(BookItem bookItem) {
        return GetBookItemResponse
                .builder()
                .id(bookItem.getId())
                .bookId(bookItem.getBook().getId())
                .bookName(bookItem.getBook().getName())
                .quantity(bookItem.getQuantity())
                .price(bookItem.getBook().getPrice())
                .build();
    }

    private BookItem toBookItem(CartItem book) {
        return Boxed
                .of(book)
                .filter(cartItem -> cartItem.getBook().getQuantity() >= cartItem.getQuantity())
                .doWith(this::subtractQuantity)
                .map(bookItemMapper::toBookItem)
                .orElseThrow(() -> new NotEnoughBookQuantityException("Book with '%s' id doesn't have enough quantity" +
                                                                      " to order!"));
    }

    private void subtractQuantity(CartItem cartItem) {
        cartItem.getBook().setQuantity(cartItem.getBook().getQuantity() - cartItem.getQuantity());
    }


}
