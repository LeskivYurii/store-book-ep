package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.request.CreateEmployeeRequest;
import com.epam.rd.autocode.spring.project.dto.request.UpdateEmployeeRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetEmployeeDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetEmployeeListResponse;
import com.epam.rd.autocode.spring.project.exception.AlreadyExistException;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.mapper.EmployeeMapper;
import com.epam.rd.autocode.spring.project.repo.EmployeeRepository;
import com.epam.rd.autocode.spring.project.service.EmployeeService;
import com.epam.rd.autocode.spring.project.util.Boxed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    public static final String EMPLOYEE_NOT_FOUND_ERROR_MESSAGE = "Employee with %s email wasn't found!";

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public Page<GetEmployeeListResponse> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(employeeMapper::toGetEmployeeListResponse);
    }

    @Override
    public GetEmployeeDetailsResponse getEmployeeByEmail(String email) {
        return Boxed
                .of(email)
                .flatOpt(employeeRepository::findEmployeeByEmail)
                .map(employeeMapper::toGetEmployeeDetailsResponse)
                .orElseThrow(() -> new NotFoundException(EMPLOYEE_NOT_FOUND_ERROR_MESSAGE.formatted(email)));
    }

    @Override
    public GetEmployeeDetailsResponse updateEmployeeByEmail(String email, UpdateEmployeeRequest employeeDto) {
        return Boxed
                .of(email)
                .flatOpt(employeeRepository::findEmployeeByEmail)
                .doWith(employee1 -> employeeMapper.updateEmployee(employee1, employeeDto))
                .map(employeeRepository::save)
                .map(employeeMapper::toGetEmployeeDetailsResponse)
                .orElseThrow(() -> new NotFoundException(EMPLOYEE_NOT_FOUND_ERROR_MESSAGE.formatted(email)));
    }

    @Override
    public void deleteEmployeeByEmail(String email) {
        Boxed
                .of(email)
                .flatOpt(employeeRepository::findEmployeeByEmail)
                .ifPresentOrElseThrow(employeeRepository::delete,
                        () -> new NotFoundException(EMPLOYEE_NOT_FOUND_ERROR_MESSAGE.formatted(email)));
    }

    @Override
    public GetEmployeeDetailsResponse addEmployee(CreateEmployeeRequest employeeDTO) {
        return Boxed
                .of(employeeDTO)
                .filter(employeeDTO1 -> !employeeRepository.existsByEmail(employeeDTO.getEmail()))
                .map(employeeMapper::toEmployee)
                .map(employeeRepository::save)
                .map(employeeMapper::toGetEmployeeDetailsResponse)
                .orElseThrow(() -> new AlreadyExistException("Employee with %s email already exist!"
                        .formatted(employeeDTO.getEmail())));
    }

}
