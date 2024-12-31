package com.epam.rd.autocode.spring.project.mapper;

import com.epam.rd.autocode.spring.project.dto.request.CreateClientRequest;
import com.epam.rd.autocode.spring.project.dto.request.UpdateClientRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetClientDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetClientListResponse;
import com.epam.rd.autocode.spring.project.mapper.helper.UserMapperHelper;
import com.epam.rd.autocode.spring.project.model.Client;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = UserMapperHelper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper {

    @Mapping(target = "password", source = "clientDTO.password", qualifiedByName = "toEncodedPassword")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "balance", constant = "0.0")
    Client toClient(CreateClientRequest clientDTO);

    @Mapping(target = "password", source = "clientDTO.password", qualifiedByName = "toOauthEncodedPassword")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "balance", constant = "0.0")
    Client toOuathClient(CreateClientRequest clientDTO);

    GetClientDetailsResponse toGetClientDetailsResponse(Client client);

    GetClientListResponse toGetClientListResponse(Client client);

    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "password", source = "clientDTO.password", qualifiedByName = "toEncodedPassword")
    void updateClient(@MappingTarget Client client, UpdateClientRequest clientDTO);

    @AfterMapping
    default void addBalance(@MappingTarget Client client, UpdateClientRequest updateClientRequest) {
        if (updateClientRequest.getBalance() != null && client.getBalance() != null) {
            client.setBalance(client.getBalance().add(updateClientRequest.getBalance()));
        } else if(client.getBalance() == null && updateClientRequest.getBalance() != null) {
            client.setBalance(updateClientRequest.getBalance());
        }
    }

}
