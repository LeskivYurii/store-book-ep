package com.epam.rd.autocode.spring.project.mapper;

import com.epam.rd.autocode.spring.project.dto.response.GetBookItemResponse;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.model.BookItem;
import com.epam.rd.autocode.spring.project.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", source = "cartItem.book")
    @Mapping(target = "quantity", source = "cartItem.quantity")
    BookItem toBookItem(CartItem cartItem);

    @Mapping(target = "bookName" , source = "cartItem.book.name")
    @Mapping(target = "quantity" , source = "cartItem.quantity")
    @Mapping(target = "price" , source = "cartItem.book.price")
    @Mapping(target = "bookId" , source = "cartItem.book.id")
    GetBookItemResponse toGetBookItemRequest(CartItem cartItem);

}
