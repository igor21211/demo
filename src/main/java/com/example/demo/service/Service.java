package com.example.demo.service;

import com.example.demo.domain.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface Service {

    Employee create(Employee employee);

    Employee findByEmail(String email);

    Page<Employee> getAllWithPagination(int page, int size, List<String> sortList, String sortOrder);

    List<Employee> getAll();

    List<Employee> findAllByPlanAndCountry(String country, Integer plan);

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    Employee pay(Integer id, String plan);

    void removeById(Integer id);

    Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder);

    void removeAll();

}
