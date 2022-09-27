package com.example.demo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeDto {

    public Integer id;

    public String name;
    public String country;
    public String email;
    public Long numberOfPhone;

}
