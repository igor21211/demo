package com.example.demo.dto;


import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {


    public String name;

    public String country;

    public String email;

    public Long numberOfPhone;

}
