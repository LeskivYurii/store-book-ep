package com.epam.rd.autocode.spring.project.mapper;

import com.epam.rd.autocode.spring.project.dto.request.ModifyBookRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetBookDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetBookListResponse;
import com.epam.rd.autocode.spring.project.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    GetBookDetailsResponse toGetBookDetailsResponse(Book book);

    GetBookListResponse toGetBookListResponse(Book book);

    Book toBook(ModifyBookRequest modifyBookRequest);

    void updateBook(@MappingTarget Book book, ModifyBookRequest modifyBookRequest);

}
