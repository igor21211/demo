package com.example.demo.util.config;

import com.example.demo.domain.Employee;
import com.example.demo.dto.EmployeeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    EmployeeDto toDto(Employee employee);
}
