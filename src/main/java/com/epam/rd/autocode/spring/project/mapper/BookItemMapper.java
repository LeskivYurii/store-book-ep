package com.epam.rd.autocode.spring.project.mapper;

import com.epam.rd.autocode.spring.project.dto.BookItemDTO;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.model.BookItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookItemMapper {

    @Mapping(target = "book", source = "book")
    @Mapping(target = "quantity", source = "bookItemDTO.quantity")
    BookItem toBookItem(Book book, BookItemDTO bookItemDTO);
}
