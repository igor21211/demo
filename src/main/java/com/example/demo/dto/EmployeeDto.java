package com.example.demo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class EmployeeDto {

    public Integer id;
    @NotNull
    @Size(min = 2,max = 32, message = "Name must be between 2 and 32 characters long")
    public String name;
    @NotNull
    @Size(min = 3, max = 42, message = "Country must be between 3 and 42 characters long")
    public String country;

    public Long numberOfPhone;
    public Date dateCreated;
}
