package com.epam.rd.autocode.spring.project.mapper;

import com.epam.rd.autocode.spring.project.dto.request.ModifyBookRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetBookDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetBookListResponse;
import com.epam.rd.autocode.spring.project.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    @Mapping(target = "image", ignore = true)
    GetBookDetailsResponse toGetBookDetailsResponse(Book book);
    @Mapping(target = "image", ignore = true)

    GetBookListResponse toGetBookListResponse(Book book);

    @Mapping(target = "image", ignore = true)
    Book toBook(ModifyBookRequest modifyBookRequest);

    @Mapping(target = "image", ignore = true)
    void updateBook(@MappingTarget Book book, ModifyBookRequest modifyBookRequest);

}
