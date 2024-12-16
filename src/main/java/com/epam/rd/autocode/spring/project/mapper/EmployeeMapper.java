package com.epam.rd.autocode.spring.project.mapper;

import com.epam.rd.autocode.spring.project.dto.EmployeeDTO;
import com.epam.rd.autocode.spring.project.mapper.helper.UserMapperHelper;
import com.epam.rd.autocode.spring.project.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserMapperHelper.class)
public interface EmployeeMapper {

    @Mapping(target = "password", source = "employeeDTO.password", qualifiedByName = "toEncodedPassword")
    Employee toEmployee(EmployeeDTO employeeDTO);

    @Mapping(target = "password", ignore = true)
    EmployeeDTO toEmployeeDto(Employee employee);

    @Mapping(target = "password", source = "employeeDTO.password", qualifiedByName = "toEncodedPassword")
    void updateEmployee(@MappingTarget Employee employee, EmployeeDTO employeeDTO);

}
