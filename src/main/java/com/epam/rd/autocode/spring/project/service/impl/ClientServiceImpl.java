package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.request.CreateClientRequest;
import com.epam.rd.autocode.spring.project.dto.request.UpdateClientRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetClientDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetClientListResponse;
import com.epam.rd.autocode.spring.project.exception.AlreadyExistException;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.mapper.ClientMapper;
import com.epam.rd.autocode.spring.project.repo.ClientRepository;
import com.epam.rd.autocode.spring.project.service.ClientService;
import com.epam.rd.autocode.spring.project.util.Boxed;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    public static final String CLIENT_NOT_FOUND_ERROR_MESSAGE = "error.client.notfound";

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public Page<GetClientListResponse> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::toGetClientListResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public GetClientDetailsResponse getClientByEmail(String email) {
        return Boxed
                .of(email)
                .flatOpt(clientRepository::findClientByEmail)
                .map(clientMapper::toGetClientDetailsResponse)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage(CLIENT_NOT_FOUND_ERROR_MESSAGE, null,
                        LocaleContextHolder.getLocale()).formatted(email)));
    }

    @Override
    @Transactional
    public GetClientDetailsResponse updateClientByEmail(String email, UpdateClientRequest clientDTO) {
        return Boxed
                .of(email)
                .flatOpt(clientRepository::findClientByEmail)
                .doWith(client1 -> clientMapper.updateClient(client1, clientDTO))
                .map(clientRepository::save)
                .map(clientMapper::toGetClientDetailsResponse)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage(CLIENT_NOT_FOUND_ERROR_MESSAGE, null,
                        LocaleContextHolder.getLocale()).formatted(email)));
    }

    @Override
    @Transactional
    public void deleteClientByEmail(String email) {
        Boxed
                .of(email)
                .flatOpt(clientRepository::findClientByEmail)
                .ifPresentOrElseThrow(clientRepository::delete,
                        () -> new NotFoundException(messageSource.getMessage(CLIENT_NOT_FOUND_ERROR_MESSAGE, null,
                                LocaleContextHolder.getLocale()).formatted(email)));
    }

    @Override
    @Transactional
    public GetClientDetailsResponse addClient(CreateClientRequest client) {
        return Boxed
                .of(client)
                .filter(client1 -> !clientRepository.existsByEmail(client1.getEmail()))
                .map(clientMapper::toClient)
                .map(clientRepository::save)
                .map(clientMapper::toGetClientDetailsResponse)
                .orElseThrow(() -> new AlreadyExistException(messageSource.getMessage(CLIENT_NOT_FOUND_ERROR_MESSAGE, null,
                        LocaleContextHolder.getLocale()).formatted(
                        client.getEmail())));
    }

    @Override
    @Transactional
    public void blockUnblockClient(String email) {
        Boxed
                .of(email)
                .flatOpt(clientRepository::findClientByEmail)
                .doWith(client -> client.setActive(!client.isActive()))
                .ifPresentOrElseThrow(clientRepository::save, () -> new EntityNotFoundException(
                        messageSource.getMessage(CLIENT_NOT_FOUND_ERROR_MESSAGE, null,
                                LocaleContextHolder.getLocale()).formatted(email)));
    }

    @Override
    @Transactional
    public GetClientDetailsResponse addOauthClient(CreateClientRequest client) {
        return Boxed
                .of(client)
                .filter(client1 -> !clientRepository.existsByEmail(client1.getEmail()))
                .map(clientMapper::toOuathClient)
                .map(clientRepository::save)
                .map(clientMapper::toGetClientDetailsResponse)
                .orElseThrow(() -> new AlreadyExistException( messageSource.getMessage("error.client.alreadyExist", null,
                                LocaleContextHolder.getLocale()).formatted(client.getEmail())));
    }
}
