package com.epam.rd.autocode.spring.project.mapper;

import com.epam.rd.autocode.spring.project.dto.request.ModifyBookRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetBookDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetBookListResponse;
import com.epam.rd.autocode.spring.project.mapper.helper.BookMapperHelper;
import com.epam.rd.autocode.spring.project.model.Book;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = BookMapperHelper.class)
public interface BookMapper {

    @Mapping(target = "image", source = "image", qualifiedByName = "toImageLink")
    GetBookDetailsResponse toGetBookDetailsResponse(Book book);
    @Mapping(target = "image", source = "image", qualifiedByName = "toImageLink")
    GetBookListResponse toGetBookListResponse(Book book);

    @Mapping(target = "image", source = "image", qualifiedByName = "toImage")
    Book toBook(ModifyBookRequest modifyBookRequest);

    @Mapping(target = "image", source = "image", qualifiedByName = "toImage")
    void updateBook(@MappingTarget Book book, ModifyBookRequest modifyBookRequest);

}
