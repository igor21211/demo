package com.example.demo.dto;

import com.example.demo.domain.Employee;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeDTO {
    public String name;
    public String country;
    public String email;

    public static EmployeeDTO toDto(Employee employee) {
        return new EmployeeDTO(
                employee.getName(),
                employee.getCountry(),
                employee.getEmail());
    }
}
