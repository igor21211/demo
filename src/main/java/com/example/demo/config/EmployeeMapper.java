package com.example.demo.config;

import com.example.demo.domain.Employee;
import com.example.demo.dto.EmployeeDto;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class EmployeeMapper extends CustomMapper<Employee, EmployeeDto> {

    @Override
    public void mapBtoA(EmployeeDto employeeDto, Employee employee, MappingContext context) {
        super.mapBtoA(employeeDto, employee, context);
    }
}
