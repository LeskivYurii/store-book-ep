package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.request.ModifyBookRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetBookDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetBookListResponse;
import com.epam.rd.autocode.spring.project.exception.AlreadyExistException;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.mapper.BookMapper;
import com.epam.rd.autocode.spring.project.azure.service.BlobService;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import com.epam.rd.autocode.spring.project.service.BookService;
import com.epam.rd.autocode.spring.project.util.Boxed;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    public static final String BOOK_NOT_FOUND_ERROR_MESSAGE = "error.book.name.notfound";
    public static final String BOOK_NOT_FOUND_ID_ERROR_MESSAGE = "error.book.notfound";

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BlobService blobService;
    private final MessageSource messageSource;

    @Override
    public Page<GetBookListResponse> getAllBooks(Pageable pageable, Specification<Book> specification) {
        return bookRepository.findAll(specification, pageable).map(bookMapper::toGetBookListResponse);
    }

    @Override
    public GetBookDetailsResponse getBookByName(String name) {
        return Boxed
                .of(name)
                .map(bookRepository::findBookByName)
                .map(bookMapper::toGetBookDetailsResponse)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage(BOOK_NOT_FOUND_ERROR_MESSAGE, null,
                        LocaleContextHolder.getLocale()).formatted(name)));
    }

    @Override
    public GetBookDetailsResponse getBookById(Long id) {
        return Boxed
                .of(id)
                .flatOpt(bookRepository::findById)
                .map(bookMapper::toGetBookDetailsResponse)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage(BOOK_NOT_FOUND_ID_ERROR_MESSAGE, null,
                        LocaleContextHolder.getLocale()).formatted(id)));
    }

    @Override
    public GetBookDetailsResponse updateBookByName(String name, ModifyBookRequest modifyBookRequest) {
        return Boxed
                .of(name)
                .map(bookRepository::findBookByName)
                .doWith(book1 -> bookMapper.updateBook(book1, modifyBookRequest))
                .map(bookRepository::save)
                .map(bookMapper::toGetBookDetailsResponse)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage(BOOK_NOT_FOUND_ERROR_MESSAGE, null,
                        LocaleContextHolder.getLocale()).formatted(name)));
    }

    @Override
    public GetBookDetailsResponse updateBookById(Long id, ModifyBookRequest modifyBookRequest) {
        return Boxed
                .of(id)
                .flatOpt(bookRepository::findById)
                .doWith(book1 -> bookMapper.updateBook(book1, modifyBookRequest))
                .map(bookRepository::save)
                .map(bookMapper::toGetBookDetailsResponse)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage(BOOK_NOT_FOUND_ID_ERROR_MESSAGE, null,
                        LocaleContextHolder.getLocale()).formatted(id)));
    }

    @Override
    public void deleteBookByName(String name) {
        Boxed
                .of(name)
                .map(bookRepository::findBookByName)
                .ifPresentOrElseThrow(bookRepository::delete,
                        () -> new NotFoundException(messageSource.getMessage(BOOK_NOT_FOUND_ERROR_MESSAGE, null,
                                LocaleContextHolder.getLocale()).formatted(name)));
    }

    @Override
    public void deleteBookById(Long id) {
        Boxed
                .of(id)
                .flatOpt(bookRepository::findById)
                .doWith(book -> blobService.deleteImage(book.getImage()))
                .ifPresentOrElseThrow(bookRepository::delete,
                        () -> new NotFoundException(messageSource.getMessage(BOOK_NOT_FOUND_ID_ERROR_MESSAGE, null,
                                LocaleContextHolder.getLocale()).formatted(id)));
    }

    @Override
    public GetBookDetailsResponse addBook(ModifyBookRequest modifyBookRequest) {
        return Boxed
                .of(modifyBookRequest)
                .filter(bookDTO1 -> !bookRepository.existsByName(bookDTO1.getName()))
                .map(bookMapper::toBook)
                .map(bookRepository::save)
                .map(bookMapper::toGetBookDetailsResponse)
                .orElseThrow(() -> new AlreadyExistException(messageSource.getMessage("error.book.alreadyExist", null,
                        LocaleContextHolder.getLocale()).formatted(modifyBookRequest.getName())));
    }

}
