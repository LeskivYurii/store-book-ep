package com.epam.rd.autocode.spring.project.mapper;

import com.epam.rd.autocode.spring.project.dto.ClientDTO;
import com.epam.rd.autocode.spring.project.mapper.helper.UserMapperHelper;
import com.epam.rd.autocode.spring.project.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserMapperHelper.class)
public interface ClientMapper {

    @Mapping(target = "password", source = "clientDTO.password", qualifiedByName = "toEncodedPassword")
    Client toClient(ClientDTO clientDTO);

    @Mapping(target = "password", ignore = true)
    ClientDTO toClientDto(Client client);

    @Mapping(target = "password", source = "clientDTO.password", qualifiedByName = "toEncodedPassword")
    void updateClient(@MappingTarget Client client, ClientDTO clientDTO);

}
