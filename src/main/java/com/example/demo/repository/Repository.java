package com.example.demo.repository;

import com.example.demo.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    Employee findByEmail(String email);
}
