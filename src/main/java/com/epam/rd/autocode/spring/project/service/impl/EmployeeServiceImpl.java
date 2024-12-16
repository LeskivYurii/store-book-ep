package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.EmployeeDTO;
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

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    public static final String EMPLOYEE_NOT_FOUND_ERROR_MESSAGE = "Employee with %s email wasn't found!";

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(employeeMapper::toEmployeeDto);
    }

    @Override
    public EmployeeDTO getEmployeeByEmail(String email) {
        return Boxed
                .of(email)
                .map(employeeRepository::findEmployeeByEmail)
                .map(employeeMapper::toEmployeeDto)
                .orElseThrow(() -> new NotFoundException(EMPLOYEE_NOT_FOUND_ERROR_MESSAGE.formatted(email)));
    }

    @Override
    public EmployeeDTO updateEmployeeByEmail(String email, EmployeeDTO employeeDto) {
        return Boxed
                .of(email)
                .map(employeeRepository::findEmployeeByEmail)
                .doWith(employee1 -> employeeMapper.updateEmployee(employee1, employeeDto))
                .map(employeeRepository::save)
                .map(employeeMapper::toEmployeeDto)
                .orElseThrow(() -> new NotFoundException(EMPLOYEE_NOT_FOUND_ERROR_MESSAGE.formatted(email)));
    }

    @Override
    public void deleteEmployeeByEmail(String email) {
        Boxed
                .of(email)
                .map(employeeRepository::findEmployeeByEmail)
                .ifPresentOrElseThrow(employeeRepository::delete,
                        () -> new NotFoundException(EMPLOYEE_NOT_FOUND_ERROR_MESSAGE.formatted(email)));
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        return Boxed
                .of(employeeDTO)
                .filter(employeeDTO1 -> !employeeRepository.existsByEmail(employeeDTO.getEmail()))
                .map(employeeMapper::toEmployee)
                .map(employeeRepository::save)
                .map(employeeMapper::toEmployeeDto)
                .orElseThrow(() -> new AlreadyExistException("Employee with %s email already exist!".formatted(employeeDTO.getEmail())));
    }

}
