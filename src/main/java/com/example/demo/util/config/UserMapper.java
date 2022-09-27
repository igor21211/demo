package com.example.demo.util.config;

import com.example.demo.domain.Employee;
import com.example.demo.dto.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    EmployeeDto toDto(Employee employee);
}
