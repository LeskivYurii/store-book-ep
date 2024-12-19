package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.request.CreateClientRequest;
import com.epam.rd.autocode.spring.project.dto.request.UpdateClientRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetClientDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetClientListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

    Page<GetClientListResponse> getAllClients(Pageable pageable);

    GetClientDetailsResponse getClientByEmail(String email);

    GetClientDetailsResponse updateClientByEmail(String email, UpdateClientRequest client);

    void deleteClientByEmail(String email);

    GetClientDetailsResponse addClient(CreateClientRequest client);
}
