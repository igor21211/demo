package com.example.demo.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Field name must be not null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    private String name;
    @NotEmpty
    @NotNull(message = "please input your country")
    @Size(min = 2, max = 42, message = "Country name must be between 2 and 32 characters long")
    private String country;
    @Email
    private String email;
    @Column(name = "number_of_phone")
    private Long numberOfPhone;
    private Integer plan;
    @Column(name = "date_created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateCreated = Date.from(Instant.now());
    @Column(name = "date_activation")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateActivation;

}

