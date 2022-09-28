package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class EmployeePayDto {

    public String result = "successful";
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public Date dateActivation;
}
