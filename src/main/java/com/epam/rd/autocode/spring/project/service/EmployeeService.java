package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.request.CreateEmployeeRequest;
import com.epam.rd.autocode.spring.project.dto.request.UpdateEmployeeRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetEmployeeDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetEmployeeListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    Page<GetEmployeeListResponse> getAllEmployees(Pageable pageable);

    GetEmployeeDetailsResponse getEmployeeByEmail(String email);

    GetEmployeeDetailsResponse updateEmployeeByEmail(String email, UpdateEmployeeRequest employee);

    void deleteEmployeeByEmail(String email);

    GetEmployeeDetailsResponse addEmployee(CreateEmployeeRequest employee);
}
