package com.example.demo.service;

import com.example.demo.domain.Employee;

import java.util.List;

public interface Service {

    Employee create(Employee employee);

    Employee findByEmail(String email);

    List<Employee> getAll();

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();

}
