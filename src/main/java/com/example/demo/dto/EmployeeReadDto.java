package com.example.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class EmployeeReadDto {

    public String country;

    @Email
    @NotNull
    public String email;
}
