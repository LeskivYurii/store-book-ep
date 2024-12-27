package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.request.CreateClientRequest;
import com.epam.rd.autocode.spring.project.dto.request.UpdateClientRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetClientDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetClientListResponse;
import com.epam.rd.autocode.spring.project.exception.AlreadyExistException;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.mapper.ClientMapper;
import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.repo.ClientRepository;
import com.epam.rd.autocode.spring.project.service.ClientService;
import com.epam.rd.autocode.spring.project.util.Boxed;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    public static final String CLIENT_NOT_FOUND_ERROR_MESSAGE = "Client with %s email wasn't found!";

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public Page<GetClientListResponse> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::toGetClientListResponse);
    }

    @Override
    public GetClientDetailsResponse getClientByEmail(String email) {
        return Boxed
                .of(email)
                .flatOpt(clientRepository::findClientByEmail)
                .map(clientMapper::toGetClientDetailsResponse)
                .orElseThrow(() -> new NotFoundException(CLIENT_NOT_FOUND_ERROR_MESSAGE.formatted(email)));
    }

    @Override
    public GetClientDetailsResponse updateClientByEmail(String email, UpdateClientRequest clientDTO) {
        return Boxed
                .of(email)
                .flatOpt(clientRepository::findClientByEmail)
                .doWith(client1 -> clientMapper.updateClient(client1, clientDTO))
                .map(clientRepository::save)
                .map(clientMapper::toGetClientDetailsResponse)
                .orElseThrow(() -> new NotFoundException(CLIENT_NOT_FOUND_ERROR_MESSAGE.formatted(email)));
    }

    @Override
    public void deleteClientByEmail(String email) {
        Boxed
                .of(email)
                .flatOpt(clientRepository::findClientByEmail)
                .ifPresentOrElseThrow(clientRepository::delete,
                        () -> new NotFoundException(CLIENT_NOT_FOUND_ERROR_MESSAGE.formatted(email)));
    }

    @Override
    public GetClientDetailsResponse addClient(CreateClientRequest client) {
        return Boxed
                .of(client)
                .filter(client1 -> !clientRepository.existsByEmail(client1.getEmail()))
                .map(clientMapper::toClient)
                .map(clientRepository::save)
                .map(clientMapper::toGetClientDetailsResponse)
                .orElseThrow(() -> new AlreadyExistException("Client with %s email already exist!".formatted(client.getEmail())));
    }

    @Override
    public void blockUnblockClient(String email) {
        Boxed
                .of(email)
                .flatOpt(clientRepository::findClientByEmail)
                .doWith(client -> client.setActive(!client.isActive()))
                .ifPresentOrElseThrow(clientRepository::save, () -> new EntityNotFoundException(CLIENT_NOT_FOUND_ERROR_MESSAGE.formatted(email)));
    }

}
