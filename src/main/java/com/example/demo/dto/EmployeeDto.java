package com.example.demo.dto;


import com.example.demo.domain.Employee;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    @NotNull(message = "Field name must be not null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    public String name;
    @NotEmpty
    @NotNull(message = "please input your country")
    public String country;
    @Email
    public String email;
    @NotNull(message = "please input your number of phone")
    public Long numberOfPhone;

    public static EmployeeDto map(Employee employee) {
        return new EmployeeDto(
                employee.getName(),
                employee.getCountry(),
                employee.getEmail(),
                employee.getNumberOfPhone()

        );
    }
}
