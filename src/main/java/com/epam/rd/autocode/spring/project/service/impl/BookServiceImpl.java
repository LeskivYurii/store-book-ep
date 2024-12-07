package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.BookDTO;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import com.epam.rd.autocode.spring.project.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<BookDTO> getAllBooks() {
        return List.of();
    }

    @Override
    public BookDTO getBookByName(String name) {
        return null;
    }

    @Override
    public BookDTO updateBookByName(String name, BookDTO book) {
        return null;
    }

    @Override
    public void deleteBookByName(String name) {

    }

    @Override
    public BookDTO addBook(BookDTO book) {
        return null;
    }

}
