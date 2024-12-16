package com.epam.rd.autocode.spring.project.mapper.helper;

import com.epam.rd.autocode.spring.project.dto.BookItemDTO;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.mapper.BookItemMapper;
import com.epam.rd.autocode.spring.project.model.BookItem;
import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.model.Employee;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import com.epam.rd.autocode.spring.project.repo.ClientRepository;
import com.epam.rd.autocode.spring.project.repo.EmployeeRepository;
import com.epam.rd.autocode.spring.project.util.Boxed;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapperHelper {

    private final BookRepository bookRepository;
    private final BookItemMapper bookItemMapper;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Named("toClient")
    public Client toClient(String email) {
        return clientRepository.findClientByEmail(email);
    }

    @Named("toEmployee")
    public Employee toEmployee(String email) {
        return employeeRepository.findEmployeeByEmail(email);
    }

    @Named("toBookItems")
    public List<BookItem> toBookItems(List<BookItemDTO> bookItemDTOS) {
        return bookItemDTOS
                .stream()
                .map(this::toBookItem)
                .toList();

    }

    @Named("toOrderDate")
    public LocalDateTime toOrderDate(Object plug) {
        return LocalDateTime.now();
    }

    @Named("toBookItemDtos")
    public List<BookItemDTO> toBookItemsDto(List<BookItem> bookItems) {
        return bookItems
                .stream()
                .map(bookItem -> new BookItemDTO(bookItem.getBook().getName(), bookItem.getQuantity()))
                .toList();
    }

    private BookItem toBookItem(BookItemDTO bookItemDTO) {
        return Boxed
                .of(bookItemDTO)
                .map(bookItemDTO1 -> bookRepository.findBookByName(bookItemDTO1.getBookName()))
                //.filter(book -> validateBookQuantity(book, bookItemDTO))
                .map(book -> bookItemMapper.toBookItem(book, bookItemDTO))
                .orElseThrow(() -> new NotFoundException("Book with %s name wasn't found!".formatted(bookItemDTO.getBookName())));
    }

   /* private void validateBookQuantity(Book book, BookItemDTO bookItemDTO){
        if(book.getQuantity() < bookItemDTO.getQuantity()) {
            throw new NotEnoughBookQuantityException("Book with '%s' name doesn't have enough quantity to order!");
        }
    }*/
}
