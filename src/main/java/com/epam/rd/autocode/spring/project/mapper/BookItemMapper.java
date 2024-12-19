package com.epam.rd.autocode.spring.project.mapper;

import com.epam.rd.autocode.spring.project.dto.BookItemDTO;
import com.epam.rd.autocode.spring.project.dto.request.GetBookItemRequest;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.model.BookItem;
import com.epam.rd.autocode.spring.project.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookItemMapper {

    @Mapping(target = "book", source = "book")
    @Mapping(target = "quantity", source = "bookItemDTO.quantity")
    BookItem toBookItem(Book book, BookItemDTO bookItemDTO);

    @Mapping(target = "bookName" , source = "cartItem.book.name")
    @Mapping(target = "quantity" , source = "cartItem.quantity")
    @Mapping(target = "price" , source = "cartItem.book.price")
    GetBookItemRequest toGetBookItemRequest(CartItem cartItem);

}
