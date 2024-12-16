package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.ClientDTO;
import com.epam.rd.autocode.spring.project.exception.AlreadyExistException;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.mapper.ClientMapper;
import com.epam.rd.autocode.spring.project.repo.ClientRepository;
import com.epam.rd.autocode.spring.project.service.ClientService;
import com.epam.rd.autocode.spring.project.util.Boxed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    public static final String CLIENT_NOT_FOUND_ERROR_MESSAGE = "Client with %s email wasn't found!";

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public Page<ClientDTO> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::toClientDto);
    }

    @Override
    public ClientDTO getClientByEmail(String email) {
        return Boxed
                .of(email)
                .map(clientRepository::findClientByEmail)
                .map(clientMapper::toClientDto)
                .orElseThrow(() -> new NotFoundException(CLIENT_NOT_FOUND_ERROR_MESSAGE.formatted(email)));
    }

    @Override
    public ClientDTO updateClientByEmail(String email, ClientDTO clientDTO) {
        return Boxed
                .of(email)
                .map(clientRepository::findClientByEmail)
                .doWith(client1 -> clientMapper.updateClient(client1, clientDTO))
                .map(clientRepository::save)
                .map(clientMapper::toClientDto)
                .orElseThrow(() -> new NotFoundException(CLIENT_NOT_FOUND_ERROR_MESSAGE.formatted(email)));
    }

    @Override
    public void deleteClientByEmail(String email) {
        Boxed
                .of(email)
                .map(clientRepository::findClientByEmail)
                .ifPresentOrElseThrow(clientRepository::delete,
                        () -> new NotFoundException(CLIENT_NOT_FOUND_ERROR_MESSAGE.formatted(email)));
    }

    @Override
    public ClientDTO addClient(ClientDTO client) {
        return Boxed
                .of(client)
                .filter(client1 -> !clientRepository.existsByEmail(client1.getEmail()))
                .map(clientMapper::toClient)
                .map(clientRepository::save)
                .map(clientMapper::toClientDto)
                .orElseThrow(() -> new AlreadyExistException("Client with %s email already exist!".formatted(client.getEmail())));
    }

}
