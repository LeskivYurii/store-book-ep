package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.request.ModifyBookRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetBookDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetBookListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Page<GetBookListResponse> getAllBooks(Pageable pageable);

    GetBookDetailsResponse getBookByName(String name);

    GetBookDetailsResponse getBookById(Long id);

    GetBookDetailsResponse updateBookByName(String name, ModifyBookRequest book);

    GetBookDetailsResponse updateBookById(Long id, ModifyBookRequest book);

    void deleteBookByName(String name);

    void deleteBookById(Long id);

    GetBookDetailsResponse addBook(ModifyBookRequest book);
}
