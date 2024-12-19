package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.request.ModifyBookRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetBookDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetBookListResponse;
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
    public static final String BOOK_NOT_FOUND_ID_ERROR_MESSAGE = "Book with %s id doesn't exist!";

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<GetBookListResponse> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toGetBookListResponse);
    }

    @Override
    public GetBookDetailsResponse getBookByName(String name) {
        return Boxed
                .of(name)
                .map(bookRepository::findBookByName)
                .map(bookMapper::toGetBookDetailsResponse)
                .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_ERROR_MESSAGE.formatted(name)));
    }

    @Override
    public GetBookDetailsResponse getBookById(Long id) {
        return Boxed
                .of(id)
                .flatOpt(bookRepository::findById)
                .map(bookMapper::toGetBookDetailsResponse)
                .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_ID_ERROR_MESSAGE.formatted(id)));
    }

    @Override
    public GetBookDetailsResponse updateBookByName(String name, ModifyBookRequest modifyBookRequest) {
        return Boxed
                .of(name)
                .map(bookRepository::findBookByName)
                .doWith(book1 -> bookMapper.updateBook(book1, modifyBookRequest))
                .map(bookRepository::save)
                .map(bookMapper::toGetBookDetailsResponse)
                .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_ERROR_MESSAGE.formatted(name)));
    }

    @Override
    public GetBookDetailsResponse updateBookById(Long id, ModifyBookRequest modifyBookRequest) {
        return Boxed
                .of(id)
                .flatOpt(bookRepository::findById)
                .doWith(book1 -> bookMapper.updateBook(book1, modifyBookRequest))
                .map(bookRepository::save)
                .map(bookMapper::toGetBookDetailsResponse)
                .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_ID_ERROR_MESSAGE.formatted(id)));
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
    public void deleteBookById(Long id) {
        Boxed
                .of(id)
                .flatOpt(bookRepository::findById)
                .ifPresentOrElseThrow(bookRepository::delete,
                        () -> new NotFoundException(BOOK_NOT_FOUND_ID_ERROR_MESSAGE.formatted(id)));
    }

    @Override
    public GetBookDetailsResponse addBook(ModifyBookRequest modifyBookRequest) {
        return Boxed
                .of(modifyBookRequest)
                .filter(bookDTO1 -> !bookRepository.existsByName(bookDTO1.getName()))
                .map(bookMapper::toBook)
                .map(bookRepository::save)
                .map(bookMapper::toGetBookDetailsResponse)
                .orElseThrow(() -> new AlreadyExistException("Book with %s name already exist!".formatted(modifyBookRequest.getName())));
    }

}
