package com.epam.rd.autocode.spring.project.mapper;

import com.epam.rd.autocode.spring.project.dto.request.CreateEmployeeRequest;
import com.epam.rd.autocode.spring.project.dto.request.UpdateEmployeeRequest;
import com.epam.rd.autocode.spring.project.dto.response.GetEmployeeDetailsResponse;
import com.epam.rd.autocode.spring.project.dto.response.GetEmployeeListResponse;
import com.epam.rd.autocode.spring.project.mapper.helper.UserMapperHelper;
import com.epam.rd.autocode.spring.project.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserMapperHelper.class)
public interface EmployeeMapper {

    @Mapping(target = "password", source = "createEmployeeRequest.password", qualifiedByName = "toEncodedPassword")
    @Mapping(target = "active", constant = "true")
    Employee toEmployee(CreateEmployeeRequest createEmployeeRequest);

    GetEmployeeListResponse toGetEmployeeListResponse(Employee employee);

    GetEmployeeDetailsResponse toGetEmployeeDetailsResponse(Employee employee);

    @Mapping(target = "password", source = "employeeDTO.password", qualifiedByName = "toEncodedPassword")
    void updateEmployee(@MappingTarget Employee employee, UpdateEmployeeRequest employeeDTO);

}
