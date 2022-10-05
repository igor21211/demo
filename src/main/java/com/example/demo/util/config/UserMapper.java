package com.example.demo.util.config;

import com.example.demo.domain.Employee;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeePayDto;
import com.example.demo.dto.EmployeePlanDto;
import com.example.demo.dto.EmployeeReadDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {


    EmployeeDto toDto(Employee employee);

    Employee toEmployee(EmployeeDto employeeDto);

    EmployeeReadDto toReadDto(Employee employee);


    EmployeePlanDto getplanDto(Employee employee);

    EmployeePayDto payDto(Employee employee);

}
