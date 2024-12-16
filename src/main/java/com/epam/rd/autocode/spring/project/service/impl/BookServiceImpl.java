package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.BookDTO;
import com.epam.rd.autocode.spring.project.exception.AlreadyExistException;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.mapper.BookMapper;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import com.epam.rd.autocode.spring.project.service.BookService;
import com.epam.rd.autocode.spring.project.util.Boxed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    public static final String BOOK_NOT_FOUND_ERROR_MESSAGE = "Book with %s name doesn't exist!";

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toBookDTO);
    }

    @Override
    public BookDTO getBookByName(String name) {
        return Boxed
                .of(name)
                .map(bookRepository::findBookByName)
                .map(bookMapper::toBookDTO)
                .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_ERROR_MESSAGE.formatted(name)));
    }

    @Override
    public BookDTO updateBookByName(String name, BookDTO bookDTO) {
        return Boxed
                .of(name)
                .map(bookRepository::findBookByName)
                .doWith(book1 -> bookMapper.updateBook(book1, bookDTO))
                .map(bookRepository::save)
                .map(bookMapper::toBookDTO)
                .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_ERROR_MESSAGE.formatted(name)));
    }

    @Override
    public void deleteBookByName(String name) {
        Boxed
                .of(name)
                .map(bookRepository::findBookByName)
                .ifPresentOrElseThrow(bookRepository::delete,
                        () -> new NotFoundException(BOOK_NOT_FOUND_ERROR_MESSAGE.formatted(name)));
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        return Boxed
                .of(bookDTO)
                .filter(bookDTO1 -> !bookRepository.existsByName(bookDTO1.getName()))
                .map(bookMapper::toBook)
                .map(bookRepository::save)
                .map(bookMapper::toBookDTO)
                .orElseThrow(() -> new AlreadyExistException("Book with %s name already exist!".formatted(bookDTO.getName())));
    }

}
