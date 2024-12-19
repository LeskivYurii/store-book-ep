package com.epam.rd.autocode.spring.project.mapper;

import com.epam.rd.autocode.spring.project.dto.request.CreateClientRequest;
import com.epam.rd.autocode.spring.project.dto.request.UpdateClientRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetClientDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetClientListResponse;
import com.epam.rd.autocode.spring.project.mapper.helper.UserMapperHelper;
import com.epam.rd.autocode.spring.project.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserMapperHelper.class)
public interface ClientMapper {

    @Mapping(target = "password", source = "clientDTO.password", qualifiedByName = "toEncodedPassword")
    @Mapping(target = "active", constant = "true")
    Client toClient(CreateClientRequest clientDTO);

    GetClientDetailsResponse toGetClientDetailsResponse(Client client);

    GetClientListResponse toGetClientListResponse(Client client);

    @Mapping(target = "password", source = "clientDTO.password", qualifiedByName = "toEncodedPassword")
    void updateClient(@MappingTarget Client client, UpdateClientRequest clientDTO);

}
