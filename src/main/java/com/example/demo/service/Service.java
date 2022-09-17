package com.example.demo.service;

import com.example.demo.domain.Employee;

import java.util.List;

public interface Service {

    Employee create(Employee employee);

    Employee findByEmail(String email);

    List<Employee> getAll();

    List<Employee> findAllByPlanAndCountry(String country, Integer plan);

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    Employee pay(Integer id, String plan);

    void removeById(Integer id);

    void removeAll();

}
